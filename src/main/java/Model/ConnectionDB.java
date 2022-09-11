/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */

public class ConnectionDB {
   
    public static final String Archivo = SettingsProperties.FILE_SETTINGS;
    private static String Host;
    private static String Port;
    private static String DB_Name;
    private static String User;
    private static String Password;
    private static String URL;
    private static Connection con;
    
    public static Connection getConnection(){
        
        readConfig();
        URL = "jdbc:mysql://" + Host + ":" + Port+ "/" + DB_Name;    
        
        System.out.println("URL: " + URL);
        System.out.println("Usuario: " + User);
        System.out.println("Contraseña: "+Password);
        
        try {
            // load the Driver Class
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, User, Password);
            
        } catch (SQLException e) {
            System.err.println("Error getConnection: "+e.getMessage());
            System.out.println(URL); System.out.println(User); System.out.println(Password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public static void readConfig(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(Archivo)));
            
            Host = properties.getProperty("bd.ServerHost");
            Port = properties.getProperty("bd.ServerPort");
            DB_Name = properties.getProperty("bd.DataBaseName");
            User = properties.getProperty("bd.UserName");
            Password = properties.getProperty("bd.Password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            SettingsProperties.basicProperties();
        } catch(IOException e){
            e.printStackTrace();
        } 
    }
}
