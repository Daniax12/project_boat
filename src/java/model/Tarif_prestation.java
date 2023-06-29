/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.TableAnnotation;
import database.ConnectionBase;
import java.sql.Connection;
import java.sql.Time;
import java.util.List;
import mapping.BddObject;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "v_prestation_tranche")
public class Tarif_prestation {
    @ColumnField(column = "id_tarif_prestation", primary_key = true)
    private String id_tarif_prestation;
    
    @ColumnField(column = "id_prestation")
    private String id_prestation;
    
    @ColumnField(column = "id_nationality")
    private String id_nationality;
    
    @ColumnField(column = "id_dock")
    private String id_dock;
    
    @ColumnField(column = "id_boat_type")
    private String id_boat_type;
    
    @ColumnField(column = "id_tranche_hour")
    private String id_tranche_hour;
    
    @ColumnField(column = "debut_tranche")
    private java.sql.Time debut_tranche;
    
    @ColumnField(column = "end_tranche")
    private java.sql.Time end_tranche;
    
    @ColumnField(column = "montant_prestation")
    private Float montant_prestation;
    
    @ColumnField(column = "id_monnaie")
    private String id_monnaie;
    
    @ColumnField(column = "level")
    private Integer level;

    // Specific montant depending on quai, nationality, prestation (MUST BE CLASS HERE, NOT STRING !!!!!!!!!!!)
    public static List<Tarif_prestation> get_specific_tarif(String id_dock, String id_boat_type, String id_nationality, String id_prestation, String id_tranche_hour, int dock_calculation, int boat_calculation, Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Tarif_prestation tp = new Tarif_prestation();
            if(dock_calculation == 0){
                tp.setId_dock(id_dock);
            }
            
            if(boat_calculation == 0){
                tp.setId_boat_type(id_boat_type);
            }
            tp.setId_tranche_hour(id_tranche_hour);
            tp.setId_nationality(id_nationality);
            tp.setId_prestation(id_prestation);
            
            List<Tarif_prestation> result = BddObject.find("v_prestation_tranche", tp, connection);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting specific prestation tarif. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    } 
    
    
    // Specific montant depending on quai, nationality, prestation (MUST BE CLASS HERE, NOT STRING !!!!!!!!!!!)
    public static List<Tarif_prestation> get_specific_tarif(String id_dock, String id_boat_type, String id_nationality, String id_prestation, int dock_calculation, int boat_calculation, Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Tarif_prestation tp = new Tarif_prestation();
            if(dock_calculation == 0){
                tp.setId_dock(id_dock);
            }
            
            if(boat_calculation == 0){
                tp.setId_boat_type(id_boat_type);
            }
            
            tp.setId_nationality(id_nationality);
            tp.setId_prestation(id_prestation);
            
            List<Tarif_prestation> result = BddObject.find("v_prestation_tranche", tp, connection);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting specific prestation tarif. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    } 
    
    
    // FK
    
    
    // Getters and setters
    public String getId_tarif_prestation() {
        return id_tarif_prestation;
    }

    public void setId_tarif_prestation(String id_tarif_prestation) {
        this.id_tarif_prestation = id_tarif_prestation;
    }

    public String getId_prestation() {
        return id_prestation;
    }

    public void setId_prestation(String id_prestation) {
        this.id_prestation = id_prestation;
    }

    public String getId_nationality() {
        return id_nationality;
    }

    public void setId_nationality(String id_nationality) {
        this.id_nationality = id_nationality;
    }

    public String getId_dock() {
        return id_dock;
    }

    public void setId_dock(String id_dock) {
        this.id_dock = id_dock;
    }

    public String getId_tranche_hour() {
        return id_tranche_hour;
    }

    public void setId_tranche_hour(String id_tranche_hour) {
        this.id_tranche_hour = id_tranche_hour;
    }

    public Time getDebut_tranche() {
        return debut_tranche;
    }

    public void setDebut_tranche(Time debut_tranche) {
        this.debut_tranche = debut_tranche;
    }

    public Time getEnd_tranche() {
        return end_tranche;
    }

    public void setEnd_tranche(Time end_tranche) {
        this.end_tranche = end_tranche;
    }

    public Float getMontant_prestation() {
        return montant_prestation;
    }

    public void setMontant_prestation(Float montant_prestation) {
        this.montant_prestation = montant_prestation;
    }

    public String getId_monnaie() {
        return id_monnaie;
    }

    public void setId_monnaie(String id_monnaie) {
        this.id_monnaie = id_monnaie;
    }

    public String getId_boat_type() {
        return id_boat_type;
    }

    public void setId_boat_type(String id_boat_type) {
        this.id_boat_type = id_boat_type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    
    
    
}
