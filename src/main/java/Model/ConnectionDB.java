/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */

public class ConnectionDB {
    public static final String Archivo = "C:\\Users\\Luis\\DataBase.properties";
    private String Host;
    private String Port;
    private String DB_Name;
    private String User;
    private String Password;
    private String URL;
    private Connection con;

    public ConnectionDB() {
        this.ReadConfig();
        this.URL = "jdbc:mysql://" + this.Host + ":" + this.Port+ "/" + this.DB_Name;
    }
    
    public Connection getConnection(){
            
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(this.URL, this.User, this.Password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            /*System.out.println(URL); System.out.println(User); System.out.println(Password);*/
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void ReadConfig(){
        try (InputStream lectura = new FileInputStream(Archivo)){
            Properties propiedades = new Properties();
            
            propiedades.load(lectura);
           
            this.Host = propiedades.getProperty("bd.ServerHost");
            this.Port = propiedades.getProperty("bd.ServerPort");
            this.DB_Name = propiedades.getProperty("bd.DataBaseName");
            this.User = propiedades.getProperty("bd.UserName");
            this.Password = propiedades.getProperty("bd.Password");
            
        } catch(IOException e){
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
    }
}
