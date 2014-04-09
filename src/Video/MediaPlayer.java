/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Video;

import java.io.IOException;

/**
 *
 * @author home
 */
public class MediaPlayer extends Thread{

    public static volatile Process playerProcess;
    public MediaPlayer() {
        playerProcess = null;
    }
    
    public static void restartMediaPlayer()
    {
        if(playerProcess != null)
        {
            playerProcess.destroy();
            playerProcess = null;
            Globals.log.message("media player closed.");
        }
        MediaPlayer player = new MediaPlayer();
        player.start();
    }
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        String cmd= "cmd /c start "+Globals.GlobalData.ffplayPath+" rtp://"+Globals.GlobalData.RTPStreamingAddress+":"+Globals.GlobalData.RTPStreamPort;
        Runtime rt= Runtime.getRuntime();
           try {
               playerProcess = rt.exec(cmd);
               Globals.log.message("starting media player\nffmpeg started");
               } catch (IOException ex) 
               {
                   Globals.log.error("error while opening ffmpeg libs");
                    ex.printStackTrace();
               }
    }
    
}
