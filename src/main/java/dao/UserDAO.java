/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.User;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet consult (User user) throws SQLException{
        String sql = "select * from userstb where \"user\" = ? and password = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        System.out.println("DEBUG DAO user.getUser(): [" + user.getUser() + "]");
        System.out.println("DEBUG DAO user.getPassword(): [" + user.getPassword() + "]");
        
        statement.setString(1, user.getUser());
        statement.setString(2, user.getPassword());
        statement.execute();
        return statement.executeQuery();
        
    }
    
    public void insert (User user) throws SQLException{
        String sql = 
                "insert into userstb (name, user, password) values (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getUser());
        statement.setString(3, user.getPassword());
        statement.execute();
        conn.close();
        
    }
    

}
