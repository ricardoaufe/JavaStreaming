/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author Ricardo Ferreira
 */
public abstract class Video {
    protected int id;
    protected String title;
    protected String description;
    
    protected int likes;
    protected int dislikes;
    
    public Video() {
    }

    public Video(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Video(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public abstract String getType();
    
}
    
    
    
