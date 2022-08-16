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
    
    // Obtiene solo un usuario de la Base de Datos.
    public User getUser(int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        User user = new User(0, "", "", null, "", 0, "", false);
        String sql = "SELECT * FROM users WHERE ControlNumber=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            rs = ps.executeQuery();
            
            if(rs.next()) {                
                // Se comineza desde la columna 1 no del 0.
                user.setControlNumber(rs.getInt(1));
                user.setNames(rs.getString(2));
                user.setLastNames(rs.getString(3));
                user.setPhone(rs.getLong(4));
                user.setEmail(rs.getString(5));
                user.setCollegeCareer(rs.getInt(6));
                user.setAddress(rs.getString(7));
                user.setBanned(rs.getBoolean(8));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }
    
      public boolean isBanned(int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        String sql = "SELECT Banned FROM users WHERE ControlNumber=?";
        boolean Banned = false;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            rs = ps.executeQuery();
            
            if(rs.next()) {                
                // Se comineza desde la columna 1 no del 0.
                Banned = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Banned;
    }
        
    // Obtiene todos los usuarios de la Base de Datos.
    public List getAllUsers(){
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
    
    // Obtiene solo el campo del numero de control de la Base de Datos.
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
        } catch (SQLException e) {
            System.out.println(e);
        }
        return careers;
    }
    
    // Elimina un usuario de la base de datos
    public boolean deleteUser(User user){
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
            return false;
        }
    }
    
    // Registra un usuario en la base de datos
    public boolean registerUser(User user){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "INSERT INTO users (`ControlNumber`, `Names`, `LastNames`, `Phone`, `Email`, `CollegeCareer`, `Address`, `Banned`) VALUES (?, ?, ?, ?, ?, ?, ?, false)";
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
                default:
                    throw new AssertionError();
            }
            return false;
        }
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
