/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;


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
    public ListView VODList;
    @FXML
    public ListView liveStreamList;
    @FXML
    public TextArea logArea;
    //@FXML
    //ScrollBar logPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MainUI.mainUIController = this;
    }    
    
    @FXML
     public void VODClicked(MouseEvent evt) throws IOException
     {
        if(evt.getClickCount() == 2)   
        {
            Globals.GlobalData.connection.requestStream(VODList.getSelectionModel().getSelectedItem().toString());
        }
     }

    }    


