/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RTP;

/**
 *
 * @author Kiran
 */
public class RTPPacket {
    public int packetSize;
    public byte[] binData;
    public int timeStamp;
    public int seq;

    public RTPPacket() {
        packetSize = timeStamp = seq =0;
        binData = new byte[2048];
    }
    
}
