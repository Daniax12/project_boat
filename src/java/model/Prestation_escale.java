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
import mapping.BddObject;
import utilities.DateUtil;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "prestation_escale", sequence = "prestation_seq", prefix = "PRE_ESC_")
public class Prestation_escale {
    @ColumnField(column = "id_prestation_escale", primary_key = true, is_increment = true)
    private String id_prestation_escale;
    
    @ColumnField(column = "id_escale")
    private String id_escale;
    
    @ColumnField(column = "id_prestation")
    private String id_prestation;
    
    @ColumnField(column = "id_dock")
    private String id_dock;
    
    @ColumnField(column = "debut_prestation")
    private java.sql.Timestamp debut_prestation;
    
    @ColumnField(column = "end_prestation")
    private java.sql.Timestamp end_prestation;
    
    @ColumnField(column = "status_prestation")
    private Integer status_prestation;
    
    // VALIDATION D'UNE PRESTATION
    public static void validate_prestation(String id_pe, Utilisateur user, Connection connection) throws Exception{
         if(user.can_validate_prestation()== false){
            throw new Exception("Not allowed to make this action");
        } 
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Prestation_escale pe = new Prestation_escale();
            pe.setId_prestation_escale(id_pe);
            pe = BddObject.findById("prestation_escale", pe, connection);
            if(pe.getEnd_prestation() == null){
                throw new Exception("Can not validate prestation en cours");
            }
            if(pe.getStatus_prestation() == 11){
                throw new Exception("Prestation already validate");
            }
            pe.setStatus_prestation(11);
            BddObject.updatingObject(pe, connection);
         
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on validating the end prestation of an escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // ENDING A PRESTATION
    public static void end_prestation_escal(String date_end, String id_pe, Utilisateur user, Connection connection) throws Exception{
        if(user.can_add_prestation() == false){
            throw new Exception("Not allowed to make this action");
        } 
        
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Timestamp time_end = DateUtil.string_to_timestamp(date_end);
            Prestation_escale pe = new Prestation_escale();
            pe.setId_prestation_escale(id_pe);
            pe = BddObject.findById("prestation_escale", pe, connection);
            
            pe.setEnd_prestation(time_end);
            BddObject.updatingObject(pe, connection);
         
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on setting the end prestation of an escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // INSERTION D;UNE NOUVELLE PRESTATION
    public static void add_new_prestation_escale(String escale_id ,String id_prestation, String iddock, Utilisateur user, String date_debut, String date_end, Connection connection) throws Exception{
        if(user.can_add_prestation() == false){
            throw new Exception("Not allowed to make this action");
        } 
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Escale escale = new Escale();
            escale.setId_escale(escale_id);
            escale = BddObject.findById("escale", escale, connection);
            if(escale.is_invoice_valide(connection) == true){
                throw new Exception("Can not modify an escale where invoice is already valid");
            }
            
            Timestamp time_debut = DateUtil.string_to_timestamp(date_debut);
            Timestamp time_end = DateUtil.string_to_timestamp(date_end);
            
            if(id_prestation.equals("PRES_2") == true || id_prestation.equals("PRES_3") == true){       // Si eau douce ou remorqiuabe
                time_end = new Timestamp(time_debut.getTime());
            } else {
                time_end = null;
            }
            Prestation_escale presta = new Prestation_escale(escale_id,  id_prestation, iddock, time_debut, time_end, 1);
            String presta_id = BddObject.insertInDatabase(presta, connection);
            History history_prestation = new History(user.getId_utilisateur(), "A2", time_debut, presta_id);
            BddObject.insertInDatabase(history_prestation, connection);
            
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on saving the new prestation of an escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
     // PRENDRE LE PRESTATION DETAILLE
    public Prestation get_prestation(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Prestation prestation = new Prestation();
            prestation.setId_prestation(this.getId_prestation());
            return BddObject.findById("prestation", prestation, connection);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the prestation of prestation_escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // PRENDRE LE QUAI DU PRESTATION
    public Dock get_dock(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Dock dock = new Dock();
            dock.setId_dock(this.getId_dock());
            return BddObject.findById("dock", dock, connection);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the dock of prestation_escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // AJOUT D'UNE PRESTATION POUR UNE ESCALE ET DANS L'HISTORIQUE
    public static void prestation_for_escale(Escale escale, Dock dock, String id_prestation, Utilisateur user, Timestamp date_debut, Timestamp date_end, Connection connection) throws  Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Prestation_escale presta = new Prestation_escale(escale.getId_escale(), id_prestation, dock.getId_dock(), date_debut, date_end, 1);
            String presta_id = BddObject.insertInDatabase(presta, connection);
            History history_prestation = new History(user.getId_utilisateur(), "A2", date_debut, presta_id);
            BddObject.insertInDatabase(history_prestation, connection);
            
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on saving the new escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // CONSTRUCTORS
    public Prestation_escale(){}
    
    public Prestation_escale(String id_escale, String id_prestation, String id_dock, Timestamp debut_prestation, Timestamp end_prestation, Integer status_prestation) {
        this.setId_escale(id_escale);
        this.setId_prestation(id_prestation);
        this.setId_dock(id_dock);
        this.setDebut_prestation(debut_prestation);
        this.setEnd_prestation(end_prestation);
        this.setStatus_prestation(status_prestation);
    }

    
    // Getters and setters
    public String getId_prestation_escale() {
        return id_prestation_escale;
    }

    public void setId_prestation_escale(String id_prestation_escale) {
        this.id_prestation_escale = id_prestation_escale;
    }

    public String getId_escale() {
        return id_escale;
    }

    public void setId_escale(String id_escale) {
        this.id_escale = id_escale;
    }

    public String getId_prestation() {
        return id_prestation;
    }

    public void setId_prestation(String id_prestation) {
        this.id_prestation = id_prestation;
    }

    public String getId_dock() {
        return id_dock;
    }

    public void setId_dock(String id_dock) {
        this.id_dock = id_dock;
    }

    public Timestamp getDebut_prestation() {
        return debut_prestation;
    }

    public void setDebut_prestation(Timestamp debut_prestation) {
        this.debut_prestation = debut_prestation;
    }

    public Timestamp getEnd_prestation() {
        return end_prestation;
    }

    public void setEnd_prestation(Timestamp end_prestation) {
        this.end_prestation = end_prestation;
    }

    public Integer getStatus_prestation() {
        return status_prestation;
    }

    public void setStatus_prestation(Integer status_prestation) {
        this.status_prestation = status_prestation;
    }
    
    public String get_font_etat(){
        String result = "";
        if(this.getStatus_prestation() == 1){
            result = "fa fa-times fa-2x";
        } else if(this.getStatus_prestation() == 11){
            result = "fas fa-check-square fa-2x";
        }
        return result;
    }
}
