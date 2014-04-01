/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Globals;

import VideoStore.VideoLibrary;

/**
 * All global data will be stored here.
 * @author Kiran
 */
public class GlobalData {
    public static String UserID = "USER"+System.currentTimeMillis();
    public static String RTPVideoStorePath = "./KLiveServer/RTPVideos";
    public static String cloudIP = "";
    public static int cloudPort = 8080;
    public static int serverPort = 8080;
    public static boolean logEnabled = true;
    public static VideoLibrary videoLibrary;
    public static void init()
    {
        videoLibrary = new VideoLibrary();
    }
}
