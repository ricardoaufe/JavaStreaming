/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Connect;
import dao.VideoDAO;
import dao.ReactionDAO;
import dao.FavoriteDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.Home;

/**
 *
 * @author Ricardo Ferreira
 */
public class HomeControl {
    private Home screen;
    
    public HomeControl(Home screen){
        this.screen = screen;
    }

    public void searchVideo(){
        String title = screen.getTxtSearchVideo().getText();
        
        try{
            Connect connect = new Connect();
            Connection conn = connect.getConnection();
            
            VideoDAO dao = new VideoDAO(conn);
            ResultSet result = dao.searchByTitle(title);
            
            ReactionDAO reactionDAO = new ReactionDAO(conn);
           
            String text = "";
            
            DefaultTableModel model = 
                    (DefaultTableModel) screen.getTbl_videos().getModel();
            
            model.setRowCount(0);
            
            while (result.next()) {
                int videoId = result.getInt("id");

                int likes = reactionDAO.countLikes(videoId);
                int dislikes = reactionDAO.countDislikes(videoId);

                model.addRow(new Object[]{
                    videoId,
                    result.getString("title"),
                    result.getString("type"),
                    result.getString("genre"),
                    result.getString("situation"),
                    likes,
                    dislikes
                });
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(screen, "Nenhum vídeo encontrado.");
            }
              screen.getTbl_videos().getColumn(title);
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen, "Erro ao buscar vídeos: \n" +
                    e.getMessage());
        }
    }
            
            
    public void reactToSelectedVideo(String reactionType){

        int selectedRow = screen.getTbl_videos().getSelectedRow();

        if(selectedRow == -1){
            JOptionPane.showMessageDialog(screen, "Selecione um vídeo");
            return;
        }

        try{
            int videoId = (int) screen.getTbl_videos()
                    .getValueAt(selectedRow, 0);

            int userId = screen.getUser().getId();

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            ReactionDAO dao = new ReactionDAO(conn);

            dao.toggleReaction(userId, videoId, reactionType);

            searchVideo();
        }catch(SQLException e) {
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(screen, "Erro ao reageir ao vídeo");
        }
    } 

    public void toggleFavoriteSelectedVideo() {
    int selectedRow = screen.getTbl_videos().getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(screen, "Selecione um vídeo.");
        return;
    }

    try {
        int videoId = (int) screen.getTbl_videos().getValueAt(selectedRow, 0);
        int userId = screen.getUser().getId();

        Connect connect = new Connect();
        Connection conn = connect.getConnection();

       FavoriteDAO dao = new FavoriteDAO(conn);
        boolean favorited = dao.toggleFavorite(userId, videoId);

        String title = screen.getTbl_videos()
                .getValueAt(selectedRow, 1)
                .toString();

        if (favorited) {

            JOptionPane.showMessageDialog(screen,
                    "\"" + title + "\" foi adicionado aos favoritos!");

        } else {

            JOptionPane.showMessageDialog(screen,
                    "\"" + title + "\" foi removido dos favoritos!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(screen,
                "Erro ao atualizar favorito:\n" + e.getMessage());
    }
}
          

    
    
}
