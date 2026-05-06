/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ricardo Ferreira
 */
public class Films extends Video{
    public Films(String title, String description){
        super(title, description);
    }
    
    @Override
    public String getType(){
        return "Filme";
    }
}
