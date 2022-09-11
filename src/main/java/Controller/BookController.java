/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Book;
import Model.DAO.BookDAO;
import Model.ComboItems;
import View.AddEditBook;
import View.BookView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis
 */
public class BookController implements ActionListener{
    
    private Book book;
    private BookDAO bookDAO;
    private BookView bookView;
    private AddEditBook addEditBook;
    DefaultTableModel modelo = new DefaultTableModel();
   
    public BookController(Book book, BookDAO bookDAO, BookView bookV, AddEditBook addEditBook) {
        this.book = book;
        this.bookDAO = bookDAO;
        this.bookView = bookV;
        this.addEditBook = addEditBook;
        
        // Todos los botones que deben tenener una interaccion 
        this.bookView.btnSearch.addActionListener(this);
        this.bookView.btnAdd.addActionListener(this);
        this.bookView.btnEdit.addActionListener(this);
        this.bookView.btnDelete.addActionListener(this);
        
        this.addEditBook.btnSave.addActionListener(this);
        this.addEditBook.btnCancel.addActionListener(this);
               
        // Acciones que deben suceder la principio
        getAllBooks(this.bookView.tableBooks);
    }
    
    // Establece los elementos de la tabla principal ===========================
    public void getAllBooks(JTable Table){ 
        modelo = (DefaultTableModel) Table.getModel();
        List<Book> books = bookDAO.getLibrary();
        Object[] bookObj = new Object[13];
        
        for (int i = 0; i < books.size(); i++) {
            bookObj[0] = books.get(i).getISBN();
            bookObj[1] = books.get(i).getTitle();
            bookObj[2] = books.get(i).getAuthor();
            
            bookObj[3] = books.get(i).getEditorial();
            bookObj[4] = books.get(i).getEdition();
            
            // Pendiente de verificacion
            bookObj[5] = books.get(i).getYearPublication().get(Calendar.YEAR);
            
            bookObj[6] = books.get(i).getShelf();
            bookObj[7] = books.get(i).getSection();
            bookObj[8] = books.get(i).getDepartment();
            
            bookObj[9] = books.get(i).getCategory();
            bookObj[10] = books.get(i).getLanguage();
            bookObj[11] = books.get(i).getStock();
            bookObj[12] = books.get(i).getBFree();
            
            modelo.addRow(bookObj);
        }
        bookView.tableBooks.setModel(modelo);
    }
    
    // Busca los libros de acuerdo a un parametro de busqueda en la colimna ====
    public void getBooksFiltered(JTable Table, String column, String word){ 
        modelo = (DefaultTableModel) Table.getModel();
        List<Book> bookList = bookDAO.searchByColumn(column, word);
        Object[] bookObj = new Object[13];
        
        for (int i = 0; i < bookList.size(); i++) {
            bookObj[0] = bookList.get(i).getISBN();
            bookObj[1] = bookList.get(i).getTitle();
            bookObj[2] = bookList.get(i).getAuthor();
            
            bookObj[3] = bookList.get(i).getEditorial();
            bookObj[4] = bookList.get(i).getEdition();
            bookObj[5] = bookList.get(i).getYearPublication().get(Calendar.YEAR);
            
            bookObj[6] = bookList.get(i).getShelf();
            bookObj[7] = bookList.get(i).getSection();
            bookObj[8] = bookList.get(i).getDepartment();
            
            bookObj[9] = bookList.get(i).getCategory();
            bookObj[10] = bookList.get(i).getLanguage();
            bookObj[11] = bookList.get(i).getStock();
            bookObj[12] = bookList.get(i).getBFree();
            modelo.addRow(bookObj);
        }
        bookView.tableBooks.setModel(modelo);
    }
    
