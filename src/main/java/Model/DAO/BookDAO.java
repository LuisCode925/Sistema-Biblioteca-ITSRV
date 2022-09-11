/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.Book;
import Model.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Luis
 */
public class BookDAO {
    //TODO delete, addToInventory, updateBibliography necesitan manejar excepciones

    /**
     * Get the infomation from the book with the
     * @param ISBN (unique id of each book)
     * @return the seached data in a Book Object or throw.
     */
    public Book getInfo(Long ISBN){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;


        Calendar YearPublication = new GregorianCalendar();
        Book book = new Book(0, "", "", "", 0, YearPublication, 0, 0, 0, "", "", 0, 0);
        String sql = "SELECT * FROM books WHERE ISBN=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, ISBN);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                
                // Se comineza desde la columna 1 no del 0.
                book.setISBN(rs.getLong(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                
                book.setEditorial(rs.getString(4));
                book.setEdition(rs.getInt(5));
                
                YearPublication.setTime(rs.getDate(6));
                book.setYearPublication(YearPublication);
                
                book.setShelf(rs.getInt(7));
                book.setSection(rs.getInt(8));
                book.setDepartment(rs.getInt(9));
                
                book.setCategory(rs.getString(10));
                book.setLanguage(rs.getString(11));
                book.setStock(rs.getInt(12));
                book.setBFree(rs.getInt(13));
            } else {
                throw new IllegalArgumentException("No se encontro el libros con el ISBN espesificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error getInfo: "+e.getMessage());
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return book;
    }

    /**
     * Query the database using the 
     * @param ISBN (unique id of each book) to
     * @return the number of books avalible for loans.
     */
    public int getBooksAvalible(Long ISBN){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;

        String sql = "SELECT BFree FROM books WHERE ISBN=?";
        int BFree=0;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, ISBN);
            rs = ps.executeQuery();
           
            if (rs.next()) {
                BFree = rs.getInt(1);
            } else {
                throw new IllegalArgumentException("No se han encontrado resultados.");
            }
        } catch (SQLException e) {
            System.out.println("Error en getBooksAvalible: "+e.getMessage());
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return BFree;
    }
    
    /**
     * None parameters simply
     * @return all the books registered
     */
    public List<Book> getLibrary(){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;
        
        List<Book> datos = new ArrayList<>();
        String sql = "select * from books";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if(rs == null) return null;
            while (rs.next()) {
                Calendar YearPublication = new GregorianCalendar();
                Book book = new Book(0, "", "", "", 0, YearPublication, 0, 0, 0, "", "", 0, 0);
                
                // Se comineza desde la columna 1 no del 0.
                book.setISBN(rs.getLong(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                
                book.setEditorial(rs.getString(4));
                book.setEdition(rs.getInt(5));
                
                YearPublication.setTime(rs.getDate(6));
                book.setYearPublication(YearPublication);
                
                book.setShelf(rs.getInt(7));
                book.setSection(rs.getInt(8));
                book.setDepartment(rs.getInt(9));
                
                book.setCategory(rs.getString(10));
                book.setLanguage(rs.getString(11));
                book.setStock(rs.getInt(12));
                book.setBFree(rs.getInt(13));
                
                datos.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return datos;
    }

    /**
     * Used in the autocomplete function in JTextField
     * @return a list with all the xontrol numbers form the users
     */
    public List<String> getAllISBN(){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;
        
        List<String> datos = new ArrayList<>();
        String sql = "SELECT ISBN FROM books";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if(rs == null) return null;
            while (rs.next()) {
                String s = Long.toString(rs.getLong(1));
                datos.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return datos;
    }

    // TODO Falta informacion y checar el borrado de las llaves foraneas
    // TODO Refactorizar para manejar correctamente las excepciones
    
    /**
     * Used to delete a registry in the Data Base
     * @param ISBN (unique id of each book)
     * @return true if the operation is successful.
     */
    public boolean delete(Long ISBN){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();

        String SQL = "DELETE FROM books WHERE ISBN=?";

        try {
            ps = con.prepareStatement(SQL);
            ps.setLong(1, ISBN);
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error delete: " + e);
            return false;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
    }
    
    /**
     * Used to insert a registry in the Data Base
     * @param book Object contains all data 
     * @return true if the operation is successful
     */
    public boolean addToInventory(Book book){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();

        String SQL = "INSERT INTO books (ISBN, Title, Author, Editorial, Edition, "
                + "YearPublication, Shelf, Section, Department, Category, Language, "
                + "Stock, BFree) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setLong(1, book.getISBN());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getEditorial());
            ps.setInt(5, book.getEdition());
            ps.setInt(6, book.getYearPublication().get(Calendar.YEAR));
            ps.setInt(7, book.getShelf());
            ps.setInt(8, book.getSection());
            ps.setInt(9, book.getDepartment());
            ps.setString(10, book.getCategory());
            ps.setString(11, book.getLanguage());
            ps.setInt(12, book.getStock());
            ps.setInt(13, book.getBFree());

            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
    }
    
    /**
     * Used to update a registry in the Data Base
     * @param book Object contains all data 
     * @return true if the operation is successful.
     */
    public boolean updateBibliography(Book book){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        String SQL = "UPDATE books SET Title=?, Author=?, Editorial=?, Edition=?, "
                + "YearPublication=?, Shelf=?, Section=?, Department=?, Category=?, "
                + "Language=?, Stock=?, BFree=? WHERE ISBN=?";
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getEditorial());
            ps.setInt(4, book.getEdition());
            ps.setInt(5, book.getYearPublication().get(Calendar.YEAR));
            ps.setInt(6, book.getShelf());
            ps.setInt(7, book.getSection());
            ps.setInt(8, book.getDepartment());
            ps.setString(9, book.getCategory());
            ps.setString(10, book.getLanguage());
            ps.setInt(11, book.getStock());
            ps.setInt(12, book.getBFree());
            ps.setLong(13, book.getISBN());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try { con.close();} catch (Exception e) {}
        }
    }
    
    /**
     * Used to seach results more specific
     * @param column definen the column where the search will be done
     * @param word is the phrase to search
     * @return the closer results if not null.
     */
    public List<Book> searchByColumn(String column, String word){
        PreparedStatement ps = null;
        Connection con = ConnectionDB.getConnection();
        ResultSet rs = null;
        
        List<Book> datos = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE " + column + " LIKE ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + word + "%");
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Calendar YearPublication = new GregorianCalendar();
                Book book = new Book(0, "", "", "", 0, YearPublication, 0, 0, 0, "", "", 0, 0);
                
                // Se comineza desde la columna 1 no del 0.
                book.setISBN(rs.getLong(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
               
                book.setEditorial(rs.getString(4));
                book.setEdition(rs.getInt(5));
                
                YearPublication.setTime(rs.getDate(6));
                book.setYearPublication(YearPublication);

                book.setShelf(rs.getInt(7));
                book.setSection(rs.getInt(8));
                book.setDepartment(rs.getInt(9));
                
                book.setCategory(rs.getString(10));
                book.setLanguage(rs.getString(11));
                book.setStock(rs.getInt(12));
                book.setBFree(rs.getInt(13));
                
                datos.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try { con.close();} catch (Exception e) {}
        }
        return datos;
    }
    
}
