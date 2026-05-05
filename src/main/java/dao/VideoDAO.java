/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Video;

/**
 *
 * @author Ricardo Ferreira
 */
public class VideoDAO {
    
    private Connection conn;

    public VideoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void insert(Video video) throws SQLException{
        String sql = 
            "INSERT INTO videostb (title, description, type) VALUES (?, ?, ?)";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, video.getTitle());
        statement.setString(2, video.getDescription());
        statement.setString(3, video.getType());
        
        statement.executeUpdate();
        conn.close();
        
    }
    
    public ResultSet searchByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM videostb WHERE title ILIKE ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%" + title + "%");

        return statement.executeQuery();
    }
}
