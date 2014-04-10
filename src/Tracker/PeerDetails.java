/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tracker;

/**
 *
 * @author Arun_
 */
public class PeerDetails {
    public String userName;
    public String ip;
    public int port;

    public PeerDetails() {
        userName = "user";
        ip = "";
        port = Globals.GlobalData.serverPort;
    }
    
}
