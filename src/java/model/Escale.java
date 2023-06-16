/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.TableAnnotation;
import database.ConnectionBase;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mapping.BddObject;
import utilities.DatePattern;
import utilities.DateUtil;
import utilities.Ordering;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "escale", sequence = "escale_seq", prefix = "ESC_")
public class Escale {
    @ColumnField(column = "id_escale", primary_key = true, is_increment = true)
    private String id_escale;
    
    @ColumnField(column = "id_boat")
    private String id_boat;
    
    @ColumnField(column = "id_dock")
    private String id_dock;
    
    @ColumnField(column = "debut_prevision")
    private java.sql.Timestamp debut_prevision;
    
    @ColumnField(column = "end_prevision")
    private java.sql.Timestamp end_prevision;
    
    @ColumnField(column = "rang")
    private Integer rang;
    
    private Boat boat_escale;
    
    private Dock dock_escale;
    
    /**
     * Get all previsions escale among a list of escales
     * @param connection
     * @return
     * @throws Exception 
     */
    public static List<Escale> all_prevision_escales(List<Escale> escales){
        List<Escale> result = new ArrayList<>();
         Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Escale escale : escales) {
            if(escale.getDebut_prevision().after(now) == true){
                result.add(escale);
            }
        }
        return result;
    }
       
    // Constructors

    public Escale(String id_boat, String debut_prevision, String end_prevision, int seq) throws Exception{
        try {
            this.setId_boat(id_boat);
            this.setDebut_prevision(debut_prevision);
            this.setEnd_prevision(end_prevision);
            Boat my_boat = this.getBoat_escale();
            Dock my_dock = Dock.dock_for_boat(my_boat, null);
            this.setId_dock(my_dock.getId_dock());
            this.setRang(seq);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on constructing the escale. Error: "+e.getMessage());
        }
    }
   
    public Escale(){}
    
    public Escale(Timestamp debut_prevision, Timestamp end_prevision, Boat boat_escale, Dock dock_escale) {
        this.setDebut_prevision(debut_prevision);
        this.setEnd_prevision(end_prevision);
        this.setBoat_escale(boat_escale);
        this.setDock_escale(dock_escale);
    }
    

    // Getters and setters
    public Boat getBoat_escale() throws Exception{
        if(this.getId_boat() == null) return null;
        ConnectionBase connectionBase = new ConnectionBase();
        Connection connection = null;
        try {
            connection = connectionBase.dbConnect(); 
            Boat boat = new Boat();
            boat.setId_boat(this.getId_boat());
            boat = (Boat) BddObject.findById("v_boat_detail", boat, connection);
            return boat;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on finding the boat of the escale");
        } finally{
            connection.close();
        }
    }

    public void setBoat_escale(Boat boat_escale) {
        this.boat_escale = boat_escale;
    }

    public Dock getDock_escale() throws Exception{
        if(this.getId_dock() == null) return null;
        ConnectionBase connectionBase = new ConnectionBase();
        Connection connection = null;
        try {
            connection = connectionBase.dbConnect(); 
            Dock dock = new Dock();
            dock.setId_dock(this.getId_dock());
            dock = (Dock) BddObject.findById(null, dock, connection);
            return dock;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on finding the dock of the escale");
        } finally{
            connection.close();
        }
    }

    public void setDock_escale(Dock dock_escale) {
        this.dock_escale = dock_escale;
    }

   
    public String getId_escale() {
        return id_escale;
    }

    public void setId_escale(String id_escale) {
        this.id_escale = id_escale;
    }

    public String getId_boat() {
        return id_boat;
    }

    public void setId_boat(String id_boat) {
        this.id_boat = id_boat;
    }

    public String getId_dock() {
        return id_dock;
    }

    public void setId_dock(String id_dock) {
        this.id_dock = id_dock;
    }

    public Timestamp getDebut_prevision() {
        return debut_prevision;
    }

    public void setDebut_prevision(Timestamp debut_prevision) {
        this.debut_prevision = debut_prevision;
    }
    public void setDebut_prevision(String debut_prevision_string) throws Exception{
        try {
            java.sql.Timestamp date = DateUtil.string_to_timestamp(debut_prevision_string);
            Timestamp now = new Timestamp(System.currentTimeMillis());        // Get now
            
             int comparisonResult = date.compareTo(now);
             if(comparisonResult < 0){
                 throw new Exception("Date already on the last");
             } 
             this.setDebut_prevision(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on setting the debut of the escale prevision");
        }
    }

    public Timestamp getEnd_prevision() {
        return end_prevision;
    }

    public void setEnd_prevision(Timestamp end_prevision) {
        this.end_prevision = end_prevision;
    }
    
    public void setEnd_prevision(String end_prevision_string) throws Exception{
        if(this.getDebut_prevision() == null){
            throw  new Exception("Can not insert the end of prevision date without setting the debut of the prevision");
        }
        
        try {
            java.sql.Timestamp date = DateUtil.string_to_timestamp(end_prevision_string);
            int comparisonResult = this.getDebut_prevision().compareTo(date);
            
            if(comparisonResult > 0){
                throw  new Exception("End prevision is less than debut previsin");
            }
            this.setEnd_prevision(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on setting end date of the prevision");
        }
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }
    
    
}
