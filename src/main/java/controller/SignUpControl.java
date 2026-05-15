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
        String name = screen3.getTxtName().getText().trim();
        String signup = screen3.getTxtUserSign().getText().trim();
        String password = screen3.getTxtPwdSign().getText().trim();
        
        if (name.isBlank() || signup.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(screen3,
                    "Preencha nome, usuário e senha.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        User user = new User(name, signup, password);
        
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            UserDAO dao = new UserDAO(conn);
            dao.insert(user);
            JOptionPane.showMessageDialog(screen3, "Usuário Cadastrado!",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            
            view.Login login = new view.Login();
            login.setVisible(true);
            screen3.dispose();
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            JOptionPane.showMessageDialog(screen3, 
                    "Usuário Não Cadastrado!\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
}
