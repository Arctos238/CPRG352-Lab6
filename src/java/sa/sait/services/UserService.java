/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sa.sait.services;

import ca.sait.dataaccess.ConnectionPool;
import ca.sait.dataaccess.UserDB;
import ca.sait.models.Role;
import ca.sait.models.User;
import java.sql.Connection;
import java.util.ArrayList;


/**
 *
 * @author J.Pointer
 */
public class UserService {

    private ArrayList<User> users;

    public UserService() {

    }

    public ArrayList<User> getAll(ArrayList<Role> roles) {
        users = new ArrayList();
        
        UserDB udb = new UserDB();
        
        udb.getAll(users, roles);

        return users;
    }

    public void updateUser(User user) {
        UserDB udb = new UserDB();
        
        udb.updateUser(user);
        
        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(user.getEmail())) {
                users.get(i).setFirstName(user.getFirstName());
                users.get(i).setLastName(user.getLastName());
                users.get(i).setRole(user.getRole());
                users.get(i).setActive(user.isActive());
            }
        }
    }
    
    public void deleteUser(User user) {
        UserDB udb = new UserDB();
        
        udb.deleteUser(user);
        
        users.remove(user);
    }
    
    public void createUser(User user) {
        UserDB udb = new UserDB();
        
        udb.createUser(user);
        
        users.add(user);
    }
}
