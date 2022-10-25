/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sait.dataaccess;

import ca.sait.models.Role;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author J.Pointer
 */
public class RoleDB {
    public RoleDB() {
        
    }
    
     public void createRole(Role role) {
        String updateStmt = "INSERT INTO (role_id, role_name"
                + "VALUES (" + role.getRoleId() + ", \"" + role.getRoleName()
                + "\")";
        DBUtil dbu = new DBUtil();
        int rowsupdated = dbu.updateQuery(updateStmt);
    }

    public void deleteRole(Role role) {
        String updateStmt = "DELETE FROM role \n"
                + "WHERE role_id =" + role.getRoleId();

        DBUtil dbu = new DBUtil();
        int rowsupdated = dbu.updateQuery(updateStmt);

    }

    public void updateRole(Role role) {
        String updateStmt = "UPDATE role"
                + "SET role_name = \"" + role.getRoleName() + "\""
                + "WHERE role_id = " + role.getRoleId();

        DBUtil dbu = new DBUtil();
        int rowsupdated = dbu.updateQuery(updateStmt);
    }

    
    
    public void getAll(ArrayList<Role> roles) {
         try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM role");
            
            while (rs.next()) {
                
                int roleId = rs.getInt(1);
                String roleName = rs.getString(2);
                
                Role role = new Role(roleId, roleName);
                
                roles.add(role);
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        
}
