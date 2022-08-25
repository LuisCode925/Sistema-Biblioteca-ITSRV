/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.Book;
import com.github.javafaker.Faker;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.GregorianCalendar;
import static org.junit.Assert.*;


/**
 *
 * @author Luis
 */
public class BookDAOTest {
    
    public BookDAO bookDAO;
    public Book book;
    public Faker faker;

    @BeforeClass
    public static void alInicio(){
        System.out.println("Inicio del test.");
    }

    @Before
    public void before(){
        bookDAO = new BookDAO();
        faker = new Faker();
    }
    
    @Test
    public void getInfo_WithValid_ISBN(){
        book = bookDAO.getInfo(9701704053L);
        assertEquals("Auditoría en sistemas computacionales", book.getTitle());
    }
    
    @Test(expected = NullPointerException.class)
    public void getInfo_WithInvalid_ISBN(){
        bookDAO.getInfo(1027836474263L);
    }

    @Test
    public void getBooksAvalible_WithValid_ISBN(){
        assertEquals(4, bookDAO.getBooksAvalible(9789702611905L));
    }

    @Test(expected = NullPointerException.class)
    public void getBooksAvalible_WithInvalid_ISBN(){
        bookDAO.getBooksAvalible(1027836474263L);
    }

    @Test
    public void getLibrary(){
        assertEquals(3, bookDAO.getLibrary().size());
    }

    /*@Test
    public void getLibrary_butReturnNull(){
        TODO Hacer una prueba pero sin elementos
        assertEquals(3, bookDAO.getLibrary().size());
    }*/

    @Test
    public void getAllISBN(){
        assertEquals(3, bookDAO.getAllISBN().size());
    }

    // TODO un test donde no devuelvan ISBN's

    @Test
    public void addToInventory(){
        book = new Book(9780764574818L,
                "Reversing: Secrets of Reverse Engineering",
                "Eldad Eilam", "Wiley Publishing, Inc.",1,
                new GregorianCalendar(2005,0,1),
                1,1,1,"Reversing", "Ingles",
                12,6);
        assertTrue(bookDAO.addToInventory(book));
    }

    @Test
    public void updateBibliography(){
        book = new Book(9780764574818L,
                "Reversing: Secrets of Reverse Engineering",
                "Eldad Eilam", "Wiley Publishing, Inc.",1,
                new GregorianCalendar(2019,0,1),
                // INFO si se pone el dia en 0 el anño disminuye
                1,1,1,"Reversing", "Ingles",
                12,6);
        assertTrue(bookDAO.updateBibliography(book));
    }

    @Test
    public void delete_WithValidISBN(){
        assertTrue(bookDAO.delete(9780764574818L));
    }

    /*@Test(expected = SQLException.class) Arriba
    public void delete_WithInvalidISBN() throws SQLException {
        assertTrue(bookDAO.delete(9780764574818L));
    }*/

    @Test
    public void searchByColumn_WithValidSearch(){
        assertEquals(1, bookDAO.searchByColumn("title", "java").size());
    }

    @Test
    public void searchByColumn_WithInvalidSearch(){
        assertEquals(0, bookDAO.searchByColumn("title", "zshyiuzhdiuahsiudh").size());
    }

    @AfterClass
    public static void ejecutarAlFinalizar(){
        System.out.println("Fin del test.");
    }
}
