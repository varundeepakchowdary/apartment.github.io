/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartmentmanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author gudhe
 */
public class PaymentSceneController implements Initializable {

    @FXML
    private AnchorPane payAnchor;
    @FXML
    private DatePicker pDate;
    @FXML
    private Label tName;
    @FXML
    private TextField bName;
    @FXML
    private TextField flatNum;
    @FXML
    private Label rentL;
    @FXML
    private TableView<?> PayTable;
    
ObservableList<String> bList=  FXCollections.observableArrayList(); 
ObservableList<String> fList = FXCollections.observableArrayList();
    

    DisplayDatabase pData = new DisplayDatabase();
    @FXML
    private Label warnMsg;
    @FXML
    private Button fBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ResultSet rs = QueryDatabase.QueryDatabase("Select Buildingname from BuildingTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    bList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PaymentSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(bName,bList); 
       
       
      
      auto.setOnAutoCompleted( 
      
      e -> {
      getFlats();
      });
      
      
      
         pData.buildData(PayTable, "Select * from paymentTable;");
    }    
String buildName = "";
String nameT = "";
String flat = "";
double rent = 0;
LocalDate date = null;

    @FXML
    private void makePayment(ActionEvent event) {
        
        
       if(!getBFields()){
        return;
       }
       
       ResultSet rs = QueryDatabase.QueryDatabase("Select * from paymenttable where BuildingName='"+buildName+"' AND FlatNum='"+flat+"'  AND Month(Date)='"+date.getMonthValue()+"';");
       if(rs!=null){
           try {
               if(rs.next()){
                   warnMsg.setText("Payment already exists for the selected Month.");
                   return;
               }
           } catch (SQLException ex) {
               Logger.getLogger(PaymentSceneController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       Connection c;
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO PaymentTable (Date,Buildingname,tName,FlatNum,PaidRent)VALUES("+
                        "'"+date+"',\n" +
                        "'"+buildName+"',\n" +
                        "'"+nameT+"',\n" +
                      "'"+flat+"',\n" +
                    "'"+rent+"');";                    

            c.createStatement().execute(query);
       }catch (SQLException ex) {
            Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         pData.buildData(PayTable, "Select * from paymentTable;");
    }
    
AutoCompletionBinding<String> auto3;

    @FXML
    private void getFlats() {
        
         ResultSet rs = QueryDatabase.QueryDatabase("Select Flatnumber from ApartmentTable where BuildingName = '"+bName.getText()+"';");
        if(rs!=null){
            try {
                fList.clear();
                while(rs.next()){
                  fList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PaymentSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
     
      auto3 = TextFields.bindAutoCompletion(flatNum,fList);
        auto3.setOnAutoCompleted( 
      
      e -> {
      getRate();
      });                
        
    }

    @FXML
    private void getRate() {
      
         
         ResultSet rs = QueryDatabase.QueryDatabase("Select Rent,Tenant from ApartmentTable where Flatnumber = '"+flatNum.getText()+"' AND BuildingName='"+bName.getText()+"';");
        if(rs!=null){
            try {
                if(rs.next()){
                  rent =  Double.parseDouble(rs.getString(1));
                  rentL.setText(rs.getString(1));
                  tName.setText(rs.getString(2));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PaymentSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        
    }

    private boolean getBFields() {
             buildName = bName.getText();
            nameT = tName.getText();
            flat = flatNum.getText();
            rent = Double.parseDouble(rentL.getText());
            date = pDate.getValue();

            if(buildName==null || buildName.isEmpty()){
            bName.requestFocus();
            return false;
        }
        
        if(flat==null || flat.isEmpty()){
            flatNum.requestFocus();
            return false;
        }
        
            return true;
                    
    }

    private void clearAllFields() {
     
            bName.clear();
           tName.setText("");
          flatNum.clear();
          rentL.setText("0.0");
            pDate.setValue(LocalDate.now());
        
    }

    @FXML
    private void mDeletePayment(ActionEvent event) {
        int index = PayTable.getSelectionModel().getSelectedIndex();
         ObservableList data = pData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "PaymentTable");
         
        pData.buildData(PayTable, "Select * from paymentTable;");
        
    }

  
}
