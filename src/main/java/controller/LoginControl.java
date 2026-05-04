/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.Connect;
import model.User;
import view.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class LoginControl {
    private Login screen1;

    public LoginControl(Login screen1) {
        this.screen1 = screen1;
    }
        
    public void userLogin(){
        User user = new User( null, screen1.getTxtUserLogin().getText(),
        screen1.getTxtPwdLog().getText());
        
        System.out.println("DEBUG: Tentando logar com Usuário: [" + 
                screen1.getTxtUserLogin().getText() + "]");
        System.out.println("DEBUG: Tentando logar com Senha: [" + 
                screen1.getTxtPwdLog().getText() + "]");
        
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            UserDAO dao = new UserDAO(conn);
            ResultSet res = dao.consult(user);
            if(res.next()){
                JOptionPane.showMessageDialog(screen1, "Login Feito!",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
                String name =  res.getString("name");
                String login = res.getString("user");
                String password = res.getString("password");
                
                User loggedUser = new User(login, name, password);
                
                view.Home home = new view.Home();
                home.setVisible(true);
                screen1.setVisible(false);
          
            }else{
                JOptionPane.showMessageDialog(screen1, "Login não efetuado :(",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }catch(SQLException e){
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(screen1, "Erro de conexão", 
            "Erro", JOptionPane.ERROR_MESSAGE);
        }
      }
   }
    
