/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author home
 */
public class FXMLDocumentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ListView VODList;
    @FXML
    ListView liveStreamList;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ShowMainUI.mainUIController = this;
    }    
    
}
