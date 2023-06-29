/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.TableAnnotation;
import database.ConnectionBase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mapping.BddObject;
import utilities.Ordering;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "v_monnaie_rate")
public class Exchange_rate {
    @ColumnField(column = "id_exchange_rate", primary_key = true)
    public String id_exchange_rate;
    
    @ColumnField(column = "date_exchange")
    public java.sql.Date date_exchange;
    
    @ColumnField(column = "id_monnaie1")
    public String id_monnaie1;
    
    @ColumnField(column = "id_monnaie2")
    public String id_monnaie2;
    
    @ColumnField(column = "value_rate")
    public Float value_rate;
    
    @ColumnField(column = "name_monnaie")
    public String name_monnaie;
    
    
     // Get latest value of a monnaie exchange
    public static float value_monnaie(String id_monnaie, Timestamp reference, Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            float result = 0;
            Exchange_rate ex = new Exchange_rate();
            ex.setId_monnaie1(id_monnaie);
            List<Exchange_rate> test = new ArrayList<>();
            
            List<Exchange_rate> all = BddObject.findByOrder("v_monnaie_rate", ex, "date_exchange", Ordering.DESC, connection);
            if(all.size() > 0){
                for(int i = 0; i < all.size(); i++){
                    if(all.get(i).getDate_exchange().before(reference) == true){
                        test.add(all.get(i));
                    }
                }
                if(test.size() > 0) return test.get(0).getValue_rate();
                else return 0;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the rate exchange of a monnaie. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
        
    }
    
    
    // Get latest value of a monnaie exchange
    public static float value_monnaie(String id_monnaie, Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            float result = 0;
            Exchange_rate ex = new Exchange_rate();
            ex.setId_monnaie1(id_monnaie);
            
            List<Exchange_rate> all = BddObject.findByOrder("v_monnaie_rate", ex, "date_exchange", Ordering.DESC, connection);
            if(all.size() > 0){
                return all.get(0).getValue_rate();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the rate exchange of a monnaie. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // Getters and Setters

    public String getId_exchange_rate() {
        return id_exchange_rate;
    }

    public void setId_exchange_rate(String id_exchange_rate) {
        this.id_exchange_rate = id_exchange_rate;
    }

    public Date getDate_exchange() {
        return date_exchange;
    }

    public void setDate_exchange(Date date_exchange) {
        this.date_exchange = date_exchange;
    }

    public String getId_monnaie1() {
        return id_monnaie1;
    }

    public void setId_monnaie1(String id_monnaie1) {
        this.id_monnaie1 = id_monnaie1;
    }

    public String getId_monnaie2() {
        return id_monnaie2;
    }

    public void setId_monnaie2(String id_monnaie2) {
        this.id_monnaie2 = id_monnaie2;
    }

    public Float getValue_rate() {
        return value_rate;
    }

    public void setValue_rate(Float value_rate) {
        this.value_rate = value_rate;
    }

    public String getName_monnaie() {
        return name_monnaie;
    }

    public void setName_monnaie(String name_monnaie) {
        this.name_monnaie = name_monnaie;
    }
    
    
}
