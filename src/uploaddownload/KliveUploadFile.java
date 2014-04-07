/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uploaddownload;

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
public class KliveUploadFile extends Thread 
{

     String ipAddress;
     int port;
     File file;
    
    public KliveUploadFile(String filename) {
     ipAddress= Globals.GlobalData.cloudIP;
     port = Globals.GlobalData.cloudPort;
     file = new File(filename);
    }
    
    

    @Override
    public void run() 
    {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        byte[] aByte = new byte[1];
        int bytesRead;
           
        try
        {
            if(!file.exists())
            {
                Globals.log.error("File "+file.getAbsolutePath()+" does not exist. choose an existing file ");
                return;
            }
            Socket clientSocket = new Socket(ipAddress, port);
            BufferedOutputStream outToClient = null;
            DataOutputStream dos= new DataOutputStream(clientSocket.getOutputStream());
            PrintStream ps = new PrintStream(dos);
            FileInputStream fis = new FileInputStream(file);
            ps.println("upload");
            ps.println(Globals.GlobalData.UserID);
            ps.println(file.getName());
            ps.println(file.length());
            byte[] buffer = new byte[8096];
            int read = 0;
            int readtotal = 0;
            ByteBuffer wrapped = ByteBuffer.wrap(buffer);
            while ((read = fis.read(buffer)) != -1) 
            {
                readtotal = read + readtotal;
                //Globals.log.message("Writing :" + read + ", Total written:" + readtotal);
                dos.write(buffer,0,read);
            }
            ps.flush();
            ps.close();
            fis.close();
            dos.close();
            clientSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}