/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kliveserver;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * this class gives a list of all the ip addresses of this machine.
 * @author kiran
 */
public class NetworkInterfaces {
    public static ArrayList<String> getNetAddresses()
    {
        ArrayList<String> intrefaces = new ArrayList();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets))
            {
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if(inetAddress.isSiteLocalAddress())
                    intrefaces.add(inetAddress.getHostAddress());
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(NetworkInterfaces.class.getName()).log(Level.SEVERE, null, ex);
        }
        return intrefaces;
    }
}
