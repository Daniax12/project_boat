/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.TableAnnotation;
import java.sql.Time;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "tranche_hour")
public class Tranche_hour {
    @ColumnField(column = "id_tranche_hour", primary_key = true)
    private String id_tranche_hour;
    
    @ColumnField(column = "debut_tranche")
    private java.sql.Time debut_tranche;
    
    @ColumnField(column = "end_tranche")
    private java.sql.Time end_tranche;

    
    // GETTERS AND SETTERS
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
    
    
}
