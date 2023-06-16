/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mapping.BddObject;

/**
 *
 * @author rango
 */
public class Proposition{
    
    private Escale escale_propositin;
    private java.sql.Timestamp debut_propose;
    private java.sql.Timestamp end_propose;
    private long attente_duration;
    private Float prevision_montant;

    /**
     * Get all propositions of the port -> Normaly group by all quai
     * @return 
     */
    public List<Proposition> all_proposition(){
        List<Proposition> result = new ArrayList<>();
        
        return  result;
    }
    
    // Constructors
    public Proposition(Escale escale, Timestamp debut_propose, Timestamp end_propose, long attente_duration, Float prevision_montant) throws Exception{
        this.setEscale_propositin(escale);
        this.setDebut_propose(debut_propose);
        this.setEnd_propose(end_propose);
        this.setAttente_duration(attente_duration);
        this.setPrevision_montant(prevision_montant);
    }
    public Proposition(){

    }

        // Getters and setterss
    public Escale getEscale_propositin() {
        return escale_propositin;
    }

    public void setEscale_propositin(Escale escale_propositin) {
        this.escale_propositin = escale_propositin;
    }
    
    
    public Timestamp getDebut_propose() {
        return debut_propose;
    }

    public void setDebut_propose(Timestamp debut_propose) {
        this.debut_propose = debut_propose;
    }

    public Timestamp getEnd_propose() {
        return end_propose;
    }

    public void setEnd_propose(Timestamp end_propose) {
        this.end_propose = end_propose;
    }

    public long getAttente_duration() {
        return attente_duration;
    }

    public void setAttente_duration(long attente_duration) {
        this.attente_duration = attente_duration;
    }

    public Float getPrevision_montant() {
        return prevision_montant;
    }

    public void setPrevision_montant(Float prevision_montant) {
        this.prevision_montant = prevision_montant;
    }
    
    
}
