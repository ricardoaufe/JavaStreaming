/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.Connect;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.User;
import view.SignUp;
/**
 *
 * @author Ricardo Ferreira
 */
public class SignUpControl {
    private SignUp screen3;

    public SignUpControl(SignUp screen3) {
        this.screen3 = screen3;
    }
    
    public void saveUser(){
        String name = screen3.getTxtName().getText();
        String signup = screen3.getTxtUserSign().getText();
        String password = screen3.getTxtPwdSign().getText();
        User user = new User(name, signup, password);
        
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            UserDAO dao = new UserDAO(conn);
            dao.insert(user);
            JOptionPane.showMessageDialog(screen3, "Usuário Cadastrado!",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(screen3, "Usuário Não Cadastrado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
}
