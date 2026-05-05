/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class User {
    private String user, name, password;

    public User() {
    }

    public User(String name, String user, String password) {
        System.out.println("DEBUG CONSTRUCTOR name: [" + name + "]");
        System.out.println("DEBUG CONSTRUCTOR user: [" + user + "]");
        System.out.println("DEBUG CONSTRUCTOR password: [" + password + "]");
        this.name = name;
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
