/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uploaddownload;

import Globals.GlobalData;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author home
 */
public class SendToFFMPeg extends Thread
    {
        public static volatile boolean stopThread = false;
        
        MulticastSocket sendSocket;
        DatagramPacket sendPacket;
        byte[] sendBuffer = new byte[8196];
        public volatile ConcurrentLinkedQueue<String> nextFileToStreamQueue;
        public volatile ConcurrentLinkedQueue<Integer> nextStartChunkQueue;
        long tStart, tEnd;
        
        public SendToFFMPeg() 
        {
            nextFileToStreamQueue = new ConcurrentLinkedQueue();
            nextStartChunkQueue = new ConcurrentLinkedQueue();
            setDaemon(true);
            setPriority(MIN_PRIORITY);
        }
        
        public void queueFileToStream(String fileName,Integer startChunk)
        {
            nextFileToStreamQueue.add(fileName);
            nextStartChunkQueue.add(startChunk);
        }
        
        void streamChunk(String fileName,int chunkNumber)
        {
            File chunk=new File(Globals.GlobalData.RTPVideoStorePath+"\\"+fileName+"\\chunk"+chunkNumber);
            if(!chunk.exists()||!chunk.canRead())
            {
                Globals.log.error("chunk "+chunkNumber+" not readable. cannot be streamed.");
                return;
            }
            try
            {
                FileInputStream fin = new FileInputStream(chunk);
                DataInputStream dis = new DataInputStream(fin);
                while(nextFileToStreamQueue.isEmpty()&&dis.available()!=0)
                {
                    int toRead = dis.readInt();
                    short timeStamp = dis.readShort();
                    dis.readFully(sendBuffer,0,toRead);
                    sendPacket.setData(sendBuffer,0,toRead);
                    sendSocket.send(sendPacket);
                }
                dis.close();
                fin.close();
            }
            catch (Exception e)
            {
                Globals.log.error("Error while streaming "+fileName+" chunk"+chunkNumber);
                e.printStackTrace();
            }
        }
        
        void streamFile(String fileName,int startChunk)
        {
            try
            {
                int chunk = startChunk;
                int chunkTimeout = 0;
                while(nextFileToStreamQueue.isEmpty()&&chunkTimeout<=20)
                {
                    File chunkFile=new File(Globals.GlobalData.RTPVideoStorePath+"\\"+fileName+"\\chunk"+chunk);
                    if(!chunkFile.exists()||!chunkFile.canRead())
                    {
                        Globals.log.error(fileName+" chunk "+chunk+" not cached. Waiting for 2 sec");
                        Thread.sleep(2000);
                        chunkTimeout += 2;
                        continue;
                    }
                    tEnd=System.currentTimeMillis();
                    if(tEnd-tStart < (GlobalData.videoSegmentLength-1)*1000)
                    {
                        Thread.sleep((GlobalData.videoSegmentLength-1)*1000-(tEnd-tStart));   
                        tStart = System.currentTimeMillis();
                    }
                    chunkTimeout = 0;
                    Globals.log.Progress("-<stream:"+fileName+":" +chunk+">");
                    streamChunk(fileName, chunk);
                    chunk++;
                }
            }
            catch (Exception e)
            {
                Globals.log.error("Error while streaming "+fileName);
                e.printStackTrace();
            }
        }
       
       

    @Override
    public void run() 
    {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try
        {
            sendSocket = new MulticastSocket(GlobalData.RTPStreamPort);
            sendSocket.setSendBufferSize(5000000);
            sendSocket.joinGroup(InetAddress.getByName(GlobalData.RTPStreamingAddress));
            sendSocket.setTimeToLive(0);
            sendPacket = new DatagramPacket(sendBuffer, 0,InetAddress.getByName(GlobalData.RTPStreamingAddress),GlobalData.RTPStreamPort);
            while(!stopThread)
            {
                String fileName = nextFileToStreamQueue.poll();
                if(fileName == null)
                {
                    Thread.sleep(1000);
                    continue;
                }
                Integer start = nextStartChunkQueue.poll();
                while(start == null)
                {
                    Thread.sleep(1000);
                    start = nextStartChunkQueue.poll();
                }
                int startChunk = start;
                Globals.log.message("sending rtp stream "+fileName+" startTime "+startChunk*GlobalData.videoSegmentLength);
                tStart = System.currentTimeMillis();
                streamFile(fileName, startChunk);
                Globals.log.message("ended rtp stream "+fileName+" startTime ");
            }
        }
        catch(Exception e)
        {
            Globals.log.error("CRITICAL: Cannot send streams to ffmpeg. restart app.");
            e.printStackTrace();
        }
    }
}


