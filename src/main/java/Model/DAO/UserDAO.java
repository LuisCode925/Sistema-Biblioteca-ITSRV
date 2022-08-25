/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.ConnectionDB;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class UserDAO extends ConnectionDB {
    
    /**
     * 
     * @param ControlNumber that identifies the user: students and professors
     * @return all the data about the user.
     */
    public User getUser(int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        String sql = "SELECT * FROM users WHERE ControlNumber=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            rs = ps.executeQuery();
            
            User user = new User(0, "", "", null, "", 0, "", false);
            
            while(rs.next()) {                
                // Se comineza desde la columna 1 no del 0.
                user.setControlNumber(rs.getInt(1));
                user.setNames(rs.getString(2));
                user.setLastNames(rs.getString(3));
                user.setPhone(rs.getLong(4));
                user.setEmail(rs.getString(5));
                user.setCollegeCareer(rs.getInt(6));
                user.setAddress(rs.getString(7));
                user.setBanned(rs.getBoolean(8));
                
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * 
     * @param ControlNumber that identifies the user: students and professors
     * @return if can get more books or is restricted for Penalty
     */
    public boolean isBanned(int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        String sql = "SELECT Banned FROM users WHERE ControlNumber=?";
       
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            rs = ps.executeQuery();
            
            if (rs.next()) {                
                return rs.getBoolean(1);
            } else {
                throw new IllegalArgumentException("No Existe el Numero de Control.");   
            }
        } catch (SQLException e) {
            System.out.println("Error isBanned: "+e.getErrorCode());
        }
        return true;
    }
        
    /**
     * 
     * @return a list with all the Users form the data base. 
     */
    public List<User> getAllUsers(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<User> datos = new ArrayList<>();
        String sql = "select * from users";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
          
                User user = new User(0, "", "", null, "", 0, "", false);
                
                // Se comineza desde la columna 1 no del 0.
                user.setControlNumber(rs.getInt(1));
                user.setNames(rs.getString(2));
                user.setLastNames(rs.getString(3));
                user.setPhone(rs.getLong(4));
                user.setEmail(rs.getString(5));
                user.setCollegeCareer(rs.getInt(6));
                user.setAddress(rs.getString(7));
                user.setBanned(rs.getBoolean(8));
              
                datos.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }
    
    /**
     * Get all the control numbers for the autocomplete function 
     * @return a list of string with Control Numbers.
     */
    public List<String> getAllControlNumbers(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<String> datos = new ArrayList<>();
        String sql = "SELECT ControlNumber FROM users";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                datos.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }
    
    /**
     * Integer: id, String: career name
     * @return a map for join the user table
     */
    public Map<Integer, String>  getAllCollegeCareers(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        Map<Integer, String> careers = new HashMap<>();
        String sql = "SELECT * FROM collegecareers";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                careers.put(rs.getInt(1), rs.getString(2));
            }
            return careers;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * Used to delete a user from the date base
     * @param user to get the Control Number
     * @return true if the account is deleted successful
     */
    public boolean unsubscribe(User user){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "DELETE FROM users WHERE ControlNumber=?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setLong(1, user.getControlNumber());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }
    
     /**
     * Used to insert a user from the date base
     * @param user to all the data from the Object
     * @return true if the account to register is OK
     */
    public boolean subscribe(User user){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "INSERT INTO users (ControlNumber, Names, LastNames, Phone, Email, CollegeCareer, Address, Banned) VALUES (?, ?, ?, ?, ?, ?, ?, false)";
        
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setInt(1, user.getControlNumber());
            ps.setString(2, user.getNames());
            ps.setString(3, user.getLastNames());
            ps.setLong(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getCollegeCareer());
            ps.setString(7, user.getAddress());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1062: JOptionPane.showMessageDialog(null, "El numero de control ingresado ya esta en uso.");break;
                default: throw new AssertionError();
            }
        }
        return false;
    }
    
    // Actualiza la informacion del usuario
    public boolean updateUserInfo(User user){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "UPDATE users SET `Names`=?,`LastNames`=?,`Phone`=?,`Email`=?,`CollegeCareer`=?,`Address`=?,`Banned`=? WHERE `ControlNumber`=?";
        try {
            ps = con.prepareStatement(SQL);
                    
            ps.setString(1, user.getNames());
            ps.setString(2, user.getLastNames());
            ps.setLong(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getCollegeCareer());
            ps.setString(6, user.getAddress());
            ps.setBoolean(7, user.isBanned());
            
            ps.setInt(8, user.getControlNumber());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    // Banea o desbanea a los usuarios
    public boolean banUnban(User user){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "UPDATE users SET `Banned`=? WHERE `ControlNumber`=?";
        try {
            ps = con.prepareStatement(SQL);
                    
            ps.setBoolean(1, user.isBanned());
            ps.setInt(2, user.getControlNumber());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public List searchByColumn(String column, String word){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<User> datos = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE " + column + " LIKE ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + word + "%");
            
            rs = ps.executeQuery();
            
            while (rs.next()) { 
                User user = new User(0, "", "", null, "", 0, "",  false);
                
                // Se comineza desde la columna 1 no del 0.
                user.setControlNumber(rs.getInt(1));
                user.setNames(rs.getString(2));
                user.setLastNames(rs.getString(3));
                                          
                user.setPhone(rs.getLong(4));
                user.setEmail(rs.getString(5));
                user.setCollegeCareer(rs.getInt(6));
                
                user.setAddress(rs.getString(7));
                user.setBanned(rs.getBoolean(8));
                
                datos.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    } 
}
