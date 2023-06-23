/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.DataType;
import annoted.TableAnnotation;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "boat", sequence = "boat_seq", prefix = "boat_")
public class Boat {

    @ColumnField(column = "id_boat", primary_key = true, is_increment = true)
    private String id_boat;
    
    @ColumnField(column = "boat_name")
    private String boat_name;
    
    @ColumnField(column = "id_boat_type")
    private String id_boat_type;
    
    @ColumnField(column = "id_nationality")
    private String id_nationality;
    
    private String name_boat_type;
    
    @ColumnField(column = "profondeur")
    private Float profondeur;
    
    @ColumnField(column = "remorquage_duration")
    private Integer remorquage_duration;
    
  
   // Getters and setters
    public String getName_boat_type() {
        return name_boat_type;
    }

    public void setName_boat_type(String name_boat_type) {
        this.name_boat_type = name_boat_type;
    }
    public String getId_boat() {
        return id_boat;
    }

    public void setId_boat(String id_boat) {
        this.id_boat = id_boat;
    }

    public String getBoat_name() {
        return boat_name;
    }

    public void setBoat_name(String boat_name) {
        this.boat_name = boat_name;
    }

    public String getId_boat_type() {
        return id_boat_type;
    }

    public void setId_boat_type(String id_boat_type) {
        this.id_boat_type = id_boat_type;
    }

    public Float getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(Float profondeur) {
        this.profondeur = profondeur;
    }

    public Integer getRemorquage_duration() {
        return remorquage_duration;
    }

    public void setRemorquage_duration(Integer remorquage_duration) {
        this.remorquage_duration = remorquage_duration;
    }

    public String getId_nationality() {
        return id_nationality;
    }

    public void setId_nationality(String id_nationality) {
        this.id_nationality = id_nationality;
    }
}
