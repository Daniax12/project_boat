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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mapping.BddObject;
import utilities.DateUtil;
import utilities.Ordering;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "dock", sequence = "dock_seq", prefix = "QUAI_")
public class Dock {
    @ColumnField(column = "id_dock", primary_key = true, is_increment = true)
    private String id_dock;
    
    @ColumnField(column = "name_dock")
    private String name_dock;
    
    @ColumnField(column = "profondeur_dock")
    Float profondeur_dock;
    
    /**
     * Propositions de tous les sambos
     * @param connection
     * @return
     * @throws Exception 
     */
    public List<Proposition> dock_propositions_escale(Connection connection) throws Exception{
         boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        List<Proposition> result = new ArrayList<>();
        try {
            List<Escale> previsions = this.dock_previsions_escale(connection); 
            if(previsions.size() != 0){
                List<List<Timestamp>> schedules = new ArrayList<>();
                // AJOUTER LA PREMIERE PREVISION
                add_schedules(schedules, previsions.get(0).getDebut_prevision(), previsions.get(0).getEnd_prevision());         // Ajouter premier escale
                result.add(new Proposition(previsions.get(0), previsions.get(0).getDebut_prevision(), previsions.get(0).getEnd_prevision(), 0, 0.0f));
                
                if(previsions.size() > 1){
                    for(int i = 1; i < previsions.size(); i++){
                        Proposition proposition = null;
                        Escale esc = previsions.get(i);                                     // Each escale
                        Timestamp new_debut = null; Timestamp new_end = null;
                        Integer index = null; Integer reverse_index = null;
                        Timestamp temp_debut = new Timestamp(esc.getDebut_prevision().getTime());               // Debut prevision
                        Timestamp temp_end = new Timestamp(esc.getEnd_prevision().getTime());                   // End previsosin
//                        Time difference = DateUtil.time_difference_two_timestamp(temp_debut, temp_end);     // Duree de la prevision
                        long difference = DateUtil.time_difference_two_timestamp_long(temp_debut, temp_end);
                        for(int k = 0; k < schedules.size(); k++){
                            Timestamp debut_schedule = schedules.get(k).get(0);
                            Timestamp end_schedule = schedules.get(k).get(1);

                            if(can_be_free(temp_debut, temp_end, debut_schedule, end_schedule) == false){
                                new_debut = new Timestamp(end_schedule.getTime());
                                new_end = DateUtil.add_time_to_timestamp(new_debut, difference);
                                index = k;
                            }
                        }

                        if(index != null){
                            for(int j = index; j >= 0; j--){
                                Timestamp debut_schedule = schedules.get(j).get(0);
                                Timestamp end_schedule = schedules.get(j).get(1);

                                if(can_be_free(temp_debut, temp_end, debut_schedule, end_schedule) == false){
                                    new_debut = new Timestamp(end_schedule.getTime());
                                    new_end = DateUtil.add_time_to_timestamp(new_debut, difference);
                                    reverse_index = j;
                                }
                            }

                            int index_new = index;
                            if(reverse_index != null) index_new = reverse_index;

                            long attente = DateUtil.difference_two_timestamp_long(new_debut, esc.getDebut_prevision());       // Temps d'attente -> NOuvelle propopse - prevision debut
                            proposition = new Proposition(esc, new_debut, new_end, attente, 0.0f);  // proposition with new schedules

                            schedules.get(index_new).set(1, new_end);                       // Changement
                            System.out.println("INdex is "+index_new);
                            System.out.println("VAovao debut "+new_debut);
                            System.out.println("VAovao end "+new_end);


                        } else {
                            add_schedules(schedules, esc.getDebut_prevision(), esc.getEnd_prevision());             // Add in the schedules
                            proposition = new Proposition(esc, esc.getDebut_prevision(), esc.getEnd_prevision(), 0, 0.0f);      // -> ATtente = 0 / date itself
                        }
                            for(int p = 0; p < schedules.size(); p++){
                                System.out.println("deb : "+schedules.get(p).get(0) +" / end is : "+schedules.get(p).get(1));
                            }
                            System.out.println("----------------------------------------------------------");
                        result.add(proposition);
                    }
                }
                
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting all proposition DETAIL of a specified dock. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
   
    
   public void add_schedules(List<List<Timestamp>> schedules, Timestamp debut, Timestamp end){
       List<Timestamp> temp = new ArrayList<>();
       temp.add(debut);
       temp.add(end);
       schedules.add(temp);
   }
    

    
    /**
     * TO check if in an already time took(not free between it), 2 dates is not include
     * @param debut
     * @param end
     * @param took_debut
     * @param took_end
     * @return 
     */
    public boolean can_be_free(Timestamp debut, Timestamp end, Timestamp took_debut, Timestamp took_end){
        if(DateUtil.is_between_timestamps(debut, took_debut, took_end) == true || DateUtil.is_between_timestamps(end, took_debut, took_end) == true){
            return false;
        }
        return true;
    }
    
    /**
     * Get all PREVISION escale of the specified escale
     * @param connection
     * @return
     * @throws Exception 
     */
    public List<Escale> dock_previsions_escale(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
           
        try {
            List<Escale> temp_result = this.dock_escales(connection);   // prendre tous les escales
            List<Escale> result = Escale.all_prevision_escales(temp_result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting all escale PREVISION of a specified dock. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    /**
     * Get all escales of a specified dock order by RANG
     * @param connection
     * @return
     * @throws Exception 
     */
    public List<Escale> dock_escales(Connection connection) throws Exception{
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
           
        try {
            Escale escale = new Escale();
            escale.setId_dock(this.getId_dock());
            
            List<Escale> result = BddObject.findByOrder("escale", escale, "rang", Ordering.ASC, connection);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting all escale of a specified dock. Error : "+e.getMessage());
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    /**
     * Get the perfect dock for a boat
     * @param boat
     * @param connection
     * @return
     * @throws Exception 
     */
    public static Dock dock_for_boat(Boat boat, Connection connection) throws Exception{
        Dock result = new Dock();
        boolean isOpen = false;
        ConnectionBase connectionBase = new ConnectionBase();
        if(connection == null){
            connection = connectionBase.dbConnect();     // If it is null, creating connection
        }else{
            isOpen = true;
        }
        
        try {
            List<Dock> all_docks = new ArrayList<>();
            all_docks = BddObject.findByOrder(null, new Dock(), "profondeur_dock", Ordering.ASC, connection);
            
            for(int i = 0; i < all_docks.size(); i++){
                if(boat.getProfondeur() < all_docks.get(i).getProfondeur_dock()){
                    return all_docks.get(i);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("Error on getting the dock for the boat");
        } finally{
            if(isOpen == false) connection.close();
        }
    }
    
    
    // Getters and setters
    public String getId_dock() {
        return id_dock;
    }

    public void setId_dock(String id_dock) {
        this.id_dock = id_dock;
    }

    public String getName_dock() {
        return name_dock;
    }

    public void setName_dock(String name_dock) {
        this.name_dock = name_dock;
    }

    public Float getProfondeur_dock() {
        return profondeur_dock;
    }

    public void setProfondeur_dock(Float profondeur_dock) {
        this.profondeur_dock = profondeur_dock;
    }
    
}
