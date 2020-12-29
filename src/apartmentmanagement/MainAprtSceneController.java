/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartmentmanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author gudhe
 */
public class MainAprtSceneController implements Initializable {
    
    @FXML
    private ToggleButton setPaySceneBtn;
    @FXML
    private ToggleGroup g1;
    @FXML
    private ToggleButton setOwnSceneBtn;
    @FXML
    private AnchorPane cenAnchor;
    @FXML
    private BorderPane rootLayout;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeScene("CreateAprtScene.fxml");

        
    }    

    @FXML
    private void SetPayScene(ActionEvent event) {
        changeScene("PaymentScene.fxml");
    }

    @FXML
    private void SetOwnerScene(ActionEvent event) {
        changeScene("OwnerScene.fxml");
    }

    @FXML
    private void SetAprtScene(ActionEvent event) {
        changeScene("CreateAprtScene.fxml");
    }

    @FXML
    private void SetTenantScene(ActionEvent event) {
        changeScene("TenantScene.fxml");
    }
    
    
    public  void changeScene(String scenePath){
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
    try{
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        }
        catch(Exception e){
        }
}
}