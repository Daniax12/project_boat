/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.TableAnnotation;
import java.sql.Timestamp;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "history", sequence = "history_seq", prefix = "HIS_")
public class History {
    @ColumnField(column = "id_history", primary_key = true, is_increment = true)
    private String id_history;
    
    @ColumnField(column = "id_utilisateur")
    private String id_utilisateur;
    
    @ColumnField(column = "id_action")
    private String id_action;
    
    @ColumnField(column = "history_insertion")
    private java.sql.Timestamp history_insertion;
    
    @ColumnField(column = "remarks")
    private String remarks;

    
    // CONSTRUCTORS
    public History(String id_utilisateur, String id_action, Timestamp history_insertion, String remarks) {
        this.setId_utilisateur(id_utilisateur);
        this.setId_action(id_action);
        this.setHistory_insertion(history_insertion);
        this.setRemarks(remarks);
    }
    
    public History(){}
    
    // Getters and setters
    public String getId_history() {
        return id_history;
    }

    public void setId_history(String id_history) {
        this.id_history = id_history;
    }

    public String getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(String id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getId_action() {
        return id_action;
    }

    public void setId_action(String id_action) {
        this.id_action = id_action;
    }

    public Timestamp getHistory_insertion() {
        return history_insertion;
    }

    public void setHistory_insertion(Timestamp history_insertion) {
        this.history_insertion = history_insertion;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
    
}
