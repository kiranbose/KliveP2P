/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author home
 */
public class SendToFFMPeg extends Thread{
       static String multicastIpAddress="224.1.1.2";
       static int multicastPort=9999;

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        String cmd= "cmd /c start C:\\Users\\home\\Documents\\ffmpeg\\bin\\ffplay.exe rtp://"+multicastIpAddress+":"+multicastPort;
        Runtime rt= Runtime.getRuntime();
           try {
               
               Process p=rt.exec(cmd);
               
               System.out.println("thread started");
           } catch (IOException ex) {
               Logger.getLogger(SendToFFMPeg.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    
    
    public static void main(String[] args) throws IOException
    {
        SendToFFMPeg objThread = new SendToFFMPeg();
        objThread.start();
        MulticastSocket sendSocket = new MulticastSocket(multicastPort);
        sendSocket.joinGroup(InetAddress.getByName(multicastIpAddress));
        sendSocket.setTimeToLive(0);
        int i=1;
        
        try
        {
           
           File chunk=new File("chunk"+i+".mp4");
           while(chunk.exists())
           {
    
            byte[] fileData = new byte[8196];
            String response;
            DatagramPacket sendPacket=new DatagramPacket(fileData, 0,InetAddress.getByName(multicastIpAddress),multicastPort);
            sendSocket.setSendBufferSize(5000000);
            ByteBuffer wrapped = ByteBuffer.wrap(fileData);
            long tStart = System.currentTimeMillis();
            long tEnd;
            short prevTimeStamp = -1;
            FileInputStream fin = new FileInputStream(chunk);
            DataInputStream dis = new DataInputStream(fin);
             while(true)
            {
                if(dis.available()<=0)
                {
                    i++;
                    System.out.println("open chunk"+i);
                    chunk=new File("chunk"+i+".mp4");
                    dis.close();
                    fin.close();
                    fin = new FileInputStream(chunk);
                    dis = new DataInputStream(fin);
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
                    tEnd=System.currentTimeMillis();
                    Thread.sleep(1000-(tEnd-tStart)-300);
                    tStart=System.currentTimeMillis();
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


