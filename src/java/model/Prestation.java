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
}
