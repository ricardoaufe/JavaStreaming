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
    
    public String getUserReaction(int userId, int videoId) throws SQLException{
        String sql = 
        "SELECT reaction FROM reactionstb WHERE user_id = ? AND video_id = ?";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, videoId);
        
        
        ResultSet result = statement.executeQuery();
        
        if(result.next()){
            return result.getString("reaction");
        }
        
        return null;
    }
    
    public void removeReaction(int userId, int videoId) throws SQLException{
        String sql = 
                "DELETE FROM reactionstb WHERE user_id = ? AND video_id = ?";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, videoId);
        
       statement.executeUpdate();
    }
    
    public void toggleReaction(int userId, int videoId, String newReaction)
            throws SQLException{
        String currentReaction = getUserReaction(userId, videoId);
        
        if (currentReaction != null && currentReaction.equalsIgnoreCase(newReaction)){
            removeReaction(userId, videoId);
        }else{
            react(userId, videoId, newReaction);
        }
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
