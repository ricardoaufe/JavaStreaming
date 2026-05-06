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
public class ReactionDAO {
    private Connection conn;
    
    public ReactionDAO(Connection conn){
        this.conn = conn;
    }
    
    public void react(
            int userId, int videoId, String reaction) throws SQLException{
        String sql = """
            INSERT INTO reactionstb (user_id, video_id, reaction)
            VALUES (?, ?, ?)
            ON CONFLICT (user_id, video_id)
            DO UPDATE SET reaction = EXCLUDED.reaction
        """;
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, videoId);
        statement.setString(3, reaction);
        
        statement.executeUpdate();
    }
    
    public int countLikes(int videoId) throws SQLException{
        return countReaction(videoId, "LIKE");
    }
    public int countDislikes(int videoId) throws SQLException{
        return countReaction(videoId, "DISLIKE");
    }
    
    private int countReaction(int videoId, String reaction) throws SQLException{
        String sql = """
            SELECT COUNT(*) AS total FROM reactionstb 
            WHERE video_id = ? AND reaction = ?        
        """;
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, videoId);
        statement.setString(2, reaction);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return result.getInt("total");
        }

        return 0;
    }
}
