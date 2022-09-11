/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.Book;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;


/**
 *
 * @author Luis
 */
@Disabled
public class BookDAOTest {
    
    public BookDAO bookDAO;
    public Book book;
    public Faker faker;

    @BeforeEach
    public void before(){
        bookDAO = new BookDAO();
        faker = new Faker();
    }
    
    @Test
    public void getInfo_WithValidISBN(){
        book = bookDAO.getInfo(9701704053L);
        assertEquals("Auditoría en sistemas computacionales", book.getTitle());
    }
    
    @Test
    public void getInfo_WithInvalidISBN(){
        IllegalArgumentException thrown = assertThrows( IllegalArgumentException.class, () -> {
            bookDAO.getInfo(1027836474263L); //Code under test
        });
        assertEquals("No se encontro el libros con el ISBN espesificado.", thrown.getMessage());
    }

    @Test
    public void getBooksAvalible_WithValidISBN(){
        assertEquals(4, bookDAO.getBooksAvalible(9789702611905L));
    }

    @Test
    public void getBooksAvalible_WithInvalidISBN(){
        IllegalArgumentException thrown = assertThrows( IllegalArgumentException.class, () -> {
            bookDAO.getBooksAvalible(1027836474263L); //Code under test
        });
        assertEquals("No se han encontrado resultados.", thrown.getMessage());
    }

    @Test
    public void getLibrary(){
        assertEquals(3, bookDAO.getLibrary().size());
    }

    @Test
    public void getAllISBN(){
        assertEquals(3, bookDAO.getAllISBN().size());
    }
    
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

    @Test
    public void searchByColumn_WithValidSearch(){
        assertEquals(1, bookDAO.searchByColumn("title", "java").size());
    }

    @Test
    public void searchByColumn_WithInvalidSearch(){
        assertEquals(0, bookDAO.searchByColumn("title", "zshyiuzhdiuahsiudh").size());
    }

}
