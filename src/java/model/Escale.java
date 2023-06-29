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
    
    @ColumnField(column = "debut_prevision")
    private java.sql.Timestamp debut_prevision;
    
        
    // FACTURE D'UNE ESCALE
    public Facture escale_facture(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
           Facture facture = new Facture();
           facture.setId_escale(this.getId_escale());
           List<Facture> all = BddObject.find("facture", facture, connection);
           if(all.size() == 0){
               return null;
           }
           return all.get(0);
         
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the invoice of an escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // SAVOIR SI LA FACTURE DE L'ESCALEE EST DEJA VALIDE OU PAS
    public boolean is_invoice_valide(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
           Facture facture = this.escale_facture(connection);
           if(facture.getStatus_facture() == 11) return true;
           return false;
           
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on knowing if an escale is already valide or not. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // SAVOIR SI UNE ESCALE EST FACTURABLE -->> TOUS LES PRESTATIONS SONT VALIDES
    public boolean is_facturable(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            List<Prestation_escale> my_prestations = this.getPrestation_done(connection);
            
            for(int i = 0; i < my_prestations.size(); i++){
                if(my_prestations.get(i).getStatus_prestation() == 1) return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on knowing if an escale is invoiceable or not. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // PRENDRE LE QUAI EN COURS ou LE DERNIER QUAI A PARTIR DU PRESTATION
    public Dock get_last_dock(Connection connection) throws Exception{
         boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        
         try {
            List<Prestation_escale> my_prestations = this.getPrestation_done(connection);
            for(int i = 0; i < my_prestations.size(); i++){
                Prestation_escale main = my_prestations.get(i);
                if(main.getId_prestation().equals("PRES_1") == true){
                    return main.pe_dock(connection);
                }
            }
            return null;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the last dock of the escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // INSERTION D'UNE NOUVELLE ESCALE
    public void insert_first_escale(String id_dock, String debut_time, Utilisateur user, Connection connection) throws Exception {
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
            java.sql.Timestamp now = DateUtil.string_to_timestamp(debut_time);             // Enregistrement date
            String my_id = BddObject.insertInDatabase(this, connection);                    // Insertion de l'escale
            History history_escale = new History(user.getId_utilisateur(), "A1", now, my_id);   // HIstory escale
            BddObject.insertInDatabase(history_escale, connection);
            Dock verif_dock = new Dock();
            verif_dock.setId_dock(id_dock);                                                         // verifier l'existende du quai
            verif_dock = BddObject.findById("dock", verif_dock, connection);
            
            Boat the_boat = this.escale_boat(connection);
            Timestamp end_remorquage = DateUtil.add_minutes_to_timestamp(now, the_boat.getRemorquage_duration());
            
            // WE CAN CHANGE THE DEBUT HOUR OF THE 
            Prestation_escale.prestation_for_escale(this, verif_dock, "PRES_1", user, now, null, connection);   // INSERT STATIONNEMENT
            Prestation_escale.prestation_for_escale(this, verif_dock, "PRES_2", user, now, end_remorquage, connection);    // INSERT REMORQUAGE
            
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on saving the new escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
       
    // Constructors
    public Escale(String id_boat, String debut_prevision) throws Exception{
        try {
            this.setId_boat(id_boat);
            this.setDebut_prevision(debut_prevision);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on constructing the escale. Error: "+e.getMessage());
        }
    }
   
    public Escale(){}
    
    // FK
    public Boat escale_boat(Connection connection) throws Exception{
        if(this.getId_boat() == null) return null;
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Boat boat = new Boat();
            boat.setId_boat(this.getId_boat());
            boat = (Boat) BddObject.findById("v_boat_detail", boat, connection);
            return boat;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on finding the boat of the escale");
        } finally{
            if(isOpen == false) connection.close();
        }
    }
   
    
    // GETTERS AND SETTERS
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
            
//             int comparisonResult = date.compareTo(now);
//             if(comparisonResult < 0){
//                 throw new Exception("Date already on the last");
//             } 
             this.setDebut_prevision(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on setting the debut of the escale prevision");
        }
    }
  

    // Prendre tous les prestations de l'escale triE par ordre de debut de prestation
    public List<Prestation_escale> getPrestation_done(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            Prestation_escale pe = new Prestation_escale();
            pe.setId_escale(this.getId_escale());
            return BddObject.findByOrder("prestation_escale", pe, "debut_prestation", Ordering.DESC, connection);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting all prestations done of the escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
}
