/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;/**
 *
 * @author Ricardo Ferreira
 */
public class PlaylistDAO {

    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }

    public void createPlaylist(int userId, String name) throws SQLException {
        String sql = "INSERT INTO playliststb (user_id, name) VALUES (?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setString(2, name);

        statement.executeUpdate();
    }

    public ResultSet listPlaylists(int userId) throws SQLException {
        String sql = """
            SELECT p.id, p.name, COUNT(pv.video_id) AS total_videos
            FROM playliststb p
            LEFT JOIN playlist_videostb pv ON p.id = pv.playlist_id
            WHERE p.user_id = ?
            GROUP BY p.id, p.name
            ORDER BY p.name
        """;

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userId);

        return statement.executeQuery();
    }

    public void renamePlaylist(int playlistId, String newName) throws SQLException {
        String sql = "UPDATE playliststb SET name = ? WHERE id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, newName);
        statement.setInt(2, playlistId);

        statement.executeUpdate();
    }

    public void deletePlaylist(int playlistId) throws SQLException {
        String sql = "DELETE FROM playliststb WHERE id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, playlistId);

        statement.executeUpdate();
    }
    
    public void addVideoToPlaylist(int playlistId, int videoId) throws SQLException {
    String sql = """
        INSERT INTO playlist_videostb (playlist_id, video_id, position)
        VALUES (?, ?, (
            SELECT COALESCE(MAX(position), 0) + 1 
            FROM playlist_videostb
            WHERE playlist_id = ?
        ))
    """;

    PreparedStatement statement = conn.prepareStatement(sql);
    statement.setInt(1, playlistId);
    statement.setInt(2, videoId);
    statement.setInt(3, playlistId);

    statement.executeUpdate();
    }
    
    public void removeVideoFromPlaylist(int playlistId, int videoId)
            throws SQLException {

        String sql = """
            DELETE FROM playlist_videostb
            WHERE playlist_id = ? AND video_id = ?
        """;

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setInt(1, playlistId);
        statement.setInt(2, videoId);

        statement.executeUpdate();
    }
    
    public ResultSet listPlaylistVideos(int playlistId) throws SQLException {

    String sql = """
        SELECT pv.position, v.id, v.title, v.type
        FROM playlist_videostb pv
        INNER JOIN videostb v
            ON pv.video_id = v.id
        WHERE pv.playlist_id = ?
        ORDER BY pv.position
    """;

    PreparedStatement statement = conn.prepareStatement(sql);

    statement.setInt(1, playlistId);

    return statement.executeQuery();
}
}
