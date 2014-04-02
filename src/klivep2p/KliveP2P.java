/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klivep2p;

import UI.LoginScreen;
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
    public static Stage mainStage;
      @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Globals.GlobalData.init();
        SocketListener server = new SocketListener();
        server.setDaemon(true);
        server.StartServerOn(Globals.GlobalData.serverPort);
        LoginScreen login = new LoginScreen();
        login.show();
        System.err.println("asfjfkfvkudsagljgsadljfgdsljfgjdsa");
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
