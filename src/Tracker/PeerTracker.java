/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tracker;

import java.util.ArrayList;

/**
 *
 * @author Arun_
 */
public class PeerTracker {
    
    public ArrayList<PeerDetails> peerList;
    
    public PeerTracker() {
        peerList = new ArrayList<>();
    }
    
    public void addPeer(String userName,String ip,int port)
    {
        PeerDetails details = getDetails(userName);
        if( details == null)
        {
            details = new PeerDetails();
            peerList.add(details);
        }
        details.userName = userName;
        details.ip = ip;
        details.port = port;
    }
    
    public PeerDetails getDetails(String userName)
    {
        for(int i=0;i<peerList.size();i++)
        {
            if(peerList.get(i).userName.compareToIgnoreCase(userName) == 0)
                return peerList.get(i);
        }
        return null;
    }
    
    public boolean removeDetails(String userName)
    {
        for(int i=0;i<peerList.size();i++)
        {
            if(peerList.get(i).userName.compareToIgnoreCase(userName) == 0)
            {
                peerList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public void removeAll()
    {
       peerList.clear();
    }
}
