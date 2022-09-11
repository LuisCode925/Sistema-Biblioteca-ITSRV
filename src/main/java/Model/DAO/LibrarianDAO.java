/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.ConnectionDB;
import Model.Librarian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */

// Extiende del archivo de conexion para no tener que instanciarlo extends ConnectionDB 
public class LibrarianDAO {
        
    /**
     * This function is to INSERT a object Librarian in the Data Base
     * @param lib is the object with the data from Sing UP form
     * @return true if the operation is successful
     */
    public boolean signUp(Librarian lib){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();

        String SQL = "INSERT INTO librarians (Id, Names, LastNames, NickName, Email, Password) VALUES (null, ?, ?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(SQL);
            
            // Campos de la tabla
            ps.setString(1, lib.getNames());
            ps.setString(2, lib.getLastNames());
            ps.setString(3, lib.getNickName());
            ps.setString(4, lib.getEmail());
            ps.setString(5, lib.getPassword());
            
            ps.execute();
            
            JOptionPane.showMessageDialog(null, "Nuevo administrador de la biblioteca registrado.");
            return true;
        } catch (SQLException e) {
            // System.out.println(e.getMessage() + "Codigo: "+ e.getErrorCode());
            String msg_error;
            switch (e.getErrorCode()) {
                case 1062: msg_error = "Ya existe una cuenta vinculada con el correo ingresado.";  break;
                default: throw new AssertionError();
            }
            JOptionPane.showMessageDialog(null, msg_error);
            return false;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
    }
    
    /**
     * This function is to SELECT a object Librarian in the Data Base
     * @param librarian_id is the identifier of the Administrator of the Library
     * @return all the data of corresponding to the ID.
     */
    public Librarian getInfo(int librarian_id){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;
        
        Librarian librarian = new Librarian(0, null, null,  null, null, null);
        String SQL = "SELECT * FROM librarians WHERE Id=?";
        
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, librarian_id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                librarian.setId(rs.getInt(1));
                librarian.setNames(rs.getString(2));
                librarian.setLastNames(rs.getString(3));
                librarian.setNickName(rs.getString(4));
                librarian.setEmail(rs.getString(5));
                librarian.setPassword(rs.getString(6));
            } else {
                throw  new IllegalArgumentException("No hay ningun bibliotecario con ese ID.");
            }
        } catch (SQLException sql) {
            System.out.println("Error getInfo: "+sql.getMessage());
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return librarian;
    }
    
    /**
     * This function is to UPDATE a object Librarian in the Data Base
     * @param account is the object with the data from Administrator Account
     * @return true if the operation is successful
     */
    public boolean updateInfo(Librarian account){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        
        String SQL = "UPDATE librarians SET Names=?, LastNames=?, NickName=?, "
                + "Email=?, Password=?  WHERE Id=?";
        
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setString(1, account.getNames());
            ps.setString(2, account.getLastNames());
            ps.setString(3, account.getNickName());
            ps.setString(4, account.getEmail());
            ps.setString(5, account.getPassword());
            ps.setInt(6, account.getId());
            
            ps.execute();
            return true;
        } catch (SQLException sql) {
            System.out.println("Error updateInfo: "+sql.getMessage());
            return false;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
    }
    
    /**
     * This function is to DELETE a object Librarian in the Data Base
     * @param librarian_id is the identifier of the Administrator of the Library
     * @return true if the operation is successful
     */
    public boolean deleteAccount(int librarian_id){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        
        String SQL = "DELETE FROM librarians WHERE Id=?";
        
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, librarian_id);
            ps.execute();
            
            return true;
        } catch (SQLException sql) {
            System.out.println("Error deleteAccount:"+sql.getMessage());
            return false;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
    }
    
    /**
     * With the username and the password determinate if the Admin exists
     * @param lib only is necessary the username and the encrypted password 
     * @return an object with the 
     */
    public Librarian validate(Librarian lib){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;
        
        String SQL = "SELECT * FROM librarians WHERE NickName=? AND Password=?";
        
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, lib.getNickName());
            ps.setString(2, lib.getPassword());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                lib.setId(Integer.parseInt(rs.getString("Id")));
                lib.setNames(rs.getString("Names"));
                lib.setLastNames(rs.getString("LastNames"));
                lib.setNickName(rs.getString("NickName"));
                lib.setEmail(rs.getString("Email"));
                lib.setPassword(rs.getString("Password"));
                
                return lib;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return null;
    } 
        
    /**
     * Used to have the autocomplete function on Nickname field.
     * @return a list of the nicknames of the active users 
     */
    public List<String> getAllNicknames(){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;
        
        List<String> datos = new ArrayList<>();
        String sql = "SELECT NickName FROM librarians";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) { 
                datos.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return datos;
    }
    
}
