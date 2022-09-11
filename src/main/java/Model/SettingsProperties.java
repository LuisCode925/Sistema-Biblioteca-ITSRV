/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

/**
 *
 * @author Luis
 */
public class SettingsProperties {
    
    public static final AppDirs APP_DIRS = AppDirsFactory.getInstance();
    public static final String PROGRAM_PATH = APP_DIRS.getSiteConfigDir("SistemaBiblioteca", "1.0.0", "ITSRV");
    public static final String FILE_SETTINGS = PROGRAM_PATH.replace("\\", "\\\\")+"\\\\Settings.properties";

    // TODO Crear un metodo estatico que cree la configuracion que se va ha leer
    public static void basicProperties(){
        try {
            Path path = Paths.get(PROGRAM_PATH);
            File directorio = new File(PROGRAM_PATH);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    Map<String, String> defaultSettings = new HashMap<>();
                    defaultSettings.put("bd.UserName", "test_library");
                    defaultSettings.put("bd.ServerHost", "localhost");
                    defaultSettings.put("bd.Password", "books1234");
                    defaultSettings.put("bd.DataBaseName", "library");
                    defaultSettings.put("bd.ServerPort", "3306");
                    writeProperties(defaultSettings);
                } else {
                    System.out.println("Error al crear directorio");
                }
            } else {
                System.out.println("El directorio ya existe");
                File archivo = new File(FILE_SETTINGS);
                if (!archivo.exists()) {
                    System.out.println("¡¡No existe el archivo de configuración!!");
                    Map<String, String> defaultSettings = new HashMap<>();
                    defaultSettings.put("bd.UserName", "test_library");
                    defaultSettings.put("bd.ServerHost", "localhost");
                    defaultSettings.put("bd.Password", "books1234");
                    defaultSettings.put("bd.DataBaseName", "library");
                    defaultSettings.put("bd.ServerPort", "3306");
                    writeProperties(defaultSettings);
                }
                
            }
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
            
            /*if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Se ha creado la ruta "+PROGRAM_PATH);
            } else {
                if (Files.isRegularFile(Paths.get(FILE_SETTINGS))) {
                    System.out.println("Existe " + FILE_SETTINGS);
                } else {
            
                }
        }
        } catch (IOException e) {
            System.out.println("Error al crear las properties: "+ e.getMessage());
        }*/
    }

    public static void writeProperties(Map<String, String> config) {
        try{
            OutputStream propertiesFile = new FileOutputStream(FILE_SETTINGS);
            Properties settings = new Properties();
            
            // Aqui se van agregando las propiedades de la base de datos
            for (Map.Entry<String, String> config_item : config.entrySet()) {
                settings.setProperty(config_item.getKey(), config_item.getValue());
            }
            
            // Se almacena en el archivo de configuracion
            settings.store(propertiesFile, null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(); 
            SettingsProperties.basicProperties();
            System.out.println("Error readProperties: " + ex.getMessage()); // Cuando el archivo no se encuentra
        } catch(IOException e){
            e.printStackTrace(); System.out.println("Error readProperties: " + e.getMessage());// Cuando la escritura de un archivo fallo
        }
    }
    
    public Map<String, String> readProperties() {
        try { 
            InputStream properties_file = new FileInputStream(FILE_SETTINGS);
            Map<String,String> settings = new HashMap<>();
        
            Properties read_conf = new Properties();
            read_conf.load(properties_file);

            for (String key: read_conf.stringPropertyNames()){
                settings.put(key ,read_conf.getProperty(key));
            } 
            return settings;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            basicProperties();
            System.out.println("Error readProperties: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace(); System.out.println("Error readProperties: " + e.getMessage());
        }
        return null;
    }
    
}
