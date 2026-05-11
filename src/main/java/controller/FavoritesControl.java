/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Connect;
import dao.FavoriteDAO;
import dao.PlaylistDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.Favorites;

/**
 *
 * @author Ricardo Ferreira
 */
public class FavoritesControl {
    
    private Favorites screen;

    public FavoritesControl(Favorites screen) {
        this.screen = screen;
    }
    
    public void loadFavorites() {
        try {
            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            FavoriteDAO dao = new FavoriteDAO(conn);
            ResultSet result = dao.listFavorites(screen.getUser().getId());

            DefaultTableModel model =
                    (DefaultTableModel) screen.getTbl_favorites().getModel();

            model.setRowCount(0);

            while (result.next()) {
                model.addRow(new Object[]{
                    result.getInt("id"),
                    result.getString("title"),
                    result.getString("type"),
                    result.getString("description")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen,
                    "Erro ao carregar favoritos:\n" + e.getMessage());
        }
    }
    
    public void createPlaylist() {

        String name = screen.getTxt_playlistName().getText();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(screen,
                    "Digite um nome para a lista.");
            return;
        }

        try {
            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);

            dao.createPlaylist(
                    screen.getUser().getId(),
                    name
            );

            screen.getTxt_playlistName().setText("");

            loadPlaylists();

        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(screen,
                    "Erro ao criar playlist.");
        }
    }
    
    public void loadPlaylists() {

        try {
            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);

            ResultSet result = dao.listPlaylists(
                    screen.getUser().getId()
            );

            DefaultTableModel model =
                    (DefaultTableModel) screen.getTbl_playlists().getModel();

            model.setRowCount(0);

            while (result.next()) {

                model.addRow(new Object[]{
                    result.getInt("id"),
                    result.getString("name"),
                    result.getInt("total_videos")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(screen,
                    "Erro ao carregar playlists.");
        }
    }

}
