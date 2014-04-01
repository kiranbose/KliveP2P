/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kliveserver;

import RTP.RTPFileGenerator;
import VideoStore.VideoDetails;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author home
 */
public class CloudConnection extends Thread{
    Socket sock;
    
    public CloudConnection() {
        this.sock = null;
    }

    public OutputStream getOutputStream() throws IOException
    {
        return sock.getOutputStream();
    }
    
    @Override
    public void run() {
        super.run(); 
        //send stream details to peer
        try {
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            while(true)
            {
                String request = dis.readLine();
                if(request.equalsIgnoreCase("getChannels"))
                {
                    Globals.log.message(": getChannels ");
                }
                else if(request.equalsIgnoreCase("close"))
                {
                    Globals.log.message(": closed ");
                    dis.close();
                    sock.close();
                    break;
                }
                    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
