/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author home
 */
public class KliveUploadFile {
    
   
    public static void main(String args[]) {
        String ipAddress= "192.168.0.11";
        int port = 8080;
        byte[] aByte = new byte[1];
        int bytesRead;
           
try
{
   
File file = new File("C:\\Users\\home\\Desktop\\gangster.mp4");
Socket clientSocket = new Socket(ipAddress, port);
BufferedOutputStream outToClient = null;
DataOutputStream dos= new DataOutputStream(clientSocket.getOutputStream());
PrintStream ps = new PrintStream(dos);
FileInputStream fis = new FileInputStream(file);
ps.println("upload");
ps.println("kiran");
ps.println(file.getName());
ps.println(file.length());
ps.println();
byte[] buffer = new byte[clientSocket.getSendBufferSize()];
int read = 0;
int readtotal = 0;
ByteBuffer wrapped = ByteBuffer.wrap(buffer);
while ((read = fis.read(buffer)) != -1) {
readtotal = read + readtotal;
System.out.println("Writing :" + read + ", Total written:" + readtotal);
ps.write(buffer,0,read);
}

}catch (Exception e)
{
    e.printStackTrace();
}

  }
}