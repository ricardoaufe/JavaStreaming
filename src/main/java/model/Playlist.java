/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
/**
 *
 * @author Ricardo Ferreira
 */
public class Playlist {

    private int id;
    private String name;
    private User user;

    private ArrayList<Video> videos;

    public Playlist(int id, String name, User user) {

        this.id = id;
        this.name = name;
        this.user = user;

        this.videos = new ArrayList<>();
    }

    public void addVideo(Video video) {
        videos.add(video);
    }

    public void removeVideo(Video video) {
        videos.remove(video);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}