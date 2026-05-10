/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ricardo Ferreira
 */
public class FavoriteDAO {
    private Connection conn;

    public FavoriteDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean isFavorite(int userId, int videoId) throws SQLException{
        String sql = 
                "SELECT * FROM favoritestb WHERE user_id = ? AND video_id = ?";
        
        PreparedStatement statement = conn.prepareStatement(sql);
    statement.setInt(1, userId);
        statement.setInt(2, videoId);

        ResultSet result = statement.executeQuery();

        return result.next();
    }

    public void addFavorite(int userId, int videoId) throws SQLException {
        String sql = """
            INSERT INTO favoritestb (user_id, video_id)
            VALUES (?, ?)
            ON CONFLICT (user_id, video_id) DO NOTHING
        """;

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, videoId);

        statement.executeUpdate();
    }

    public void removeFavorite(int userId, int videoId) throws SQLException {
        String sql = 
                "DELETE FROM favoritestb WHERE user_id = ? AND video_id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, videoId);

        statement.executeUpdate();
    }

    public void toggleFavorite(int userId, int videoId) throws SQLException {
        if (isFavorite(userId, videoId)) {
            removeFavorite(userId, videoId);
        } else {
            addFavorite(userId, videoId);
        }
    }
}