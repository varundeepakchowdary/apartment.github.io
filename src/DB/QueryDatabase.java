
package DB;
import java.sql.Connection;  
 import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;
public class QueryDatabase {
    
    
     public static ResultSet QueryDatabase(String query){
        ResultSet rs = null;
         Connection c ;  
        try { 
            c = DBConnection.connect();
        
        rs = c.createStatement().executeQuery(query);
        }catch(SQLException ex){
             System.out.println("Queried: "+query + "Error in sql");
        }
        
        
        return rs;  
    }

   
  
}
