/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

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
public class Receivertest {
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //Timer timer=new Timer(1000);
        
   
    String serverIp="192.168.0.14";
    

    try
     {
    Socket receiveSocket = new Socket(InetAddress.getByName(serverIp),8080);
    int i=0;
    byte[] fileData = new byte[8196];
    //DatagramPacket receivePacket=new DatagramPacket(fileData,0, InetAddress.getByName(serverIp), 8080);
    short prevTimeStamp = -1;
    DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
    PrintStream ps= new PrintStream(receiveSocket.getOutputStream());
    ps.print("download\r\n");
    ps.print("kiran\r\n");
    ps.print("fish.mp4\r\n");
    ps.print("chunk"+i+"\r\n");
    String msg=dis.readLine();
    while(!msg.equalsIgnoreCase("nochunk"))
    {
        
        if(i!=0)
        {
            receiveSocket = new Socket(InetAddress.getByName(serverIp),8080);
            ps= new PrintStream(receiveSocket.getOutputStream());
            dis = new DataInputStream(receiveSocket.getInputStream());
            ps.print("download\r\n");
            ps.print("kiran\r\n");
            ps.print("fish.mp4\r\n");
            ps.print("chunk"+i+"\r\n");
            msg=dis.readLine();
         }
   
    System.out.println(msg);
    int chunkSize=Integer.parseInt(dis.readLine());
    System.out.println(chunkSize);
    System.out.println("reading "+dis.readLine());
    File file = new File("chunk"+i+".mp4");
    FileOutputStream fo=new FileOutputStream(file);
    int sizeRead=0;    
    while(sizeRead!=-1)
    {
        sizeRead = dis.read(fileData);
        if(sizeRead>=0)
        fo.write(fileData,0,sizeRead);
        System.out.println(sizeRead);
    }
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
