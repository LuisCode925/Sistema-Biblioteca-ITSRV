/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConnectionDB;
import Model.SettingsProperties;
import View.SettingView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class SettingController implements ActionListener{
    
    SettingView settings;
    SettingsProperties properties;
    ConnectionDB con;

    public SettingController(SettingView settings, SettingsProperties properties, ConnectionDB con) {
        this.settings = settings;
        this.properties = properties;
        this.con = con;
        
        // Agregamos un Listener para escuchar los eventos del boton 
        this.settings.btnTestCon.addActionListener(this);
        this.settings.btnSaveSettings.addActionListener(this);
        this.settings.btnCancel.addActionListener(this);
        this.settings.cboxShowPass.addActionListener(this);
               
        // Acciones que deben ocurrir por defecto
        this.loadSettings();
    }
    
    public void loadSettings(){
        try {
            Map<String,String> config = this.properties.readProperties();
            this.settings.txtServerHost.setText(config.get("bd.ServerHost"));
            this.settings.txtServerPort.setText(config.get("bd.ServerPort"));
            this.settings.txtDataBaseName.setText(config.get("bd.DataBaseName"));
            this.settings.txtUserName.setText(config.get("bd.UserName"));
            this.settings.txtPassword.setText(config.get("bd.Password"));
        } catch (Exception e) {
            System.out.println("Error loadSettings: "+e.getMessage());
        }
    }
    
    public Map<String, String> getFormSettings(){
        Map<String, String> formSettings = new HashMap<>();
        
        String Host = this.settings.txtServerHost.getText();
        String Port = this.settings.txtServerPort.getText();
        String DB_Name= this.settings.txtDataBaseName.getText();
        String User = this.settings.txtUserName.getText();
        String Password = String.valueOf(this.settings.txtPassword.getPassword());

        if (Host.length() > 0 && Port.length() > 0 && DB_Name.length() > 0 && User.length() > 0 && Password.length() > 0) {
            formSettings.put("bd.UserName", User);
            formSettings.put("bd.Password", Password);
            formSettings.put("bd.ServerHost", Host);
            formSettings.put("bd.ServerPort", Port);
            formSettings.put("bd.DataBaseName", DB_Name);  
        }
        
        return formSettings;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()== settings.btnTestCon) {
            if(this.con.getConnection() != null){ 
                JOptionPane.showMessageDialog(null, "Conexión correcta a la base de datos.");
            } else { 
                JOptionPane.showMessageDialog(null, "Conexión erronea a la base de datos."); 
            }             
        }
        
        if (e.getSource()== settings.btnSaveSettings) { 
            this.properties.writeProperties(this.getFormSettings());
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
        }
        
        if (e.getSource()== settings.btnCancel) {
            this.loadSettings();
            JOptionPane.showMessageDialog(null, "Operación cancelada, se han cargado los datos del archivo de configuración.");
        }
        
        if (e.getSource()== settings.cboxShowPass) {
            if(settings.cboxShowPass.isSelected()){
                settings.txtPassword.setEchoChar((char)0);
            } else {
                settings.txtPassword.setEchoChar('\u25cf');
            }
        }
    }
    
    
    
}