    // Limpia la tabla de todas las filas ======================================
    public void cleanTable(JTable Table) {
        int rows = Table.getRowCount();
        modelo = (DefaultTableModel) Table.getModel();
        for (int i = rows - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }
    
    // Obtiene los datos del formulario
    public void getBibliography() {
        
        // Long
        String ISBN = this.addEditBook.txtISBN.getText().replaceAll("\\D+","");
        book.setISBN(Long.parseLong(ISBN));
        
        // String
        book.setTitle(this.addEditBook.txtTitle.getText());
        book.setAuthor(this.addEditBook.txtAuthor.getText());
        book.setEditorial(this.addEditBook.txtEditorial.getText());
        
        // Int
        book.setEdition(Integer.parseInt(this.addEditBook.txtEdition.getText()));
        
        // Calendar
        Calendar YearPublication = Calendar.getInstance();
        YearPublication.set(Calendar.YEAR, this.addEditBook.txtYearPublication.getYear());
        book.setYearPublication(YearPublication);
        
        // Int
        book.setShelf(Integer.parseInt(this.addEditBook.txtShelf.getText()));
        book.setSection(Integer.parseInt(this.addEditBook.txtSection.getText()));
        book.setDepartment(Integer.parseInt(this.addEditBook.txtDepartment.getText()));
        
        // String
        book.setCategory(this.addEditBook.txtCategory.getText());
        book.setLanguage(this.addEditBook.txtLenguge.getText());
        
        // Int
        book.setStock(Integer.parseInt(this.addEditBook.txtStock.getText()));
        book.setBFree(Integer.parseInt(this.addEditBook.txtBFree.getText()));
    }
    
    // Limpia todos los campos del formulario ==================================
    public void cleanBibliography(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        this.addEditBook.txtISBN.setText(null);
        this.addEditBook.txtTitle.setText(null);
        this.addEditBook.txtAuthor.setText(null);
        this.addEditBook.txtEditorial.setText(null);
        this.addEditBook.txtEdition.setText(null);
        this.addEditBook.txtYearPublication.setYear(cal.get(Calendar.YEAR));
        this.addEditBook.txtShelf.setText(null);
        this.addEditBook.txtSection.setText(null);
        this.addEditBook.txtDepartment.setText(null);
        this.addEditBook.txtCategory.setText(null);
        this.addEditBook.txtLenguge.setText(null);
        this.addEditBook.txtStock.setText(null);
        this.addEditBook.txtBFree.setText(null);
    }
    
    // Cierra el JFrame de la manera correcta ==================================
    public void closeAddEdit(){
        addEditBook.setVisible(false);
        addEditBook.dispose();
    }
    
    // Le pasa la informacion de libro al Jframe secundario ====================
    public void setBibliography(int row, JTable Table){
        modelo = (DefaultTableModel) Table.getModel();
        book = bookDAO.getInfo((Long) modelo.getValueAt(row, 0));
        
        addEditBook.txtISBN.setText(Long.toString(book.getISBN()));
        System.out.println(book.getISBN());
        
        addEditBook.txtTitle.setText(book.getTitle());
        addEditBook.txtAuthor.setText(book.getAuthor());
        addEditBook.txtEditorial.setText(book.getEditorial());
        
        addEditBook.txtEdition.setText(String.valueOf(book.getEdition()));
        
        addEditBook.txtYearPublication.setYear(book.getYearPublication().get(Calendar.YEAR));
     
        addEditBook.txtShelf.setText(String.valueOf(book.getShelf()));
        addEditBook.txtSection.setText(String.valueOf(book.getSection()));
        addEditBook.txtDepartment.setText(String.valueOf(book.getDepartment()));
        
        addEditBook.txtCategory.setText(book.getCategory());
        addEditBook.txtLenguge.setText(book.getLanguage());
        
        addEditBook.txtStock.setText(String.valueOf(book.getStock()));
        addEditBook.txtBFree.setText(String.valueOf(book.getBFree()));
    }
    
    public void filterAndClean(JTable table, String column, String word){
        cleanTable(table);
        getBooksFiltered(table, column, word); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //======================================================================
        //======================================================================
        
        if (e.getSource() == addEditBook.btnCancel) {
           this.closeAddEdit();
        }
        
        // Boton para Agregar y Editar libros ==================================
        if (e.getSource() == addEditBook.btnSave) {
            // Se guarda que operacion se va ha realizar.
            String operation = addEditBook.lblTitle.getText();
            // Se obtiene toda la informacion del libro
            this.getBibliography(); 
            
            // Para Agregar un nuevo libro
            if (operation.equals("Agregar un nuevo libro")) {
    
                if(bookDAO.addToInventory(book)){
                    this.closeAddEdit();
                    this.cleanTable(this.bookView.tableBooks);
                    this.getAllBooks(this.bookView.tableBooks);
                    JOptionPane.showMessageDialog(null, "Se ha agregado el libro correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Hubo un problema al agregar el ejemplar.");
                }
                
            } else {
                // Para editar un libro existente
                if (operation.equals("Editar un libro existente")) {
                    if (bookDAO.updateBibliography(book)) {
                        this.closeAddEdit();
                        this.cleanTable(this.bookView.tableBooks);
                        this.getAllBooks(this.bookView.tableBooks);
                        JOptionPane.showMessageDialog(null, "Se ha actualizado la informacion del libro");
                    } else {
                        JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar el ejemplar.");
                    }
                }
            }
        } 
        
        //======================================================================
        //======================================================================
                
        // BUSCAR ==============================================================
        if (e.getSource() == bookView.btnSearch) {
            Object item = bookView.btnSearchFilter.getSelectedItem();
            String word = bookView.txtSearch.getText();
            //System.out.println(((ComboItems)item).getColumn());
            //System.out.println(((ComboItems)item).getKey());
          
            switch(((ComboItems)item).getKey()) {
                case 1: cleanTable(this.bookView.tableBooks); getAllBooks(this.bookView.tableBooks); break;
                default: filterAndClean(this.bookView.tableBooks, ((ComboItems)item).getColumn(), word); break;
            }
        }
        
        // AGREGAR =============================================================
        if (e.getSource() == bookView.btnAdd) {
            // El panel que contiene el elemento tiene que estar publico tambien.
            addEditBook.lblTitle.setText("Agregar un nuevo libro"); 
            addEditBook.setVisible(true);
            addEditBook.txtISBN.setEditable(true);
            this.cleanBibliography();
        }
        
        // EDITAR ==============================================================
        if (e.getSource() == bookView.btnEdit) {
            if (bookView.tableBooks.getSelectedRow() >= 0) {
                // El panel que contiene el elemento tiene que estar publico tambien.
                addEditBook.lblTitle.setText("Editar un libro existente"); 
                addEditBook.setVisible(true);
                addEditBook.txtISBN.setEditable(false);
                setBibliography(bookView.tableBooks.getSelectedRow(), bookView.tableBooks);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor si desea editar, seleccione una fila.");
            } 
        }
        
        // ELIMINAR ============================================================
        if (e.getSource() == bookView.btnDelete) {             
            if (bookView.tableBooks.getSelectedRow() >= 0 && JOptionPane.showConfirmDialog(null, "¿Desea eleminar el elemento seleccionado?") == 0) {
                Long ISBN = (Long) bookView.tableBooks.getModel().getValueAt(bookView.tableBooks.getSelectedRow(), 0);

                try{
                    bookDAO.delete(ISBN);
                    this.cleanTable(this.bookView.tableBooks);
                    this.getAllBooks(this.bookView.tableBooks);
                    JOptionPane.showMessageDialog(null, "Libro eliminado correctamente.");
                } catch (Exception del) {
                    JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar el ejemplar," + del.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila.");
            } 
        }
    }
}
