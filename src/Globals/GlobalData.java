/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Globals;

import Video.VideoLibrary;
import kliveserver.CloudConnection;

/**
 * All global data will be stored here.
 * @author Kiran
 */
public class GlobalData {
    public static String UserID = "USER"+System.currentTimeMillis();
    public static String RTPVideoStorePath = "C:\\Users\\home\\Videos\\KliveCache";
    public static String ffplayPath = "C:\\Users\\home\\Documents\\ffmpeg\\bin\\ffplay.exe";
    public static String cloudIP = "";
    public static int cloudPort = 8080;
    public static int serverPort = 8080;
    public static boolean logEnabled = true;
    public static VideoLibrary videoLibrary = null;
    public static CloudConnection connection = null;
    public static String RTPStreamingAddress="224.1.1.2";
    public static int RTPStreamPort=9999;
    public static void init()
    {
        videoLibrary = new VideoLibrary();
    }
}
