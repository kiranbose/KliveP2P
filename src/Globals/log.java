/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Globals;

import UI.MainUI;

/**
 *
 * @author home
 */
public class log {
    public static void message(String msg)
    {
        try
        {
            if(GlobalData.logEnabled)
                System.out.println(msg);
            if(MainUI.mainUIController!=null)
                MainUI.mainUIController.logArea.appendText(msg+"\r\n");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void Progress(String msg)
    {
        try
        {
            if(GlobalData.logEnabled)
                System.out.print(msg);
            if(MainUI.mainUIController!=null)
                MainUI.mainUIController.logArea.appendText(msg);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void error(String msg)
    {
        try
        {
            if(GlobalData.logEnabled)
                System.err.println(msg);
            if(MainUI.mainUIController!=null)
                MainUI.mainUIController.logArea.appendText(msg+"\r\n");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
