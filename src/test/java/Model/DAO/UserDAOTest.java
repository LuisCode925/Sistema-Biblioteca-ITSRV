/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.User;
import com.github.javafaker.Faker;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {
    
    public UserDAO userDAO;
    public Faker faker;
    public User user;
    
    
    @BeforeEach
    public void before(){
        userDAO = new UserDAO();
        faker = new Faker();
    }

    @Test
    @Order(1)
    public void getUser_WithTrueParam(){
        assertEquals("195925luis@gmail.com", userDAO.getUser(18220002).getEmail());
    }
    
    @Test
    @Order(2)
    public void getUser_WithFalseParam(){
        assertNull(userDAO.getUser(0));
    }
    
    @Test
    @Order(3)
    public void isBanned_WithAValidControlNumber(){
        assertFalse(userDAO.isBanned(18220002));
    }
    
    @Test
    @Order(4)
    public void isBanned_WithInvalidControlNumber(){
        IllegalArgumentException thrown = assertThrows( IllegalArgumentException.class, () -> {
            userDAO.isBanned(87564856); //Code under test
        });
        assertEquals("No Existe el Numero de Control.", thrown.getMessage()); 
    }
    
    @Test
    @Order(5)
    public void getAllUsers(){
        assertEquals(2, userDAO.getAllUsers().size());
    }
    
    @Test
    @Order(6)
    public void getAllControlNumbers(){
        assertEquals(2, userDAO.getAllControlNumbers().size());
    }
    
    @Test
    @Order(7)
    public void getAllCollegeCareers(){
        assertEquals(5, userDAO.getAllCollegeCareers().size());
    }
            
    @Test
    @Order(8)
    public void updateInfo(){
        user = new User(18220003, "Luis Enrique", "Herrera Herrera", 
                4871180899L, "test@maildrop.cc", 2, "Palomas", false);
        assertTrue(userDAO.updateInfo(user));
    }
    
    @Test
    @Order(9)
    public void unsubscribe(){
        user = new User(18220003, "Names", "LastNames",Long.MIN_VALUE, "Email", 2, "Address", false);
        assertTrue(userDAO.unsubscribe(user));
    }
    
    @Test
    @Order(10)
    public void subscribe() throws SQLException {
        // user = new User(18220003, "Luis Enrique", "Herrera Herrera", 4871180899L, "test@maildrop.cc", 2, "Benito Juarez #5, San Vicente Rioverde", false);
        user = new User(18220007, "Abraham", "Verde Rubio", 4871287707L, "bola_de_nieve@maildrop.cc", 2, "", false);
        assertTrue(userDAO.subscribe(user));
    }
    
    @Test
    @Order(11)
    public void searchByColumn(){
        assertEquals(1, userDAO.searchByColumn("Email", "maildrop.cc").size());
    }
    
    @Test
    @Order(12)
    public void updateInfo_toInit(){
        user = new User(18220003, "Luis Enrique", "Herrera Herrera", 
                4871180899L, "test@maildrop.cc", 2, "Benito Juarez #5, San Vicente Rioverde", false);
        assertTrue(userDAO.updateInfo(user));
    }

    @Test
    @Order(13)
    public void banUnban(){
        assertTrue(userDAO.banUnban(18220002, true));
    }
    
    @Test
    @Order(14)
    public void banUnban_check(){
        assertTrue(userDAO.isBanned(18220002));
    }
    
    @Test
    @Order(15)
    public void banUnban_toAfter(){
        assertTrue(userDAO.banUnban(18220002, false));
    }
    
}
