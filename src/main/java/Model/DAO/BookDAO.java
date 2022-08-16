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
public class BookDAO extends ConnectionDB {
    
    // Se retorna un libros para el jframe de edicion =======================
    public Book getBook(Long ISBN){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        Calendar YearPublication = new GregorianCalendar();
        Book book = new Book(0, "", "", "", 0, YearPublication, 0, 0, 0, "", "", 0, 0);
        String sql = "SELECT * FROM books WHERE ISBN=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, ISBN);
            rs = ps.executeQuery();
            
            while (rs.next()) {
             
                
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
                

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }
    
    // Mustra el numero de ejemplares que estan disponibles para prestar
    public int getBooksAvalible(Long ISBN){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;

        String sql = "SELECT BFree FROM books WHERE ISBN=?";
        int BFree=0;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, ISBN);
            rs = ps.executeQuery();
           
            while (rs.next()) {                
                BFree = rs.getInt(1);
            }
            return BFree;
        } catch (SQLException e) {
            System.out.println("Error en getBooksAvalible: "+e.getMessage());
        }
        return Integer.MIN_VALUE;
    }
    
    // Se retorna una lista de los libros para la tabla. =======================
    public List toList(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Book> datos = new ArrayList<>();
        String sql = "select * from books";
        
        try {
            ps = con.prepareStatement(sql);
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
        }
        return datos;
    }
    
    public List<String> getAllISBN(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<String> datos = new ArrayList<>();
        String sql = "SELECT ISBN FROM books";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String s = Long.toString(rs.getLong(1));
                datos.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }
    
    // Eliminar ================================================================
    public boolean delete(Long book){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "DELETE FROM books WHERE ISBN=?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setLong(1, book);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e);
            return false;
        }
    }
    
    // Registrar
    public boolean addToInventory(Book book){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "INSERT INTO books (ISBN, Title, Author, Editorial, Edition, YearPublication, Shelf, Section, Department, Category, Language, Stock, BFree) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            System.err.println(e);
            return false;
        }
    } // FIN Registrar
    
    // Modificar
    public boolean updateBibliography(Book book){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "UPDATE books SET Title=?, Author=?, Editorial=?, Edition=?, YearPublication=?,"
                + " Shelf=?, Section=?, Department=?, Category=?, Language=?, Stock=?, BFree=? WHERE ISBN=?";
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
        }
    } // FIN Modificar */
    
    // Buscar ==================================================================
    public List searchByColumn(String column, String word){
        PreparedStatement ps = null;
        Connection con = getConnection();
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
                
                //YearPublication.set(Calendar.YEAR, rs.getDate(6).getYear());
                //book.setYearPublication(YearPublication);
                
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
        }
        return datos;
    }
}
