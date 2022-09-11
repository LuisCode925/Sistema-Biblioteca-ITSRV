/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Controller.HomeController;
import Model.Librarian;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;


/**
 *
 * @author Luis
 */
@Disabled
public class LibrarianDAOTest {
    
    public Faker faker;
    public LibrarianDAO libDAO;
    public Librarian librarian;
    
    @BeforeEach
    public void before(){
        faker = new Faker();
        libDAO = new LibrarianDAO();
    }

    @Disabled("Si se quiere probar se deben actualizar los test que dependen del numero de librarians.")
    @Test
    @DisplayName("Registrar un administrador")
    public void signUp() {
        librarian = new Librarian(
            0, //Id
            faker.name().firstName(), // Names
            faker.name().lastName()+" "+faker.name().lastName(), // LastNames
            faker.funnyName().name(), // Nickname
            faker.dragonBall().character().replace(" ", "_")+"@gmail.com", // Email
            HomeController.getHash("Test".getBytes(), "SHA-256")); // Password
       
        assertTrue(libDAO.signUp(librarian));
    }
    
    @Test
    @DisplayName("Informacion del Administrador")
    public void getInfo(){
        assertEquals("U925", libDAO.getInfo(1).getNickName());
    }
    
    @Test
    @DisplayName("Cambiar Nombre")
    public void updateInfo(){
        librarian = new Librarian(
            1, //Id
            "Mario Luis", // Names
            "Chávez Martínez", // LastNames
            "U925", // Nickname
            "195925luis@gmail.com", // Email
            HomeController.getHash("925lol".getBytes(), "SHA-256")); // Password
        
        assertTrue(libDAO.updateInfo(librarian));
    }
    
    @Disabled("Cambiar el id por el administrador a eliminar")
    @Test
    @DisplayName("Borrar Administrador")
    public void deleteAccount(){
        assertTrue(libDAO.deleteAccount(17)); // Si falla cambiar el id y getAllNicknames()
    }
    
    @Test
    @DisplayName("Login Datos Validos")
    public void validate_WithTrueData(){
        librarian = new Librarian(
            0, "", "", "Rick O'Shea", "", 
            HomeController.getHash("Test".getBytes(), "SHA-256")); // Password
        assertEquals("Super_Saiyan_Blue_Vegeta@gmail.com", libDAO.validate(librarian).getEmail());
    }
    
    @Test
    @DisplayName("Login Datos Invalidos")
    public void validate_WithFalseData(){
        librarian = new Librarian(
            0, "", "", "Rick O'Shea", "",
            HomeController.getHash("contra".getBytes(), "SHA-256")); // Contraseña Incorrecta
       
        assertNull(libDAO.validate(librarian));
    }
    
    @Test
    @DisplayName("Autocompletado Nickname")
    public void getAllNicknames(){
        assertEquals(6, libDAO.getAllNicknames().size());
    }
}
