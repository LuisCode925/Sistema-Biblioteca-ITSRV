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

// Extiende del archivo de conexion para no tener que instanciarlo
public class LibrarianDAO extends ConnectionDB {
    
    // Registrar
    public boolean signUp(Librarian lib){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "INSERT INTO librarians (Names, LastNames, NickName, Email, Password) VALUES (?, ?, ?, ?, ?)";
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
        }
    }
    
    // Buscar
    public Librarian validateLibrarian(Librarian lib){
        
        PreparedStatement ps = null;
        Connection con = getConnection();
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
        }
        return null;
    } 
        
    // Obtiene todos los usuarios de la Base de Datos.
    public List getAllNicknames(){
        PreparedStatement ps = null;
        Connection con = getConnection();
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
        }
        return datos;
    }
}
