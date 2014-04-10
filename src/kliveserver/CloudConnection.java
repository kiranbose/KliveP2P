/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kliveserver;

import RTP.RTPFileGenerator;
import UI.LoginscreenController;
import UI.LoginScreen;
import UI.MainUI;
import Video.VideoDetails;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import uploaddownload.ChunkCrawler;

/**
 *
 * @author home
 */
public class CloudConnection extends Thread{
    Socket cloudSock;
    DataOutputStream cloudOut;
    DataInputStream cloudIn;
    PrintStream toCloud;
    public volatile static boolean connecting = false;
    public CloudConnection() {
        this.cloudSock = null;
        cloudOut = null;
        this.setDaemon(true);
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
            toCloud = new PrintStream(cloudOut);
            cloudIn = new DataInputStream(cloudSock.getInputStream());
            Globals.log.message("connected to cloud. showing main ui");
            MainUI mainui = new MainUI();
            mainui.show();
            while(MainUI.mainUIController == null){}
            Globals.log.message("Sending control message to cloud.");
            toCloud.print("control\r\n");
            toCloud.print(Globals.GlobalData.UserID+"\r\n");
            toCloud.print(Globals.GlobalData.myIP+"\r\n");
            toCloud.print(Globals.GlobalData.serverPort+"\r\n");
            toCloud.print("getChannels\r\n");
            toCloud.flush();
            while(true)
            {
                String response = cloudIn.readLine();
                if(response.equalsIgnoreCase("video")||response.equalsIgnoreCase("NewVideoAvailable"))
                {
                    String video=cloudIn.readLine();
                    Globals.GlobalData.videoLibrary.updateVideodetails(video);
                    Globals.log.message("Video  on demand "+video);
                    MainUI.reloadVideoListFromLibrary();
                }
                else if(response.equalsIgnoreCase("clearPeerList"))
                {
                    Globals.GlobalData.peerTracker.removeAll();
                    Globals.log.message("Removing all peers from local tracker");
                }
                else if(response.equalsIgnoreCase("peerDetail"))
                {
                    String peerUserName=cloudIn.readLine();
                    String peerIP=cloudIn.readLine();
                    int peerPort = java.lang.Integer.parseInt(cloudIn.readLine());
                    if(peerUserName.equalsIgnoreCase(Globals.GlobalData.UserID)
                            && peerIP.equalsIgnoreCase(Globals.GlobalData.myIP) && peerPort==Globals.GlobalData.serverPort)
                        continue;// do not add myself into tracking list
                    Globals.GlobalData.peerTracker.addPeer(peerUserName, peerIP, peerPort);
                    Globals.log.message("Tracker Adding "+peerUserName+" ip: "+peerIP+":"+peerPort);
                }
                else if(response.equalsIgnoreCase("streaming"))
                {
                    String streaminVideo=cloudIn.readLine();
                    Globals.GlobalData.videoLibrary.updateStreamdetails(streaminVideo);
                    Globals.log.message("live video Stream"+streaminVideo);
                    MainUI.reloadVideoListFromLibrary();
                }
                else if(response.equalsIgnoreCase("NewStreamLive"))
                {
                    String streaminVideo=cloudIn.readLine();
                    Globals.GlobalData.videoLibrary.updateStreamdetails(streaminVideo);
                    Globals.log.message("live video Stream"+streaminVideo);
                    MainUI.reloadVideoListFromLibrary();
                }
                else if(response.equalsIgnoreCase("StreamDead"))
                {
                    String Video=cloudIn.readLine();
                    Globals.GlobalData.videoLibrary.updateVideodetails(Video);
                    Globals.log.message("video Stream ended"+Video);
                    MainUI.reloadVideoListFromLibrary();
                }
                else if(response.equalsIgnoreCase("currentStreamingchunk"))
                {
                    String Video=cloudIn.readLine();
                    int currentStreamingChunk = Integer.parseInt(cloudIn.readLine());
                    Globals.log.message("CurrentStreamingChuink for "+Video+" is chunk "+currentStreamingChunk);
                    Globals.GlobalData.sendToFFMPEG.queueFileToStream(Video, currentStreamingChunk);
                    uploaddownload.ChunkCrawler crawlFrom = new ChunkCrawler(currentStreamingChunk,Video);
                    crawlFrom.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Globals.log.error("cloud connection terminated relogin.");
            LoginScreen login = new LoginScreen();
            login.show();
            LoginScreen.controller.connectionError.setVisible(true);
        }
        connecting = false;
    }
    public void requestStream(String filename) throws IOException
    {
        Globals.log.message("Requesting streaming of filename "+filename);
        toCloud.print("stream\r\n");
        toCloud.print(filename+"\r\n");
        toCloud.print("getCurrentStreamingChunk\r\n");
        toCloud.print(filename+"\r\n");
        toCloud.flush();
    }
}
