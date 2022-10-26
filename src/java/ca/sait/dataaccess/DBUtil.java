/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sait.dataaccess;

import static ca.sait.dataaccess.ConnectionPool.getInstance;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J.Pointer
 */
public class DBUtil {
    Connection conn;
    public DBUtil() {
        
    }
    
    private void openConnection(){
        this.conn = ConnectionPool.getInstance().getConnection();
    }
    
    
    private void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int updateQuery (String updateQuery) {
        openConnection();
        
        try {
            Statement stmt = conn.createStatement();
            int rowsUpdated  = stmt.executeUpdate(updateQuery);
            closeConnection();
            
            return rowsUpdated;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        closeConnection();
        return -1;
    }
}
