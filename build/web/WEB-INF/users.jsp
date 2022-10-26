<%-- 
    Document   : users
    Created on : 24-Oct-2022, 8:49:49 PM
    Author     : Arcto
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Accounts</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body class="bg-dark text-white" style="font-size: 14px;">
        <div class="d-flex flex-column flex-xl-row flex-fill justify-content-between mt-5 container-fluid"> 
            <form action="user" method="POST" class="bg-secondary text-white mb-2 mt-2 p-2 ml-lg-3 ml-1 mr-2 border border-light">
                <input type="hidden" name="action" value="addUser">
                <h2 class="text-center">Add User</h2>
                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="inputEmail">Email</label>
                        <input type="email" name="inputEmail" class="form-control" id="inputEmail" placeholder="Email">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="inputPassword">Password</label>
                        <input min=6 max=24 type="password" name="inputPassword" class="form-control" id="inputPassword" placeholder="Password">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="inputFirstName">First Name</label>
                        <input min=2 max=24 type="text" name="inputFirstName" class="form-control" id="inputFirstName" placeholder="First Name">
                    </div>
                    <div class="form-group col-md">
                        <label for="inputLastName">Last Name</label>
                        <input min=2 max=24 type="text" name="inputLastName" class="form-control" id="inputLastName" placeholder="Last Name">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="inputRole">Role</label>
                        <select id="inputRole" name="inputRole" class="form-control">
                            <option selected>Select a role...</option>
                            <c:forEach var="role" items="${roles}">
                                <option>${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md">
                        <label for="inputActive">Active</label>
                        <select id="inputActive" name="inputActive" class="form-control">
                            <option selected>Is active...</option>
                            <option>Yes</option>
                            <option>No</option>
                        </select>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary bg-light text-dark">Save</button>
            </form>
            <div class="border border-light mb-2 mt-2 ml-4 mr-4 p-2 flex-grow-1">
                <h2 class="text-center">Manage Users</h2>
                <table class="table table-striped table-dark">
                    <thead>
                        <tr>
                            <th scope="col">Email</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Last Name</th>
                            <th scope="col">Role</th>
                            <th scope="col">Active</th>
                            <th scope="col">Edit</th>
                            <th scope="col">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.email}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.active==true}">
                                            Yes 
                                        </c:when>    
                                        <c:otherwise>
                                            No
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a href="user?action=edit&user=${user.email}">Edit</a></td>
                                <td><a href="user?action=delete&user=${user.email}">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <c:if test="${selectedUser != null}">
                            <form class="bg-secondary text-white mb-2 mt-2 p-2 ml-1 mr-lg-3 mr-2 border border-light flex-grow-2" style="font-size: 16px">
                <h2 class="text-center">Edit User</h2>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="selectedEmail">Email</label>
                        <input name="selectedEmail" disabled type="email" class="form-control" id="selectedEmail" placeholder="${selectedUser.email}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="selectedFirstName">First Name</label>
                        <input name="selectedFirstName" type="text" class="form-control" id="selectedFirstName" placeholder="${selectedUser.firstName}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="selectedLastName">Last Name</label>
                        <input name="selectedLastName" type="text" class="form-control" id="selectedLastName" placeholder="${selectedUser.lastName}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="selectedRole">Role</label>
                        <select name="selectedRole" id="selectedRole" class="form-control">
                            <option selected>${selectedUser.role}</option>
                            <c:forEach var="role" items="${roles}">
                                <option>${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md">
                        <label for="selectedActive">Active</label>
                        <select name="selectedActive" id="selectedActive" class="form-control">
                            <option selected>
                                <c:choose>
                                    <c:when test="${selectedUser.active==true}">
                                        Yes 
                                    </c:when>    
                                    <c:otherwise>
                                        No
                                    </c:otherwise>
                                </c:choose></option>
                            <option>Yes</option>
                            <option>No</option>
                        </select>
                    </div>
                </div>
                <button type="submit" disabled class="btn btn-primary bg-light text-dark">Save</button>
                <button type="submit" disabled class="btn btn-primary bg-light text-dark">Cancel</button>
            </form>
            </c:if>
        </div>
    </body>
</html>
