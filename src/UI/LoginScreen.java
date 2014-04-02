/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author home
 */
public class LoginScreen {
    public static LoginscreenController controller = null;
    public LoginScreen() {
    }
    
    public void show()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //javaFX operations should go here
                try {
                    Parent root = FXMLLoader.load(klivep2p.KliveP2P.class.getResource("/UI/loginscreen.fxml"));

                    Scene scene = new Scene(root,757,566);

                    klivep2p.KliveP2P.mainStage.setScene(scene);
                    klivep2p.KliveP2P.mainStage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
