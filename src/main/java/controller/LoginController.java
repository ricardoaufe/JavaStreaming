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


public class LoginController {
    private Login screen1;

    public LoginController(Login screen1) {
        this.screen1 = screen1;
    }
        
    public void userLogin(){
        User user = new User( null, screen1.getTxtUserLogin().getText(),
        screen1.getTxtPwdLog().getText());
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            UserDAO dao = new UserDAO(conn);
            ResultSet res = dao.consult(user);
            if(res.next()){
                JOptionPane.showMessageDialog(screen1, "Login Feito!",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
                String name =  res.getString("name");
                String user = res.getString("user"); //VERIFICAR DEPOIS
                String pwd = res.getString("password");
                //Logged screen2 = new Logged(new User(name, user, pwd));
                //screen2.setVisible(true);
                //screen1.setVisibule(false);
              
            }else{
                JOptionPane.showMessageDialog(screen1, "Login não efetuado :(",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(screen1, "Erro de conexão", 
            "Erro", JOptionPane.ERROR_MESSAGE);
        }
      }
   }
    
