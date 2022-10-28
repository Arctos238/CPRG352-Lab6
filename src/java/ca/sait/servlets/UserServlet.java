/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ca.sait.servlets;

import ca.sait.models.Role;
import ca.sait.models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sa.sait.services.RoleService;
import sa.sait.services.UserService;

/**
 *
 * @author J.Pointer
 */
public class UserServlet extends HttpServlet {

    ArrayList<Role> roles;
    ArrayList<User> users;
    UserService userService;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleService roleService = new RoleService();
        roles = roleService.getAll();
        
        
        
        userService = new UserService();
        
        users = userService.getAll(roles);
        
         if (users == null) {
            users = new ArrayList<>();
        }

        request.getSession().setAttribute("users", users);
        request.getSession().setAttribute("roles", roles);

        String action = request.getParameter("action");

        if (action != null && action.equals("edit")) {
            request.getSession().setAttribute("message", null);
            String userEmail = request.getParameter("user").replaceAll("\\s+", "+");

            User user = null;

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(userEmail)) {
                    user = users.get(i);
                }
            }
            request.getSession().setAttribute("selectedUser", user);
        } else if (action != null && action.equals("delete")) {
            
            String userEmail = request.getParameter("user").replaceAll("\\s+", "+");
            User user = null;

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(userEmail)) {
                    user = users.get(i);
                }
            }

            userService.deleteUser(user);

            response.sendRedirect("user");
            request.getSession().invalidate();
            return;
        } else if (action != null && action.equals("cancel")) {
            request.getSession().invalidate();
            response.sendRedirect("user");
            return;
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("addUser")) {
            boolean validInputs = true;

            String inputEmail = request.getParameter("inputEmail");
            String inputPassword = request.getParameter("inputPassword");
            String inputFirstName = request.getParameter("inputFirstName");
            String inputLastName = request.getParameter("inputLastName");
            String inputRole = request.getParameter("inputRole");
            String inputActive = request.getParameter("inputActive");

            // Converts inputActive field to boolean
            boolean booleanInputActive = inputActive.equals("Yes");

            // Converts inputRole to Role.
            Role newRole = null;
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getRoleName().equals(inputRole)) {
                    newRole = roles.get(i);
                }
            }
            
            String message = null;
            
            
            if (inputEmail.length() == 0) {
                validInputs = false;
                message = "No email entered or email is invalid";
            } else if (inputPassword.length() == 0) {
                validInputs = false;
                message = "No password entered or password is invalid";
            } else if (inputFirstName.length() == 0) {
                validInputs = false;
                message = "No first name entered or first name is invalid";
            } else if (inputLastName.length() == 0) {
                validInputs = false;
                message = "No last name entered or last name is invalid";
            } else if (newRole == null) {
                validInputs = false;
                message = "Role not selected";
            } else if (inputRole.equals("Select a role...")) {
                validInputs = false;
                message = "Role field not selected";
            } else if (inputActive.equals("Is active...")) {
                validInputs = false;
                message = "Active field not selected";
            } else if (validInputs) {
                User user = new User(inputEmail, booleanInputActive, inputFirstName, inputLastName, inputPassword, newRole);
                userService.createUser(user);
                message = "Success";
            }
            
            request.getSession().setAttribute("message", message);
            response.sendRedirect("user");
            return;
        } else if (action.equals("update")) {
            String selectedEmail = request.getParameter("selectedEmail");
            User selectedUser = null;

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(selectedEmail)) {
                    selectedUser = users.get(i);
                }
            }

            String selectedFirstName = request.getParameter("selectedFirstName");
            String selectedLastName = request.getParameter("selectedLastName");
            String selectedRole = request.getParameter("selectedRole");
            String selectedActive = request.getParameter("selectedActive");

            Role newRole = null;
            
            if (selectedRole.startsWith("Current:")) {
                newRole = selectedUser.getRole();
            } else {
                for (int i = 0; i < roles.size(); i++) {
                    if (roles.get(i).getRoleName().equals(selectedRole)) {
                        newRole = roles.get(i);
                    }
                }
            }

            boolean newActive = selectedActive.equals("Yes");
            
            if (selectedActive.startsWith("Current:")) {
                
                newActive = selectedUser.isActive();
            }
            

            selectedUser.setFirstName(selectedFirstName);
            selectedUser.setLastName(selectedLastName);
            selectedUser.setRole(newRole);
            selectedUser.setActive(newActive);

            userService.updateUser(selectedUser);

            request.getSession().invalidate();
            response.sendRedirect("user");
            return;
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }
}
