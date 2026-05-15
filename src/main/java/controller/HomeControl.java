/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Video;
import model.Films;
import model.Series;
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
        //"Pega" o texto digitado no campo de busca
        String title = screen.getTxtSearchVideo().getText();
        
        try{
            Connect connect = new Connect();
            Connection conn = connect.getConnection();
            
            VideoDAO dao = new VideoDAO(conn);
            ResultSet result = dao.searchByTitle(title);
            
            ReactionDAO reactionDAO = new ReactionDAO(conn);
            FavoriteDAO favoriteDAO = new FavoriteDAO(conn);
            
            DefaultTableModel model = 
                    (DefaultTableModel) screen.getTbl_videos().getModel();
            
            //Limpa linhas da tabela antes de mostrar resultado
            model.setRowCount(0);
            
            while (result.next()) {
                int videoId = result.getInt("id");
                String type = result.getString("type");
                
                Video video;
                
                if (type.equalsIgnoreCase("Filme")) {
                video = new Films(
                        videoId,
                        result.getString("title"),
                        result.getString("genre"));
            } else {
                video = new Series(
                        videoId,
                        result.getString("title"),
                        result.getString("genre"),
                        result.getString("situation")
                );
            }
                
                //Usuário logado(getId) favoritou aquele vídeo (videoId)?
                boolean favorite =
                        favoriteDAO.isFavorite(
                                screen.getUser().getId(),
                                videoId
                        );

                //Contagem
                int likes = reactionDAO.countLikes(videoId);
                int dislikes = reactionDAO.countDislikes(videoId);
                
                String situation = "";

            //Esse obj vídeo é uma série?
            if (video instanceof Series) {
                Series s = (Series) video; //Downcasting
                situation = s.getSituation();
            }
                
                model.addRow(new Object[]{
                    video.getId(), //Oculto no Jframe
                    video.getTitle(),
                    video.getType(),
                    video.getGenre(),
                    situation,
                    likes,
                    dislikes,
                    favorite ? "★" : ""
                    });
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(screen, "Nenhum vídeo encontrado.");
            }
            
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen, "Erro ao buscar vídeos: \n" +
                    e.getMessage());
        }
    }
            
    //Método chamado no clique de Like ou Dislike
    public void reactToSelectedVideo(String reactionType){
        //Índice da linha selecionada (caso nenhuma retorna -1)
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
            //Interação
            dao.toggleReaction(userId, videoId, reactionType);

            searchVideo();
        }catch(SQLException e) {
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(screen, "Erro ao reageir ao vídeo");
        }
    } 
    //Método chamado no clique do Favoritar
    public void toggleFavoriteSelectedVideo() {
        int selectedRow = screen.getTbl_videos().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(screen, "Selecione um vídeo.");
            return;
        }

        try {
            int videoId = (int) screen.getTbl_videos()
                    .getValueAt(selectedRow, 0); //Pega linha e coluna da tabela
            
            int userId = screen.getUser().getId();

            Connect connect = new Connect();
            Connection conn = connect.getConnection();

            FavoriteDAO dao = new FavoriteDAO(conn);
           
            boolean favorited = dao.toggleFavorite(userId, videoId);
            //O título fica na coluna 1
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
            searchVideo();//Atualiza

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen,
                    "Erro ao atualizar favorito:\n" + e.getMessage());
        }
    }
         
}
