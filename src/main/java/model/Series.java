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
    
        public Series(int id, String title, String genre, String situation) {
        super(id,title, genre);
        this.situation = situation;
    }

    @Override
    public String getType() {
        return "Serie";
    }
    
    
    //Cada série possui sua situação própria
    @Override
    public String getSituation(){
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
    
    
}