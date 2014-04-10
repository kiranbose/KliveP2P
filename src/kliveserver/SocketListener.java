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
    
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try{
            ServerSocket server = null;
            boolean serverBound = false;
            while(!serverBound)
            {
                try{
                    server = new ServerSocket(Globals.GlobalData.serverPort);
                    serverBound = true;
                    
                }
                catch(Exception e)
                {
                    Globals.log.error("port already bound "+Globals.GlobalData.serverPort);
                    Globals.GlobalData.serverPort++;
                    continue;
                }
            }
            Globals.log.message("kLiveP2P started on port "+Globals.GlobalData.serverPort);
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
}
