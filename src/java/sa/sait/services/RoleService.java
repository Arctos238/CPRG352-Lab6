/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sa.sait.services;

import ca.sait.dataaccess.ConnectionPool;
import ca.sait.dataaccess.RoleDB;
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
public class RoleService {
    private ArrayList<Role> roles;

    public RoleService() {

    }

    public ArrayList<Role> getAll() {
        roles = new ArrayList();
        
        RoleDB rdb = new RoleDB();
        
        rdb.getAll(roles);

        return roles;
    }
}
