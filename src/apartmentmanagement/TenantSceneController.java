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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author gudhe
 */
public class TenantSceneController implements Initializable {

    @FXML
    private Button addTenantBtn;
    @FXML
    private TextField tenantName;
    @FXML
    private TextField tenantContact;
    @FXML
    private TextArea tenantAdrs;
DisplayDatabase tenantData = new DisplayDatabase();
    @FXML
    private TableView<?> tenantTable;
    @FXML
    private Label warnMsg;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         tenantData.buildData(tenantTable, "Select * from TenantTable;");
      
    }    
String nameT ="";
String cntcT ="";
String addT ="";
    @FXML
    private void addTenant(ActionEvent event) {
          Connection c;
        boolean val= getOwnerFields();
       if(!val){
       return;
       }
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO TenantTable (TenantName,TenantContact,TenantAddress)VALUES("+
"'"+nameT+"',\n" +
"'"+cntcT+"',\n" +                 
"'"+addT+"');";            
         
            c.createStatement().execute(query);
             c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(OwnerSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
      tenantData.buildData(tenantTable, "Select * from TenantTable;");
    }
       
        
        
    




    private boolean getOwnerFields() {
        nameT = tenantName.getText();
        cntcT = tenantContact.getText();
        addT = tenantAdrs.getText();
     
        if(nameT==null || nameT.isEmpty())
        {
            warnMsg.setText("enter Owner's Name");
            tenantName.requestFocus();
            return false;
        }
         if(cntcT==null || cntcT.isEmpty())
        {
            warnMsg.setText("enter Owner's Contact");
            tenantContact.requestFocus();
            return false;
        }
          if(addT==null || addT.isEmpty())
        {
            warnMsg.setText("enter Owner's Address");
            tenantAdrs.requestFocus();
            return false;
        }
        return true;
        
    }

    private void clearAllFields() {
        tenantName.clear();
        tenantContact.clear();
        tenantAdrs.clear();
        
        
    }
@FXML
    private void deleteTenant(ActionEvent event) {
        int index = tenantTable.getSelectionModel().getSelectedIndex();
         ObservableList data = tenantData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "TenantTable");
         
       tenantData.buildData(tenantTable, "Select * from TenantTable;");
    }

    

    
    
}
