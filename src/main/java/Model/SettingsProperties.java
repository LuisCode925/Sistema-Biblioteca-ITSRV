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
    private static final AppDirs appDirs = AppDirsFactory.getInstance();
    private static String ProgramPath = appDirs.getSiteConfigDir("SistemaBiblioteca", "1.0.0", "ITSRV");
    public static final String Archivo = ProgramPath.replace("\\", "\\\\")+"\\Settings.properties";
        
    public void writeProperties(Map<String, String> config){  
        // Se sustituyo los valores por un map 
        try(OutputStream salida = new FileOutputStream(Archivo)){
            Properties conf = new Properties();
            
            // Aqui se van agregando las propiedades de la base de datos
            for (Map.Entry<String, String> entry : config.entrySet()) {
                conf.setProperty(entry.getKey(), entry.getValue());
            }
            // Se almacena en el archivo de configuracion
            conf.store(salida, null);
        } catch (FileNotFoundException ex) {
            System.out.println("Error writeProperties: "+ex.getMessage()); // Cuando el archivo no se encuentra
        } catch(IOException e){
            System.err.println("Error writeProperties: " + e.getMessage()); // Cuando la escritura de un archivo fallo
        }
    }
    
    public Map<String, String> readProperties() throws IOException{
        try {
            Path path = Paths.get(ProgramPath);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Se ha creado la ruta "+ProgramPath);
            } else {
                // System.out.println("El directorio ya existe");
                InputStream lectura = new FileInputStream(Archivo);
                    
                Map<String,String> conf = new HashMap<>();
                Properties doc_conf = new Properties();
                doc_conf.load(lectura);

                // map.put("bd.UserName", User);
                for (String key: doc_conf.stringPropertyNames()) {
                    conf.put(key ,doc_conf.getProperty(key));
                }

                return conf;
            } 
        } catch (IOException e) {
            System.out.println("Error readProperties: "+e.getMessage());
        }
        return null;
    }
    
}
