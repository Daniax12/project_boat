/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import annoted.ColumnField;
import annoted.TableAnnotation;

/**
 *
 * @author rango
 */
@TableAnnotation(nameTable = "utilisateur")
public class Utilisateur {
    @ColumnField(column = "id_utilisateur", primary_key = true)
    private String id_utilisateur;
    
    @ColumnField(column = "name_utilisateur")
    private String name_utilisateur;

    
    // Peut ajouter prestation
    public boolean can_add_prestation(){
        if(this.getId_utilisateur().equals("user_1") == true || this.getId_utilisateur().equals("user_2") == true){
            return true;
        }
        return false;
    }
    
        // peut valider prestation
    public boolean can_validate_prestation(){
        if(this.getId_utilisateur().equals("user_2") == true){
            return true;
        }
        return false;
    }
    
        // Peut facturer
    public boolean can_facturate(){
        if(this.getId_utilisateur().equals("user_3") == true || this.getId_utilisateur().equals("user_4") == true){
            return true;
        }
        return false;
    }
    
    // peut valider facture
    public boolean can_validate_invoice(){
        if(this.getId_utilisateur().equals("user_4") == true){
            return true;
        }
        return false;
    }
    

    
    
    // Getters and setters
    public String getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(String id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getName_utilisateur() {
        return name_utilisateur;
    }

    public void setName_utilisateur(String name_utilisateur) {
        this.name_utilisateur = name_utilisateur;
    }
    
    
}
