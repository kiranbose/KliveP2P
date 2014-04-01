/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Globals;

/**
 *
 * @author home
 */
public class log {
    public static void message(String msg)
    {
        if(GlobalData.logEnabled)
            System.out.println(msg);
    }
    public static void error(String msg)
    {
        if(GlobalData.logEnabled)
            System.err.println(msg);
    }
}
