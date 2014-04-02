/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author home
 */
public class MainUI {
    public static volatile FXMLDocumentController mainUIController = null;
    public MainUI() {
    }
    
    public void show()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //javaFX operations should go here
                try {
                    mainUIController = null;
                    Parent root = FXMLLoader.load(klivep2p.KliveP2P.class.getResource("/UI/FXMLDocument.fxml"));

                    Scene scene = new Scene(root,757,566);

                    klivep2p.KliveP2P.mainStage.setScene(scene);
                    klivep2p.KliveP2P.mainStage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public static void reloadVideoListFromLibrary()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //javaFX operations should go here
                ArrayList<String> videos = new ArrayList<>();
                ArrayList<String> streams = new ArrayList<>();        
                for(int i=0;i<Globals.GlobalData.videoLibrary.videoList.size();i++)
                {
                    if(Globals.GlobalData.videoLibrary.videoList.get(i).streamingLive)
                        streams.add(Globals.GlobalData.videoLibrary.videoList.get(i).fileName);
                    else
                        videos.add(Globals.GlobalData.videoLibrary.videoList.get(i).fileName);
                }
                ObservableList<String> vodList =FXCollections.observableList(videos);
                ObservableList<String> streamingList =FXCollections.observableList(streams);
                mainUIController.VODList.setItems(vodList);
                mainUIController.liveStreamList.setItems(streamingList); 
            }
        });
    }
}
