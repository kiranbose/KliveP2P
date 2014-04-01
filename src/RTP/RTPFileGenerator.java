/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RTP;

import java.io.DataInputStream;

/**
 * Generates an rtp stream. Read Files from Library. creates stream and save it to RTPVideos folder
 * @author kiran bose
 */
public class RTPFileGenerator extends Thread{

    String srcFilePath;
    String destFolderPath;
    public static int videoSegmentLength=5;
    static int RTPStreamPort=1234;
    /**
     * createes a thread to transcode srcfile to rtp streams
     * returns immediately, but will continue creating the rtp stream in another thread.
     * use vlc player to generate the rtp stream
     * @param srcFilePath
     * @param destFolderPath
     */
    public static void createRtpFileSegments(String srcFilePath, String destFolderPath)
    {
        RTPFileGenerator fileGen = new RTPFileGenerator();
        fileGen.srcFilePath = srcFilePath;
        fileGen.destFolderPath = destFolderPath;
        fileGen.start();
    }
    
    @Override
    public void run()
    {
        Globals.log.message("Generating RTP stream file from "+ srcFilePath);
        Globals.log.message("RTP folder location "+ destFolderPath);
        
        try
        {
            RTPStreamPort++;
            Globals.log.message("starting video transcoder/streamer ");
            String cmd="cvlc -vvv "+srcFilePath+" --sout #rtp{dst=localhost,port="+RTPStreamPort+",mux=ts,ttl=1} ";
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmd);

            RTP.FileCreator.Filegen(srcFilePath,destFolderPath,RTPStreamPort);
            p.destroy();
            

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        
    }
}
