/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sa.sait.services;

import ca.sait.dataaccess.ConnectionPool;
import ca.sait.models.Role;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Arcto
 */
public class RoleService {
    private ArrayList<Role> roles;

    public RoleService() {

    }

    public ArrayList<Role> getAll() {
        Connection conn = ConnectionPool.getInstance().getConnection();
        roles = new ArrayList();

        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM role");

            while (rs.next()) {
                
                int roleId = rs.getInt(1);
                String roleName = rs.getString(2);
                
                Role role = new Role(roleId, roleName);
                
                roles.add(role);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return roles;
    }
}
