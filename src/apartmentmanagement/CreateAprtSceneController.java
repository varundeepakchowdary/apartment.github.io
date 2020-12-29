
package apartmentmanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import Model.Flats;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class CreateAprtSceneController implements Initializable {

    @FXML
    private AnchorPane apaAnchor;
    @FXML
    private TextField bName;
    @FXML
    private TextField bOwner;
    @FXML
    private TextArea bAddress;
  
    
    @FXML
    private TextField faltTenant;
    @FXML
    private TextField flatRent;
    @FXML
    private DatePicker flatStartDate;
    @FXML
    private TextField bFloors;
    @FXML
    private TableView<Flats> flatTable;
    @FXML
    private TableView<?> bTable;
DisplayDatabase aData = new DisplayDatabase();
DisplayDatabase fData = new DisplayDatabase();
ObservableList<String> oList = FXCollections.observableArrayList(); 
ObservableList<String> tList = FXCollections.observableArrayList(); 
    @FXML
    private TextField flatNum;
    @FXML
    private Label warnMsg;
    @FXML
    private Button btnSub;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        aData.buildData(bTable, "Select * from BuildingTable;");
        createFlatTable();
         ResultSet rs = QueryDatabase.QueryDatabase("Select OwnerName from OwnerTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    oList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(bOwner,oList); 
      
       rs = QueryDatabase.QueryDatabase("Select TenantName from TenantTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    tList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      AutoCompletionBinding<String> auto1 = TextFields.bindAutoCompletion(faltTenant,tList); 
      
    }    
String buildName = "";
String oName = "";
String buildAdd = "";
String buildFloors = "";
String fNum = "";
String fTenant = "";  
LocalDate fStartDate =null;
double rent =0;

ObservableList<Flats> flatList = FXCollections.observableArrayList();

    @FXML
    private void addFlat(ActionEvent event) {
        
        if(!getFlatFields()){
            return;
        }
         buildName = bName.getText();
            if(buildName==null || buildName.isEmpty()){
               warnMsg.setText("Pls enter building name.");
             bName.requestFocus();
             return;
             }
           ResultSet rs = QueryDatabase.QueryDatabase("Select Tenant from ApartmentTable where Buildingname='"+buildName+"' AND FlatNumber='"+fNum+"';");
        if(rs!=null){
            try {
                if(rs.next()){
                    warnMsg.setText("This flat is already Occupied by: "+rs.getString(1));
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
           
           flatList.add(new Flats(fNum,fTenant,rent,fStartDate));
        flatTable.setItems(flatList);
        
        clearFields();
    }

    private boolean getFlatFields() {

        fNum = flatNum.getText();
        fTenant = faltTenant.getText();
        String tempRent = flatRent.getText();
        fStartDate = flatStartDate.getValue();
        
        
           if(fNum==null || fNum.isEmpty()){
        warnMsg.setText("Pls enter Flat Number.");
        flatNum.requestFocus();
         return false;
        }     

          if(fTenant==null || fTenant.isEmpty()){
        warnMsg.setText("Pls enter Tenant Name.");
        faltTenant.requestFocus();
         return false;
        }  
          
        if(tempRent==null || tempRent.isEmpty()){
        warnMsg.setText("Pls enter rent amount.");
        flatRent.requestFocus();
         return false;
        }
        rent = Double.parseDouble(flatRent.getText());
        
        if(fStartDate==null){
         warnMsg.setText("Pls enter rent startDate.");
        flatStartDate.requestFocus();
         return false;
        }
         return true;
    }

    private void clearFields() {
      
        flatNum.clear();
        faltTenant.clear();
        flatRent.clear();
        flatStartDate.setValue(LocalDate.now());

    }
    
    
    
    
    


    private void createFlatTable() {
       
      
        
           TableColumn col_fNum = new TableColumn("FlatNumber");
        col_fNum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Flats,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Flats, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getFlatNum());              
           }            
         });  
        flatTable.getColumns().addAll(col_fNum);
        
           TableColumn col_tenant = new TableColumn("Tenant");
        col_tenant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Flats,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Flats, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getTenant());              
           }            
         }); 
        
        flatTable.getColumns().addAll(col_tenant);
        
        
        TableColumn col_rent = new TableColumn("Rent");
        col_rent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Flats,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Flats, String> param) {                                                 
             return new SimpleStringProperty(String.valueOf(param.getValue().getRent()));              
           }            
         }); 
        
        flatTable.getColumns().addAll(col_rent);
        
         TableColumn col_payDAy = new TableColumn("Payday");
        col_payDAy.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Flats,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Flats, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getStartDate().toString());              
           }            
         }); 
        
        flatTable.getColumns().addAll(col_payDAy);
        
    }
    @FXML
    private void DeleteFlat(ActionEvent event) {
        int index = flatTable.getSelectionModel().getSelectedIndex();
        flatList.remove(index);
        flatTable.refresh();
    }

    @FXML
    private void addBuilding(ActionEvent event) {
        
       boolean val =  getBFields();
       if(!val){
       return;
       }
         String query;
       Connection c;
       try{
            if(!update){
           c = DBConnection.connect();
           query = "INSERT INTO BuildingTable (Buildingname,Address,Owner,Floors)VALUES("+
"'"+buildName+"',\n" +
"'"+buildAdd+"',\n" +
"'"+oName+"',\n" +
"'"+buildFloors+"');";                    
         
            c.createStatement().execute(query);
             }else{
              c = DBConnection.connect();
            query = "Update BuildingTable set Buildingname='"+buildName+"',Address='"+buildAdd+"',Owner='"+oName+"',"
                   + "Floors='"+buildFloors+"'Where Id='"+id+"';";
                  System.out.println(query);
           c.createStatement().executeUpdate(query);
           
           c.createStatement().execute("Delete from ApartmentTable where Buildingname='"+buildName+"';");
          
           
            }
            for(Flats i: flatList){
          
             query = "INSERT INTO ApartmentTable (BuildingName,Flatnumber,Tenant,Rent,StartDate)VALUES("+
                "'"+buildName+"',\n" +
                "'"+i.getFlatNum()+"',\n" +
                "'"+i.getTenant()+"',\n" +     
                "'"+i.getRent()+"',\n" +     
                "'"+i.getStartDate()+"');";                    
         
            c.createStatement().execute(query);
            
            }
            
            
            
            c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         aData.buildData(bTable, "Select * from BuildingTable;");
    }


    private boolean getBFields() {
        
        buildName = bName.getText();
        oName = bOwner.getText();
        buildAdd = bAddress.getText();
        buildFloors = bFloors.getText();
        
        if(buildName==null || buildName.isEmpty()){
            bName.requestFocus();
            warnMsg.setText("Pls enter Building Name.");
            return false;
        }
        
        if(oName==null || oName.isEmpty()){
            bOwner.requestFocus();
            warnMsg.setText("Pls enter Owner Name.");
            return false;
        }
        
        if(buildAdd==null || buildAdd.isEmpty()){
            bAddress.requestFocus();
            warnMsg.setText("Pls enter Apartment Address.");
            return false;
        }
        if(buildFloors==null || buildFloors.isEmpty()){
            bFloors.requestFocus();
            warnMsg.setText("Pls enter Number of Floors.");
            return false;
        }
        
        return true;
    }

   

   

    @FXML
    private void deleteAprt(ActionEvent event) {
         int index = bTable.getSelectionModel().getSelectedIndex();
         ObservableList data = aData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "BuildingTable");
         
         aData.buildData(bTable, "Select * from BuildingTable;");
    }

    @FXML
    private void searchBuilding(ActionEvent event) {
         buildName = bName.getText();
       
        
        if(buildName==null || buildName.isEmpty()){
            bName.requestFocus();
            warnMsg.setText("Pls enter Building Name.");
            return;
        }
        
        ResultSet rs = QueryDatabase.QueryDatabase("Select * from ApartmentTable where Buildingname='"+buildName+"';");
        if(rs!=null){
            try {
                flatList.clear();
                while(rs.next()){
                        flatList.add(new Flats(rs.getString(3),rs.getString(4),Double.parseDouble(rs.getString(5)),LocalDate.parse(rs.getString(6))));   
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
                     warnMsg.setText("Building not found.");
                    return;
          }
           
          
        flatTable.setItems(flatList);
        
        
        
    }
     private void clearAllFields() {
        bName.clear();
        bOwner.clear();
        bAddress.clear();
        bFloors.clear();
        flatList.clear();
        flatTable.refresh();
        warnMsg.setText("");
             update=false;
        btnSub.setText("Update");
    }
int id;
boolean update = false;
    
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML
    private void updateAprt(ActionEvent event) {
        
    
        int index = bTable.getSelectionModel().getFocusedIndex();
      ObservableList<ObservableList> data = aData.getData();
      ObservableList<String> itemData = data.get(index);
      
   
      id = Integer.parseInt(itemData.get(0));
      bName.setText(itemData.get(1));
        bOwner.setText(itemData.get(3));
        bAddress.setText(itemData.get(2));
        bFloors.setText(itemData.get(4));             
        
 
        ResultSet rs= QueryDatabase.QueryDatabase("Select * from apartmenttable where Buildingname='"+itemData.get(1)+"';");
       
        if(rs!=null){
            try {
                flatList.clear();
                while(rs.next()){
                        flatList.add(new Flats(rs.getString(3),rs.getString(4),Double.parseDouble(rs.getString(5)),LocalDate.parse(rs.getString(6))));   
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(CreateAprtSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
                     warnMsg.setText("Building not found.");
                    return;
          }
           
          
        flatTable.setItems(flatList);
            
        
        
        update=true;
        btnSub.setText("Update");
    }
    }

    
    

    
    
    

