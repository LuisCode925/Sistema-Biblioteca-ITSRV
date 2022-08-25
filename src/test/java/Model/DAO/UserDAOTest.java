/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;
import Model.User;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luis
 */
public class UserDAOTest {
    
    public UserDAO userDAO;
    public Faker faker;
    public User user;
    
    
    @Before
    public void before(){
        userDAO = new UserDAO();
        faker = new Faker();
    }
    
    @Test
    public void getUserWithTrueParam(){
        assertEquals("195925luis@gmail.com", userDAO.getUser(18220002).getEmail());
    }
    
    @Test
    public void getUserWithFalseParam(){
        assertNull(userDAO.getUser(0));
    }
    
    @Test
    public void isBannedWithAValidControlNumber(){
        assertFalse(userDAO.isBanned(18220002));
    }
    
    @Test
    public void isBannedWithInvalidControlNumber(){
        IllegalArgumentException thrown = assertThrows( IllegalArgumentException.class, () -> {
            userDAO.isBanned(87564856); //Code under test
        });
        assertEquals("No Existe el Numero de Control.", thrown.getMessage()); 
    }
    
    @Test
    public void getAllUsers(){
        assertEquals(2, userDAO.getAllUsers().size());
    }
    
    @Test
    public void getAllControlNumbers(){
        assertEquals(2, userDAO.getAllControlNumbers().size());
    }
    
    @Test
    public void getAllCollegeCareers(){
        assertEquals(5, userDAO.getAllCollegeCareers().size());
    }
    
    @Test
    public void unsubscribe(){
        // TODO
    }
    
    /*@Test
    public void subscribe(){
        user = new User(
                0, 
                faker.name().firstName(), 
                faker.name().lastName()+ " " + faker.name().lastName(), 
                faker.phoneNumber().cellPhone(), 
                Email, 
                0, 
                Address, 
                true);
    }*/


// awiuejowje     
}
