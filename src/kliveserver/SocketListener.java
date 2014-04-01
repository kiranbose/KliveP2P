/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kliveserver;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kiran
 */
public class SocketListener extends Thread{
    int port;
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try{
            ServerSocket server = new ServerSocket(port);
            Globals.log.message("kLiveP2P started on port "+port);
            Socket clientSocket;
            DataInputStream dis;
            while(true)
            {
                clientSocket = server.accept();
                try{
                    dis = new DataInputStream(clientSocket.getInputStream());
                    String request = dis.readLine();
                    if(request.equalsIgnoreCase("download"))
                    {
                        String userID = dis.readLine();
                        String fileName = dis.readLine();
                        String chunkNumber = dis.readLine();
                        Globals.log.message(userID+": Recieved chunk download: "+fileName+ " " +chunkNumber);
                        ChunkSender chunkSender = new ChunkSender(clientSocket,fileName,chunkNumber);
                        chunkSender.start();
                    }
                    else
                    {
                        clientSocket.close();
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }   

    }
    
    public void StartServerOn(int port)
    {
        this.port = port;
        start();
    }
}
