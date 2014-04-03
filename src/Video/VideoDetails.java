/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Video;

/**
 * Class to store details about a video and its rtp files.
 * @author Kiran
 */
public class VideoDetails {
    public String fileName;
    public int avgBitRate;//in bits per sec
    public boolean streamingLive;

    public VideoDetails() {
        fileName = null;
        avgBitRate = 0;
        streamingLive = false;
    }
    
}
