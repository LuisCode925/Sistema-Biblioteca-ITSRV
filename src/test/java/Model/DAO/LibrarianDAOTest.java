/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Controller.HomeController;
import Model.Librarian;
import com.github.javafaker.Faker;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Luis
 */
public class LibrarianDAOTest {
    
    public Faker faker;
    public LibrarianDAO libDAO;
    public Librarian librarian;
    
    @Before
    public void before(){
        faker = new Faker();
        libDAO = new LibrarianDAO();
    }

    @Test
    public void signUpOne() {
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
    public void validateWithTrueData(){
        librarian = new Librarian(
            0, "", "", "Rick O'Shea", "", 
            HomeController.getHash("Test".getBytes(), "SHA-256")); // Password
        assertEquals("Super_Saiyan_Blue_Vegeta@gmail.com", libDAO.validate(librarian).getEmail());
    }
    
    @Test
    public void validateWithFalseData(){
        librarian = new Librarian(
            0, "", "", "Rick O'Shea", "",
            HomeController.getHash("contra".getBytes(), "SHA-256")); // Contraseña Incorrecta
       
        assertNull(libDAO.validate(librarian));
    }
    
    @Test
    public void getAllNicknames(){
        assertEquals(4, libDAO.getAllNicknames().size());
    }
}
