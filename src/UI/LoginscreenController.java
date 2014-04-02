/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kliveserver.CloudConnection;

/**
 * FXML Controller class
 *
 * @author home
 */
public class LoginscreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public AnchorPane pane;
    @FXML
    public Button button;
    @FXML
    public TextField userNameField;
    @FXML
    public Text enterCloudIP;
    @FXML
    public VBox connectionError;
    @FXML
    public TextField cloudIP;
    @FXML
    public Text noUsernameError;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoginScreen.controller = this;
    }
    
     @FXML
     public void login(ActionEvent evt) throws IOException
     {
        enterCloudIP.setVisible(false);
        noUsernameError.setVisible(false);
        if(userNameField.getText().trim().isEmpty())
        {
            noUsernameError.setVisible(true);
            return;
        }
        if(cloudIP.getText().trim().isEmpty())
        {
            enterCloudIP.setVisible(true);
            return;
        }
        else
        {
            enterCloudIP.setVisible(false);
            noUsernameError.setVisible(false);
        }
        Globals.GlobalData.cloudIP = cloudIP.getText();
        Globals.GlobalData.UserID = userNameField.getText();
        Globals.log.message("attempting to connect to cloud server at "+Globals.GlobalData.cloudIP+":"+Globals.GlobalData.cloudPort);
         Globals.GlobalData.connection = new CloudConnection();
         Globals.GlobalData.connection.start();
     }
    
}
