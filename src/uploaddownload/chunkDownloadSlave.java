/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uploaddownload;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;
import static uploaddownload.ChunkCrawler.lock;

/**
 *
 * @author Arun_
 */
public class chunkDownloadSlave extends Thread{

    String alternateSources;
    String fileName;
    int chunkNumber;

    Socket connection;
    
    public volatile boolean downloadomplete;
    
    public chunkDownloadSlave(String alternateSources, String fileName, int chunkNumber) {
        this.alternateSources = alternateSources;
        this.fileName = fileName;
        this.chunkNumber = chunkNumber;
        downloadomplete = false;
    }    
    
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        connectToRandomSource(5);
    }
    
    void connectToRandomSource(int numberOfRetries)
    {
        if(numberOfRetries <= 0)
            return;
        File rtpCachePath=new File(Globals.GlobalData.RTPVideoStorePath+"\\"+fileName);
        File chunkFile = new File(rtpCachePath.getAbsolutePath()+"\\chunk"+chunkNumber);
        if(chunkFile.exists())
            return;
        String ip = "";
        int port = 8080;
        StringTokenizer tokens = new StringTokenizer(alternateSources);
        Random gen = new Random();
        int seed = tokens.countTokens()/2;
        if(seed<=0)
            seed = 1;
        int random = gen.nextInt(seed);
        if(random<=0)
            random = 1;
        for(int i=0;i<random&&tokens.hasMoreTokens();i++) 
        {
            String ipOld = tokens.nextToken(); //ip
            int portOld = java.lang.Integer.parseInt(tokens.nextToken()); //port
            if(!ipOld.trim().equalsIgnoreCase(Globals.GlobalData.myIP)&&portOld!=Globals.GlobalData.serverPort)
            {
                ip=ipOld;
                port = portOld;
            }
        }
        try
        {
            if(ip.isEmpty())
                return;
            Globals.log.message("AlternateSource chunk"+chunkNumber +" in "+ip+":"+port);
            connection = new Socket(ip, port);
            DataInputStream dis = new DataInputStream(connection.getInputStream());
            PrintStream ps= new PrintStream(connection.getOutputStream());
            ps.print("download\r\n");
            ps.print(Globals.GlobalData.UserID+"\r\n");
            ps.print(fileName+"\r\n");
            ps.print("chunk"+chunkNumber +"\r\n");    
            ps.flush();
            String msg=dis.readLine(); //chunksize or nochunk string
            if(msg.equalsIgnoreCase("nochunk"))//give 15 seconds for chunk download to succeed.
            {
                ps.close();
                connection.close();
                Globals.log.message("No altSrc chunk"+chunkNumber +" in "+ip+":"+port);
                connectToRandomSource(numberOfRetries-1);
            }
            else
            {
                int chunkSize=Integer.parseInt(dis.readLine());
                dis.readLine();//the string "data" will be send from cloud
                File file = new File(rtpCachePath.getAbsolutePath()+"\\alt"+chunkNumber);
                if(chunkFile.exists() &&  chunkFile.isFile() && chunkFile.length()==chunkSize )                
                    return;                
                Globals.log.Progress("-<Altdwld:"+fileName+":"+chunkNumber +":"+chunkSize+">");
                FileOutputStream fo=new FileOutputStream(file);
                int sizeRead=0;    
                byte[] fileData = new byte[8196];
                while(sizeRead!=-1 ) 
                {
                    sizeRead = dis.read(fileData);
                    if(sizeRead>=0)
                    fo.write(fileData,0,sizeRead);
                }
                fo.close();
                ps.flush();
                ps.close();
                if(!chunkFile.exists())
                    file.renameTo(chunkFile);
                else
                    file.delete();
                downloadomplete = true;
                Globals.GlobalData.connection.sendCachedChunkDetails(fileName, chunkFile.getName());
                connection.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            connectToRandomSource(numberOfRetries-1);
        }
    }
    
}
