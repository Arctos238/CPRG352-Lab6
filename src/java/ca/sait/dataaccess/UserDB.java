/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sait.dataaccess;

import ca.sait.models.Role;
import ca.sait.models.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J.Pointer
 */
public class UserDB {

    public UserDB() {

    }

    public void createUser(User user) {
        int active = user.isActive() ? 1 : 0;
        
        String updateStmt = "INSERT INTO user (email, active, "
                + "first_name, last_name, password, role)" + 
                "VALUES (\"" + user.getEmail() + "\", " + active 
                + ", \"" + user.getFirstName() + "\", " +
                "\"" + user.getLastName() + "\", " +
                "\"" + user.getPassword()+ "\", " +
                user.getRole().getRoleId() + ")";
        
        DBUtil dbu = new DBUtil();
        int rowsUpdated = dbu.updateQuery(updateStmt);
        
    }

    public void deleteUser(User user) {

        String updateStmt = "DELETE FROM user \n"
                + "WHERE email = \"" + user.getEmail() + "\"";

        DBUtil dbu = new DBUtil();
        int rowsupdated = dbu.updateQuery(updateStmt);

    }

    public void updateUser(User user) {
        int active = user.isActive() ? 1 : 0;
        
        String updateStmt = "UPDATE user "
                + "SET active = " + active + ", "
                + "first_name = \"" + user.getFirstName() + "\", "
                + "last_name = \"" + user.getLastName() + "\", "
                + "role = \"" + user.getRole().getRoleId() + "\", "
                + "active = \"" + active + "\"";

        DBUtil dbu = new DBUtil();
        int rowsupdated = dbu.updateQuery(updateStmt);
    }

    public void getAll(ArrayList<User> users, ArrayList<Role> roles) {
        Connection conn = ConnectionPool.getInstance().getConnection();
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

                Role userRole = null;

                for (int i = 0; i < roles.size(); i++) {
                    System.err.println(role);
                    if (role == roles.get(i).getRoleId()) {
                        userRole = roles.get(i);
                    }
                }

                User user = new User(userEmail, userActive, userFirstName, userLastName, userPassword, userRole);

                users.add(user);

                try {
                    conn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.close();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
