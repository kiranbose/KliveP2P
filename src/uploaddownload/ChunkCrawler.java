/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uploaddownload;

import Video.MediaPlayer;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.*;
import javax.swing.Timer;

/**
 *
 * @author home
 */
public class ChunkCrawler extends Thread 
{
    String serverIp;
    int serverPort;
    String RTPCachePath;
    String fileName;
    int i;
    int offset;

    public ChunkCrawler(int currentStreamingChunk,String video) 
    {
        serverIp=Globals.GlobalData.cloudIP;
        serverPort=Globals.GlobalData.cloudPort;
        RTPCachePath = Globals.GlobalData.RTPVideoStorePath;
        i=currentStreamingChunk;
        offset=currentStreamingChunk;
        fileName=video;
    }
    @Override
    public void run() 
    {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try
            {
                File rtpCachePath=new File(RTPCachePath+"\\"+fileName);
                if(!rtpCachePath.isDirectory())
                {
                    rtpCachePath.mkdir();
                }
                Socket receiveSocket = new Socket(InetAddress.getByName(serverIp),serverPort);
                byte[] fileData = new byte[8196];
                //DatagramPacket receivePacket=new DatagramPacket(fileData,0, InetAddress.getByName(serverIp), 8080);
                short prevTimeStamp = -1;
                DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
                PrintStream ps= new PrintStream(receiveSocket.getOutputStream());
                ps.print("download\r\n");
                ps.print("kiran\r\n");
                ps.print(fileName+"\r\n");
                ps.print("chunk"+i+"\r\n");
                String msg=dis.readLine();
                Globals.log.message("Receiving chunks for decoding ");
                Globals.log.message("Calling Player ........");
                SendToFFMPeg sendObj= new SendToFFMPeg(rtpCachePath.getAbsolutePath(),offset);
                sendObj.start();
                MediaPlayer.restartMediaPlayer();
                if(true)return;
                while(!msg.equalsIgnoreCase("nochunk"))
                {

                    if(i!=offset)
                    {
                        receiveSocket = new Socket(InetAddress.getByName(serverIp),serverPort);
                        ps= new PrintStream(receiveSocket.getOutputStream());
                        dis = new DataInputStream(receiveSocket.getInputStream());
                        ps.print("download\r\n");
                        ps.print("kiran\r\n");
                        ps.print(fileName+"\r\n");
                        ps.print("chunk"+i+"\r\n");
                        msg=dis.readLine();
                     }
                    System.out.println(msg);
                    int chunkSize=Integer.parseInt(dis.readLine());
                    System.out.println(chunkSize);
                    System.out.println("reading "+dis.readLine());
                    File file = new File(rtpCachePath.getAbsolutePath()+"\\chunk"+i);
                    FileOutputStream fo=new FileOutputStream(file);
                    int sizeRead=0;    
                    while(sizeRead!=-1)
                    {
                        sizeRead = dis.read(fileData);
                        if(sizeRead>=0)
                        fo.write(fileData,0,sizeRead);
                        System.out.println(sizeRead);
                    }
                    Globals.log.message("Chunk decoding complete ");
                    fo.close();
                    ps.flush();
                    ps.close();
                    receiveSocket.close();
                    i++;
                    //dis.close();
                } 
                dis.close();

           }
           catch(Exception e)
           {
             e.printStackTrace();
           }
    }


}
