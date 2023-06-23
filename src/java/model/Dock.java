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
    private Float profondeur_dock;
    
    
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
