/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klivep2p;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import kliveserver.SocketListener;

/**
 *
 * @author home
 */
public class KliveP2P extends Application {
    
      @Override
    public void start(Stage stage) throws Exception {
        Globals.GlobalData.init();
        SocketListener server = new SocketListener();
        server.StartServerOn(Globals.GlobalData.serverPort);
        Parent root = FXMLLoader.load(getClass().getResource("/UI/loginscreen.fxml"));
        
        Scene scene = new Scene(root,757,566);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
