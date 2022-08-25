/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.SettingsProperties;
import Model.Librarian;
import Model.DAO.LibrarianDAO;
import View.HomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;
import System.Main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis
 */
public class HomeController implements ActionListener {
    
    private Librarian lib;
    private LibrarianDAO libDAO;
    private HomeView homeView;
    
    public static List<String> nicknames_list;

    public HomeController(Librarian lib, LibrarianDAO libDAO, HomeView homeView) {
        this.lib = lib;
        this.libDAO = libDAO;
        this.homeView = homeView;
        
        // Acciones para los elementos de la vista
        this.homeView.btnLogin.addActionListener(this);
        this.homeView.btnSignUp.addActionListener(this);
        this.homeView.btnLogout.addActionListener(this);
        
        this.homeView.txtSignUpNickName.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) { }
            @Override public void keyPressed(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {verifyNickname();}
            public void verifyNickname(){
                //System.out.println(homeView.txtSignUpNickName.getText());
                if(nicknames_list.contains(homeView.txtSignUpNickName.getText())) {
                    homeView.lblSignUpNickName.setText("Nombre de Usuario: NO Disponible");
                    homeView.txtSignUpNickName.setForeground(Color.RED);
                    homeView.btnSignUp.setEnabled(false);
                } else {
                    homeView.lblSignUpNickName.setText("Nombre de Usuario: Disponible");
                    homeView.txtSignUpNickName.setForeground(Color.GREEN);
                    homeView.btnSignUp.setEnabled(true);
                }
            }
        });
            
        // Obtencion de la sesion guardada
        logBySession();
        nicknames_list = libDAO.getAllNicknames();
    }
    
    public void logBySession(){
        try {
            SettingsProperties properties = new SettingsProperties();
            Map<String,String> conf = properties.readProperties();
            String nickname = "", passwd = "";

            if (conf.containsKey("admin.user") && conf.containsKey("admin.pass")) {
                nickname = conf.get("admin.user");
                passwd = conf.get("admin.pass");
            }

            // Se obtiene el usuario y la contraseña del formulario
            lib.setNickName(nickname);
                lib.setPassword(passwd);

            // El Administrador se encuentra en la base de datos
            if (libDAO.validate(lib)!= null && !nickname.equals("") && !passwd.equals("")) {

                // Se establece que el usuario logueado como administrador
                Main.Administrator = libDAO.validate(lib);

                // Se muestran las etiquetas para mustrar el usuario logueado
                homeView.lblWelcome.setVisible(true);
                homeView.lblLoggedUser.setVisible(true);
                homeView.btnLogout.setVisible(true);

                // Se establece la etiqueta con el nombre completo del administrador
                homeView.lblLoggedUser.setText(Main.Administrator.getNames() + " " + Main.Administrator.getLastNames());

                cleanLogin();
                disableContainer(homeView.LoginPanel, false);
            } 
        } catch (IOException e) {
            System.out.println("Error logBySession: "+e.getMessage());
        }
        
    }
    
    public static String getHash(byte[] input, String algorithm){
        String hashValue = null;
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(input);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Hubo un error: "+e.getMessage());
        }
        
        return hashValue;
    }
    
    public void addLibrarian() {
        
        // Validacion de los datos del formulario 
        if (
                homeView.txtSignUpNames.getText().length() > 0 && 
                homeView.txtSignUpLastNames.getText().length() > 0 &&
                homeView.txtSignUpNickName.getText().length() >= 4 && 
                homeView.txtSignUpEmail.getText().length() > 0 &&
                homeView.txtSignUpPassword.getPassword().length >= 6 && 
                homeView.txtSignUpRepass.getPassword().length >= 6) {
            
            // Obtencion de los datos del fortmulario
            lib.setNames(homeView.txtSignUpNames.getText());
            lib.setLastNames(homeView.txtSignUpLastNames.getText());
            lib.setNickName(homeView.txtSignUpNickName.getText());
            lib.setEmail(homeView.txtSignUpEmail.getText());

            if (String.valueOf(homeView.txtSignUpPassword.getPassword()).equals(String.valueOf(homeView.txtSignUpRepass.getPassword())) ) {
                
                // Encriptamos la contraseña para poder insertarla en la base de datos
                String pass = String.valueOf(homeView.txtSignUpPassword.getPassword());
                lib.setPassword(getHash(pass.getBytes(), "SHA-256"));
                
                // El proceso para registrar al administrador de la libreria
                if(libDAO.signUp(lib)){ 
                    JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
                    nicknames_list = libDAO.getAllNicknames();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar al usuario.");
                }
        
                // Limpiamos el formulario de registro
                cleanSignUp();
            } else {
                JOptionPane.showMessageDialog(null, "Ambas Contraseñas deben coincidir");
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Todos los datos del formulario son necesarios.");
        }
    }
    
    public void logLibrarian(){
        
        // Se obtiene el usuario y la contraseña del formulario
        lib.setNickName(homeView.txtLoginNickName.getText());
        String pass = String.valueOf(homeView.txtLoginPassword.getPassword());
        if(pass != null) lib.setPassword(getHash(pass.getBytes(), "SHA-256"));
        
        // El Administrador se encuentra en la base de datos
        if (libDAO.validate(lib)!= null && homeView.txtLoginNickName.getText() != null && pass != null) {
            
            // Se establece que el usuario logueado como administrador
            Main.Administrator = libDAO.validate(lib);
            
            // Se muestran las etiquetas para mustrar el usuario logueado
            homeView.lblWelcome.setVisible(true);
            homeView.lblLoggedUser.setVisible(true);
            homeView.btnLogout.setVisible(true);
            
            // Se establece la etiqueta con el nombre completo del administrador
            homeView.lblLoggedUser.setText(Main.Administrator.getNames() + " " + Main.Administrator.getLastNames());
            
            cleanLogin();
            disableContainer(homeView.LoginPanel, false);
            
            // Mensaje de configmacion al usuario
            JOptionPane.showMessageDialog(null, "Usted ha ingresado como " + Main.Administrator.getNickName());
        } else{
            // No se encontraron resultados de la busqueda
            JOptionPane.showMessageDialog(null, "El usuario o contraseña ingresados son incorrectos");
        }
    }
    
    public void cleanSignUp(){
        homeView.txtSignUpNames.setText(null);
        homeView.txtSignUpLastNames.setText(null);
        homeView.txtSignUpNickName.setText(null);
        homeView.txtSignUpEmail.setText(null);
        homeView.txtSignUpPassword.setText(null);
        homeView.txtSignUpRepass.setText(null);
    }
    
    public void cleanLogin(){
        homeView.txtLoginNickName.setText(null);
        homeView.txtLoginPassword.setText(null);
    }
    
    public static void disableContainer(Container c, boolean band) {
        Component [] components = c.getComponents();
        c.setEnabled(band);
        for(int i = 0; i < components.length; i++) {
            components[i].setEnabled(band);
            if(components[i] instanceof Container) disableContainer((Container)components[i], band);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
                
        // Registrar un nuevo administrador de la libreria
        if (e.getSource() == homeView.btnSignUp) { addLibrarian(); }
        
        // Login del los usuarios registrados
        if (e.getSource() == homeView.btnLogin) {
            logLibrarian();
            if (homeView.btnRememberMe.isSelected()) {
                try {
                    SettingsProperties properties = new SettingsProperties();
                    Map<String,String> conf = properties.readProperties();
                    conf.put("admin.user", Main.Administrator.getNickName());
                    conf.put("admin.pass", Main.Administrator.getPassword());
                    properties.writeProperties(conf);
                } catch (IOException ioe) {
                    System.out.println("Error homeView.btnLogin Action: "+ioe.getMessage());
                }
                
            } else {
                try {
                    SettingsProperties properties = new SettingsProperties();
                    Map<String,String> conf = properties.readProperties();
                    conf.remove("admin.user");
                    conf.remove("admin.pass");
                    properties.writeProperties(conf);
                } catch (IOException ioe) {
                    System.out.println("Error homeView.btnLogin Action: "+ioe.getMessage());
                }
            
            }
        }
        
        // Logout del administrador actual
        if (e.getSource() == homeView.btnLogout) {
          
            disableContainer(homeView.LoginPanel, true);
            
            homeView.lblWelcome.setVisible(false);
            homeView.lblLoggedUser.setVisible(false);
            homeView.btnLogout.setVisible(false);
            
            Main.Administrator = null;
            try {
                SettingsProperties properties = new SettingsProperties();
                Map<String,String> conf = properties.readProperties();
                conf.remove("admin.user");
                conf.remove("admin.pass");
                properties.writeProperties(conf);
            } catch (IOException ioe) {
                System.out.println("Error homeView.btnLogout: "+ioe.getMessage());
            }
       
        }
    }
    
}
