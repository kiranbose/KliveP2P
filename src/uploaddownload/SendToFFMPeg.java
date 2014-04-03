/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uploaddownload;

import Globals.GlobalData;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author home
 */
public class SendToFFMPeg extends Thread
    {
        String RTPCachePath;
        int startChunk;
        static SendToFFMPeg currentStreamingThread = null;
        public static volatile boolean stopThread = false;
        public SendToFFMPeg(String path,int startChunk) 
        {
               RTPCachePath = path;
               this.startChunk = startChunk;
        }
       
       

    @Override
    public void run() 
    {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try
        {
            if(currentStreamingThread!=null)
            {
                SendToFFMPeg.stopThread = true;
                Globals.log.message("waiting for current player stream to close.");
                currentStreamingThread.join();
                Globals.log.message("Current player stream closed. starting new player.");
            }
            currentStreamingThread = this;
            SendToFFMPeg.stopThread = false;
        MulticastSocket sendSocket = new MulticastSocket(GlobalData.RTPStreamPort);
        sendSocket.setSendBufferSize(5000000);
        sendSocket.joinGroup(InetAddress.getByName(GlobalData.RTPStreamingAddress));
        sendSocket.setTimeToLive(0);
        int i=startChunk;
           File chunk=new File(RTPCachePath+"\\chunk"+i);
           while(!stopThread&&chunk.exists())
           {
    
            byte[] fileData = new byte[8196];
            String response;
            DatagramPacket sendPacket=new DatagramPacket(fileData, 0,InetAddress.getByName(GlobalData.RTPStreamingAddress),GlobalData.RTPStreamPort);
            ByteBuffer wrapped = ByteBuffer.wrap(fileData);
            long tStart = System.currentTimeMillis();
            long tEnd;
            short prevTimeStamp = -1;
            FileInputStream fin = new FileInputStream(chunk);
            DataInputStream dis = new DataInputStream(fin);
             while(!stopThread)
            {
                if(dis.available()<=0)
                {
                    i++;
                    Globals.log.Progress("."+i);
                    chunk=new File(RTPCachePath+"\\chunk"+i);
                    dis.close();
                    fin.close();
                    fin = new FileInputStream(chunk);
                    dis = new DataInputStream(fin);
                    tEnd=System.currentTimeMillis();
                    if(tEnd-tStart < 4000)
                    {
                        Thread.sleep(4000-(tEnd-tStart));   
                        tStart = System.currentTimeMillis();
                    }
                }
                int toRead = dis.readInt();
                short timeStamp = dis.readShort();
                dis.readFully(fileData,0,toRead);

                //dis.read(fileData, 0, toRead);
                sendPacket.setData(fileData,0,toRead);
                sendSocket.send(sendPacket);
                if(timeStamp!=prevTimeStamp)
                {
                    prevTimeStamp = timeStamp;
                    
                }
            }
            //dis.close();

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}


