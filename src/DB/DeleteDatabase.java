package DB;

import java.sql.Connection;
import javafx.collections.FXCollections;

public class DeleteDatabase {
      
      
       public static void deleteRecord(int id,String tableName){
        Connection c ; 
           
           try{  
         c = DBConnection.connect(); 
        
         String query = "Delete from "+tableName+" where id='"+id+"';";
         c.createStatement().execute(query);
         c.close();
         
           
       }catch(Exception e){  
        System.out.println("Error on Building Data");        
      }  
       }
}
