/*@author kiran bose
 * create and search for video store files
 * create a library. add files to library.
 * call rtpfilegenerator class, where it calls vlc and stream.
 * Populates Vector<videolist> and initialze videolist elements.
 */

package Video;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

/**
 * maintains a list of video details
 * @author Kiran
 */
public class VideoLibrary {
    public Vector<VideoDetails> videoList;
    
    public VideoLibrary() {
        videoList = new Vector(50);
        createIndex();
    }
    
    public void createIndex()
    {
        try {
            File rtpVideoFolder= new File(Globals.GlobalData.RTPVideoStorePath);
            if(!rtpVideoFolder.exists())
            {
                rtpVideoFolder.mkdirs();
                Globals.log.message("created RTPVideo cache Store at : "+rtpVideoFolder.getCanonicalPath());
            }
            Globals.log.message("RTPVideo cache Store Location : "+rtpVideoFolder.getCanonicalPath());
            if(rtpVideoFolder.list().length>0)
            {
                for(final File fileEntry : rtpVideoFolder.listFiles())
                {
                    addVideo(fileEntry.getCanonicalPath());
                }
            }
            else
            {
                Globals.log.error(rtpVideoFolder.getCanonicalPath()+ " rtp cache folder is empty.");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addVideo(String filePath)
    {
        File fileEntry = new File(filePath);
        VideoDetails obj=new VideoDetails();
        obj.fileName=fileEntry.getName();
        videoList.add(obj);
        Globals.log.message("added video "+obj.fileName+" to library"); 
    }
    
    public void updateVideodetails(String fileName)
    {
        VideoDetails obj = getVideoDetails(fileName);
        if(obj == null)
            obj=new VideoDetails();
        obj.fileName=fileName;
        obj.streamingLive = false;
        if(getVideoDetails(fileName)==null)
            videoList.add(obj);
        Globals.log.message("video details added "+obj.fileName+" to library"); 
    }
    
    public void updateStreamdetails(String fileName)
    {
        VideoDetails obj = getVideoDetails(fileName);
        if(obj == null)
            obj=new VideoDetails();
        obj.fileName=fileName;
        obj.streamingLive = true;
        if(getVideoDetails(fileName)==null)
            videoList.add(obj);
        Globals.log.message("video details added "+obj.fileName+" to library"); 
    }
    
    public VideoDetails getVideoDetails(String videoFileName)
    {
        for(int i=0;i<videoList.size();i++)
        {
            if(videoList.elementAt(i).fileName.equals(videoFileName))
                return videoList.elementAt(i);
        }
        return null;
    }
    
}
