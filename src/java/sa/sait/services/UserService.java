/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sa.sait.services;

import ca.sait.dataaccess.ConnectionPool;
import ca.sait.models.Role;
import ca.sait.models.User;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arcto
 */
public class UserService {

    private ArrayList<User> users;

    public UserService() {

    }

    public ArrayList<User> getAll(ArrayList<Role> roles) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        users = new ArrayList();

        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            while (rs.next()) {
                String userEmail = rs.getString(1);
                boolean userActive = (rs.getInt(2) == 1) ? true : false;
                String userFirstName = rs.getString(3);
                String userLastName = rs.getString(4);
                String userPassword = rs.getString(5);
                int role = rs.getInt(6);
                
                String userRole = "No Role Assigned";
                
                for(int i = 0; i < roles.size(); i++) {
                    System.err.println(role);
                    if(role == roles.get(i).getRoleId()) {
                        userRole = roles.get(i).getRoleName();
                    }
                }
                
                User user = new User(userEmail, userActive, userFirstName, userLastName, userPassword, userRole);
                
                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public void updateUser(User user) {
        users.add(user);
    }
    
    public void deleteUser(User user) {
        users.remove(user);
    }
}
