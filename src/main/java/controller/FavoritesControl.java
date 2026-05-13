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
                    result.getString("genre")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen,
                    "Erro ao carregar favoritos:\n" + e.getMessage());
        }
    }
    
    public void removeSelectedFavorite() {
        int selectedRow = screen.getTbl_favorites().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(screen, "Selecione um favorito.");
            return;
        }

        try {
            int videoId = (int) screen.getTbl_favorites().getValueAt(selectedRow, 0);
            int userId = screen.getUser().getId();

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            FavoriteDAO dao = new FavoriteDAO(conn);
            dao.removeFavorite(userId, videoId);

            loadFavorites();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen,
                    "Erro ao remover favorito:\n" + e.getMessage());
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
    
    public void renameSelectedPlaylist() {
        int selectedRow = screen.getTbl_playlists().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(screen, "Selecione uma playlist.");
            return;
        }

        String newName = screen.getTxt_playlistName().getText();

        if (newName.isBlank()) {
            JOptionPane.showMessageDialog(screen, "Digite o novo nome da playlist.");
            return;
        }

        try {
            int playlistId = (int) screen.getTbl_playlists().getValueAt(selectedRow, 0);

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);
            dao.renamePlaylist(playlistId, newName);

            screen.getTxt_playlistName().setText("");
            loadPlaylists();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen, "Erro ao renomear playlist.");
        }
    }
    
    public void deleteSelectedPlaylist() {

        int selectedRow = screen.getTbl_playlists().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(screen,
                    "Selecione uma playlist.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                screen,
                "Deseja excluir esta playlist?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            int playlistId =
                    (int) screen.getTbl_playlists()
                            .getValueAt(selectedRow, 0);

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);

            dao.deletePlaylist(playlistId);

            loadPlaylists();

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(screen,
                    "Erro ao excluir playlist.");
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
    
    public void addSelectedFavoriteToPlaylist(){
        int favoriteRow = screen.getTbl_favorites().getSelectedRow();
        int playlistRow = screen.getTbl_playlists().getSelectedRow();

        if (favoriteRow == -1) {
            JOptionPane.showMessageDialog(screen, "Selecione um vídeo favorito.");
            return;
        }

        if (playlistRow == -1) {
            JOptionPane.showMessageDialog(screen, "Selecione uma lista de reprodução.");
            return;
        }

        try {
            int videoId = (int) screen.getTbl_favorites().getValueAt(favoriteRow, 0);
            int playlistId = (int) screen.getTbl_playlists().getValueAt(playlistRow, 0);

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);
            dao.addVideoToPlaylist(playlistId, videoId);

            JOptionPane.showMessageDialog(screen, "Vídeo adicionado à lista!");

            loadPlaylists();

        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(screen,
                    "Erro ao adicionar vídeo à lista:\n" + e.getMessage());
        }
    }
    
    public void openSelectedPlaylist() {
        int selectedRow = screen.getTbl_playlists().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(screen, "Selecione uma lista.");
            return;
        }

        int playlistId = (int) screen.getTbl_playlists().getValueAt(selectedRow, 0);
        String playlistName = screen.getTbl_playlists().getValueAt(selectedRow, 1).toString();

        new view.PlaylistDetails(screen.getUser(), playlistId, playlistName).setVisible(true);
        screen.dispose();
    }

}
