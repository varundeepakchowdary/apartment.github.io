/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartmentmanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author gudhe
 */
public class OwnerSceneController implements Initializable {

    @FXML
    private AnchorPane ownRentAnchor;
    @FXML
    private Button addOwnBtn;
    @FXML
    private TextField ownName;
    @FXML
    private TextField ownContact;
    @FXML
    private TextArea ownAdrs;
    @FXML
    private TableView<?> ownTable;
    @FXML
    private Label warnMsg;
DisplayDatabase ownerData = new DisplayDatabase();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ownerData.buildData(ownTable, "Select * from OwnerTable;");
        
    }    
String nameO ="";
String cntcO ="";
String addO ="";
    
       
        
        
    




    private boolean getOwnerFields() {
        nameO = ownName.getText();
        cntcO = ownContact.getText();
        addO = ownAdrs.getText();
     
        if(nameO==null || nameO.isEmpty())
        {
            warnMsg.setText("Enter Owner's Name");
            ownName.requestFocus();
            return false;
        }
         if(cntcO==null || cntcO.isEmpty())
        {
            warnMsg.setText("enter Owner's Contact");
            ownName.requestFocus();
            return false;
        }
          if(addO==null || addO.isEmpty())
        {
            warnMsg.setText("enter Owner's Address");
            ownName.requestFocus();
            return false;
        }
        return true;
        
    }

    private void clearAllFields() {
        ownName.clear();
        ownContact.clear();
        ownAdrs.clear();
        
        
    }

    @FXML
    private void deleteOwner(ActionEvent event) {
        int index = ownTable.getSelectionModel().getSelectedIndex();
         ObservableList data = ownerData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "OwnerTable");
         
       ownerData.buildData(ownTable, "Select * from OwnerTable;");
    }

    @FXML
    private void addOwner(ActionEvent event) {
         
        boolean val= getOwnerFields();
       if(!val){
       return;
       }
       Connection c;
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO OwnerTable (OwnerName,OwnerContact,OwnerAddress)VALUES("+
"'"+nameO+"',\n" +
"'"+cntcO+"',\n" +                 
"'"+addO+"');";            
         
            c.createStatement().execute(query);
             c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(OwnerSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
      ownerData.buildData(ownTable, "Select * from OwnerTable;");
        
    }
    
}
