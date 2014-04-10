/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Globals;

import Tracker.PeerTracker;
import Video.VideoLibrary;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import kliveserver.CloudConnection;
import uploaddownload.SendToFFMPeg;

/**
 * All global data will be stored here.
 * @author Kiran
 */
public class GlobalData {
    public static String UserID = "USER"+System.currentTimeMillis();
    public static String RTPVideoStorePath = "C:\\Users\\home\\Videos\\KliveCache";
    public static String ffplayPath = "Video\\ffplay.exe";
    public static String cloudIP = "";
    public static int cloudPort = 8080;
    public static volatile String myIP = "";
    public static volatile int serverPort = 8080;
    public static boolean logEnabled = true;
    public static volatile VideoLibrary videoLibrary = null;
    public static volatile CloudConnection connection = null;
    public static String RTPStreamingAddress="224.1.1.2";
    public static int RTPStreamPort=9999;
    public static int videoSegmentLength=5;//seconds
    public static SendToFFMPeg sendToFFMPEG;
    public static volatile PeerTracker peerTracker;
    public static void init()
    {
        RTPVideoStorePath = System.getProperty("java.io.tmpdir")+"\\KliveCache";
        videoLibrary = new VideoLibrary();
        sendToFFMPEG = new SendToFFMPeg();
        try{
            InputStream inputStream = videoLibrary.getClass().getResourceAsStream("/Video/ffplay.exe");
            File ffplay = new File(RTPVideoStorePath+"\\ffplay.exe");
            copyFile(inputStream, new FileOutputStream(ffplay));
            ffplayPath = ffplay.getAbsolutePath();
            inputStream.close();
        } 
        catch (IOException e) {
          e.printStackTrace();
        }
        sendToFFMPEG.start();
        peerTracker = new PeerTracker();
    }
    private static void copyFile(InputStream in, OutputStream out) throws IOException 
    {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
        out.close();
    }
}
