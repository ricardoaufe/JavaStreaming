/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author Ricardo Ferreira
 */
public class Video {
    private int id;
    private String title;
    private String description;
    private String type;
    
    public Video() {
    }

    public Video(int id, String title, String description, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public Video(String title, String description, String type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public int getIt() {
        return id;
    }

    public void setIt(int it) {
        this.id = it;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
