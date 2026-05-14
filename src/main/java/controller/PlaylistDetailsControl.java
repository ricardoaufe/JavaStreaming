/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Connect;
import dao.PlaylistDAO;
import model.Video;
import model.Films;
import model.Series;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.PlaylistDetails;

/**
 *
 * @author Ricardo Ferreira
 */
public class PlaylistDetailsControl {
    private PlaylistDetails screen;
    
    public PlaylistDetailsControl(PlaylistDetails screen){
        this.screen = screen;
    }
    
    public void loadPlaylistVideos(){
        try{
            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);

            ResultSet result = dao.listPlaylistVideos(screen.getPlaylistId());
            
            DefaultTableModel model = (DefaultTableModel)
                    screen.getTbl_playlistVideos().getModel();
            
             model.setRowCount(0);

            while (result.next()) {
                String type = result.getString("type");

                Video video;

                if (type.equalsIgnoreCase("Filme")) {
                    video = new Films(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("genre")
                    );
                } else {
                    video = new Series(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("genre"),
                            ""
                    );
                }

                model.addRow(new Object[]{
                    video.getId(),
                    video.getTitle(),
                    video.getType()
                });
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(screen,
                    "Erro ao carregar vídeos da playlist.");
        }
    }
    
    public void removeSelectedVideo() {

        int selectedRow =
                screen.getTbl_playlistVideos().getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(screen,
                    "Selecione um vídeo.");

            return;
        }

        try {

            int videoId =
                    (int) screen.getTbl_playlistVideos()
                            .getValueAt(selectedRow, 0);

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            PlaylistDAO dao = new PlaylistDAO(conn);

            dao.removeVideoFromPlaylist(
                    screen.getPlaylistId(),
                    videoId
            );

            loadPlaylistVideos();

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(screen,
                    "Erro ao remover vídeo da playlist.");
        }
    }
}