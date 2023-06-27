package model;


import annoted.ColumnField;
import annoted.TableAnnotation;
import database.ConnectionBase;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import mapping.BddObject;
import model.Escale;
import model.History;
import model.Prestation_escale;
import model.Utilisateur;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rango
 */

@TableAnnotation(nameTable = "facture", sequence = "facture_seq", prefix = "FAC_")
public class Facture {
    @ColumnField(column = "id_facture", primary_key = true, is_increment = true)
    private String id_facture;
    
    @ColumnField(column = "id_escale")
    private String id_escale;
    
    @ColumnField(column = "status_facture")
    private Integer status_facture;
    
    @ColumnField(column = "montant_total")
    private Float montant_facture;

    
    // Montant final d'une facture
    public float get_total_montant_facture(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
           float result = 0;
           List<Prestation_escale> my_prestation = this.facture_prestations(connection);
           if(my_prestation != null){
               for(int i = 0; i < my_prestation.size(); i++){
                   result += my_prestation.get(i).get_price_with_exchange(connection);
               }
           }
           return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the total value of the escale invoice. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // GET ALL FACTURE PRESTATION DETAILS
    public List<Prestation_escale> facture_prestations(Connection connection) throws Exception{
        if(this.getId_escale() == null) return null;
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
           List<Prestation_escale> result = BddObject.find("prestation_escale", pe, connection);
           return result;
           
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting all prestations of the invoice. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // VALIDER FACTURE
    public void validate_facture(Utilisateur user, Connection connection) throws Exception{
         if(user.can_validate_invoice()== false){
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
            if(this.getStatus_facture() == 11){
                throw new Exception("Invoice alredy validate");
            }
            
            this.setStatus_facture(11);
            BddObject.updatingObject(this, connection);
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on validating facture. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // CREATION D'UNE NOUVELLE FACTURE
    public static Facture create_facture(Utilisateur user, String escale_id, Connection connection) throws Exception{
        if(user.can_facturate() == false){
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
            if(escale.is_facturable(connection) ==false){
                throw new Exception("Invoice can not be invoiceable");
            }
            Facture facture = new Facture();
            facture.setId_escale(escale_id);
            facture.setMontant_facture(0.0f);
            facture.setStatus_facture(1);
            
            String facture_id = BddObject.insertInDatabase(facture, connection);
            
            History history = new History();
            history.setId_action("A6");
            history.setId_utilisateur(user.getId_utilisateur());
            history.setRemarks(facture_id);
            history.setHistory_insertion(new Timestamp(System.currentTimeMillis()));
            BddObject.insertInDatabase(history, connection);
            
            facture.setId_facture(facture_id);
            if(isOpen == false) connection.commit();
            return facture;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on creating facture. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // GETTERS AND SETTERS
    public String getId_facture() {
        return id_facture;
    }

    public void setId_facture(String id_facture) {
        this.id_facture = id_facture;
    }

    public String getId_escale() {
        return id_escale;
    }

    public void setId_escale(String id_escale) {
        this.id_escale = id_escale;
    }

    public Integer getStatus_facture() {
        return status_facture;
    }

    public void setStatus_facture(Integer status_facture) {
        this.status_facture = status_facture;
    }

    public Float getMontant_facture() {
        return montant_facture;
    }

    public void setMontant_facture(Float montant_facture) {
        this.montant_facture = montant_facture;
    }
    
    public String get_facture_etat(){
        String result = "";
        if(this.getStatus_facture()== 1){
            result = "CREER";
        } else if(this.getStatus_facture() == 11){
            result = " VALIDER ";
        }
        return result;
    }
    
    
}
