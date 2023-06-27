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

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "prestation")
public class Prestation {
    
    @ColumnField(column = "id_prestation", primary_key = true, is_increment = true)
    private String id_prestation;
    
    @ColumnField(column = "name_prestation")
    private String name_prestation;
    
    @ColumnField(column = "tranche_min")
    private Integer tranche_min;
    
    @ColumnField(column = "dock_calculation")
    private Integer dock_calculation;
    
    @ColumnField(column = "boat_calculation")
    private Integer boat_calculation;

    // GETTERS AND SETTERS
    public String getId_prestation() {
        return id_prestation;
    }

    public void setId_prestation(String id_prestation) {
        this.id_prestation = id_prestation;
    }

    public String getName_prestation() {
        return name_prestation;
    }

    public void setName_prestation(String name_prestation) {
        this.name_prestation = name_prestation;
    }

    public Integer getTranche_min() {
        return tranche_min;
    }

    public void setTranche_min(Integer tranche_min) {
        this.tranche_min = tranche_min;
    }

    public Integer getDock_calculation() {
        return dock_calculation;
    }

    public void setDock_calculation(Integer dock_calculation) {
        this.dock_calculation = dock_calculation;
    }

    public Integer getBoat_calculation() {
        return boat_calculation;
    }

    public void setBoat_calculation(Integer boat_calculation) {
        this.boat_calculation = boat_calculation;
    }
    
    
}
