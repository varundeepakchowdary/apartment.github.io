/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author gudhe
 */
public class Flats {

   
   
    public String getFlatNum() {
        return flatNum;
    }

    public void setFlatNum(String flatNum) {
        this.flatNum = flatNum;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Flats( String flatNum, String tenant, double rent, LocalDate startDate) {
      
        this.flatNum = flatNum;
        this.tenant = tenant;
        this.rent = rent;
        this.startDate = startDate;
    }

   
   
     String flatNum;
      String tenant;
       double rent;
        LocalDate startDate;
        
}
