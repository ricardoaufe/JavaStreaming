/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ricardo Ferreira
 */
public class Series extends Video implements Situation {

    private String situation;
    
    public Series(String title, String description, String situation) {
        super(title, description);
        this.situation = situation;
    }

    @Override
    public String getType() {
        return "Serie";
    }
    
    @Override
    public String getSituation(){
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
    
    
}