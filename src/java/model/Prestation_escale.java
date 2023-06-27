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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    
    // --------------- PRICING DETAILS ---------------------------------------
    
    // Getting the prestation with regards to the exchange
    public float get_price_with_exchange(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        
        try {
            float value = this.get_price_tarif_no_exchange(connection);
            String id_monnaie = "";
            if(this.pe_escale(connection).escale_boat(connection).getId_nationality().equals("NAT_1") == true){
                id_monnaie = "m1";
            }else{
                id_monnaie = "m2";
            }
            
            float rate = Exchange_rate.value_monnaie(id_monnaie, connection);
            return rate * value;
           
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the prestation tarif of an escale, with exchange rate. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // Getting the prestation tarif without considering the exchange rate
    public float get_price_tarif_no_exchange(Connection connection) throws Exception{
        if(this.getEnd_prestation() == null) return 0;
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        try {
            float result = 0;
            Prestation prestation = this.pe_prestation(connection);
            Boat the_boat = this.pe_escale(connection).escale_boat(connection);
            List<Tarif_prestation> tarification = Tarif_prestation.get_specific_tarif(this.getId_dock(), the_boat.getId_boat_type(), the_boat.getId_nationality(), this.getId_prestation(), prestation.getDock_calculation(), prestation.getBoat_calculation(), connection); // Get the menu
            
            // Refering with the cession d'eau which do not have dock_calculation or boat_calculation -> Return the first montant
            if(tarification.size() == 1 && tarification.get(0).getId_boat_type() == null && tarification.get(0).getId_dock() == null){
                return tarification.get(0).getMontant_prestation();
            }
            
            List<List<Timestamp>> date_cut = this.cut_pe_date();                                                                                                                            // Get all date cut
            
            
            for(int i = 0; i < date_cut.size(); i++){
                Timestamp debut = date_cut.get(i).get(0);
                Timestamp end = date_cut.get(i).get(1);
                for(int k = 0; k < tarification.size(); k++){       // We can use both debut or end because they have same date
                    Timestamp bornInf = DateUtil.add_time_data_in_timestamp(debut, tarification.get(k).getDebut_tranche());
                    Timestamp bornSup = DateUtil.add_time_data_in_timestamp(debut, tarification.get(k).getEnd_tranche());
                    
                    int difference_minute = DateUtil.duration_minutes_two_intervalle(debut, end, bornInf, bornSup);
                    int repetition = Prestation_escale.divide_and_round(difference_minute, prestation.getTranche_min());
                    result += repetition * tarification.get(k).getMontant_prestation();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the prestation tarif of an escale, without considering exchange rate. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    // Cutting the timestamp debut and end of the prestation escale into LIst of debut/end
    public List<List<Timestamp>> cut_pe_date(){
        List<List<Timestamp>> result = new ArrayList<>();
        
        if(this.is_same_date_prestation() == true){             // If same date, we return data we have
            List<Timestamp> temp_result = new ArrayList<>();
            temp_result.add(this.getDebut_prestation());
            temp_result.add(this.getEnd_prestation());
            result.add(temp_result);
        } else {
            int[] days = this.get_days_prestation();
            Timestamp debut = new Timestamp(this.getDebut_prestation().getTime());
            Timestamp end = new Timestamp(this.getEnd_prestation().getTime());
            
            // DEBUT OF THE TIMESTAMP LIST
            List<Timestamp> debut_pe = new ArrayList<>();
            debut_pe.add(debut);
            int debut_day = DateUtil.day_of_timestamp(debut);
            int debut_month = DateUtil.month_of_timestamp(debut);
            int debut_year = DateUtil.year_of_timestamp(debut);
            Calendar cal_debut = Calendar.getInstance();
            cal_debut.set(debut_year, debut_month-1, debut_day, 23, 59, 0);
            
            debut_pe.add(new Timestamp(cal_debut.getTimeInMillis()));       // Ajout end of the day
            result.add(debut_pe);
            
            
            // THE BETWEEN IF IT EXITS
            int trig = 1;
            for(int i = 1; i < days.length - 1; i++){
                Timestamp debut_btw = DateUtil.add_day_to_timestamp(debut, trig);
                Timestamp end_btw = DateUtil.add_day_to_timestamp(debut, trig);
                debut_btw.setHours(0);
                debut_btw.setMinutes(1);
                
                end_btw.setHours(23);
                end_btw.setMinutes(59);
                
                List<Timestamp> temp_btw = new ArrayList<>();
                temp_btw.add(debut_btw);
                temp_btw.add(end_btw);
                result.add(temp_btw);
                trig += 1;
            }
            
             // END OF THE TIMESTAMP LIST
            List<Timestamp> end_pe = new ArrayList<>();
            int end_day = DateUtil.day_of_timestamp(end);
            int end_month = DateUtil.month_of_timestamp(end);
            int end_year = DateUtil.year_of_timestamp(end);
            Calendar cal_end = Calendar.getInstance();
            cal_end.set(end_year, end_month-1, end_day, 0, 1, 0);
            
            end_pe.add(new Timestamp(cal_end.getTimeInMillis()));       // Ajout debut of the day
            end_pe.add(end);
            result.add(end_pe);
        }
        
//        for(int i = 0; i < result.size(); i++){
//            System.out.println("Debut: "+result.get(i).get(0) + "/ end: "+result.get(i).get(1));
//            System.out.println("-------------------------------------");
//        }
        return result;
    }
    
    // Get all dates of a prestations escale
    public int[] get_days_prestation(){
        int debut_day = DateUtil.day_of_timestamp(this.getDebut_prestation());
        int end_day = DateUtil.day_of_timestamp(this.getEnd_prestation());
        
        if(debut_day == end_day){
            return new int[]{debut_day};
        } else {
            int[] result = new int[end_day - debut_day +1];
            
            for(int i = 0; i < result.length; i++){
                result[i] = debut_day;
                debut_day += 1;
            }
            return result;
        }
    }
    
    // If the debut_date and end_date of the prestation_escale is the same
    public boolean is_same_date_prestation(){
        int debut_day = DateUtil.day_of_timestamp(this.getDebut_prestation());
        int end_day = DateUtil.day_of_timestamp(this.getEnd_prestation());
        
        if(debut_day == end_day) return true;
        return false;
    }
    
    // Usefull function
    public static int divide_and_round(int dividend, int divisor) {
        float result = (float) dividend / divisor;
        int intValue = (int) result;  // Get the integer part of the result
        float decimalValue = result - intValue;  // Get the decimal part of the result

        if (decimalValue > 0) {
            return intValue + 1;  // Round up if the decimal part is greater or equal to 0.5
        } else {
            return intValue;  // Round down otherwise
        }
    }
    
    
    // --------------- END PRICING DETAILS ----------------------------------
    // VALIDATION D'UNE PRESTATION  ---> ERREUR NY STATIC HERE BECAUSE WE ARE IN OBJECT ORIENTED PROGRAMMATION
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
    
    
    // CHANGING DOCK
    public static void change_dock(String escale_id, String iddock, String old_dock, Utilisateur user, String date_debut, Connection connection) throws Exception{
        if(user.can_add_prestation() == false){
            throw new Exception("Not allowed to make this action");
        } 
        
        if(iddock.equals(old_dock) == true) throw new Exception("Old dock detected");
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
            if(escale.is_facturable(connection) == false){
                 throw new Exception("Prestations in previous dock must valid if you want to change dock");
            }
            
            Timestamp time_debut = DateUtil.string_to_timestamp(date_debut);
            
            Prestation_escale presta = new Prestation_escale(escale_id,  "PRES_1", iddock, time_debut, null, 1);
            String presta_id = BddObject.insertInDatabase(presta, connection);
            History history_prestation = new History(user.getId_utilisateur(), "A2", time_debut, presta_id);
            BddObject.insertInDatabase(history_prestation, connection);
            
            if(isOpen == false) connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on changing the new dock for an escale. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // INSERTION D'UNE NOUVELLE PRESTATION
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
    
    // FOREIGN KEY CLASS
    
    // PRENDRE LE PRESTATION DETAILLE
    public Prestation pe_prestation(Connection connection) throws Exception{
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
    public Dock pe_dock(Connection connection) throws Exception{
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
    
    // PRENDRE L'ESCALE DETAILLE
    public Escale pe_escale(Connection connection) throws Exception{
        if(this.getId_escale() == null) return null;
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        
        try {
           Escale result = new Escale();
           result.setId_escale(this.getId_escale());
           result = BddObject.findById("escale", result, connection);
           return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on finding the escale of the prestation_escale. Error: "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
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
