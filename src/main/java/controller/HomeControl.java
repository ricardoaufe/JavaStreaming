/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Connect;
import dao.VideoDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
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
      
            String text = "";
            
            while (result.next()){
                text += "Título: " + result.getString("title") + "\n";
                text += "Tipo: " + result.getString("type") + "\n";
                text += "Descrição: " + result.getString("description") + "\n";
                text += "-----------------------------\n";
        }
            if (text.isEmpty()){
                text = "Nenhum vídeo encontrado.";
            }
            
            screen.getTxtAreaResults().setText(text);
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(screen, "Erro ao buscar vídeos: \n" +
                    e.getMessage());
        }
    }

    
    
}
