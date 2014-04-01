/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kliveserver;

import RTP.RTPFileGenerator;
import UI.LoginscreenController;
import UI.ShowLoginScreen;
import UI.ShowMainUI;
import VideoStore.VideoDetails;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author home
 */
public class CloudConnection extends Thread{
    Socket cloudSock;
    DataOutputStream cloudOut;
    DataInputStream cloudIn;
    public volatile static boolean connecting = false;
    public CloudConnection() {
        this.cloudSock = null;
        cloudOut = null;
    }
    
    @Override
    public void run() {
        super.run(); 
        //send stream details to peer
        try {
            if(connecting)
                return;
            connecting = true;
            cloudSock = new Socket(InetAddress.getByName(Globals.GlobalData.cloudIP), Globals.GlobalData.cloudPort);
            cloudOut = new DataOutputStream(cloudSock.getOutputStream());
            cloudIn = new DataInputStream(cloudSock.getInputStream());
            Globals.log.message("connected to cloud. showing main ui");
            ShowMainUI mainui = new ShowMainUI();
            mainui.show();
            Globals.log.message("Sending control message to cloud.");
            cloudOut.writeBytes("control\r\n");
            cloudOut.writeBytes(Globals.GlobalData.UserID+"\r\n");
            while(true)
            {
                String request = cloudIn.readLine();
                if(request.equalsIgnoreCase("getChannels"))
                {
                    Globals.log.message(": getChannels ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Globals.log.error("cloud connection terminated relogin.");
            ShowLoginScreen login = new ShowLoginScreen();
            login.show();
            ShowLoginScreen.controller.connectionError.setVisible(true);
        }
        connecting = false;
    }
}
