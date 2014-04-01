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
    private AnchorPane pane;
    @FXML
    Button button;
    @FXML
    TextField userNameField;
    @FXML
    Text enterCloudIP;
    @FXML
    VBox connectionError;
    @FXML
    TextField cloudIP;
    @FXML
    Text noUsernameError;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
     }
    
}
