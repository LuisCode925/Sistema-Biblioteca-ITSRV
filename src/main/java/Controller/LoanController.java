/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Book;
import Model.DAO.BookDAO;
import Model.Loan;
import Model.DAO.LoanDAO;
import Model.User;
import Model.DAO.UserDAO;
import View.LoanView;
import System.Main;
// import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;


/**
 *
 * @author Luis
 */
public class LoanController implements ActionListener, FocusListener{
    private Loan loan;
    private LoanDAO loanDAO;
    private UserDAO loanUserDAO;
    private BookDAO loanBookDAO;
    private LoanView loanView;

    public LoanController(Loan loan, LoanDAO loanDAO, LoanView loanView, UserDAO loanUserDAO, BookDAO loanBookDAO) {
        this.loan = loan;
        this.loanDAO = loanDAO;
        this.loanView = loanView;
        this.loanUserDAO = loanUserDAO;
        this.loanBookDAO = loanBookDAO;
        
        // Botones que desencadenan acciones
        this.loanView.btnSave.addActionListener(this);
        this.loanView.btnClean.addActionListener(this);
        
        // Eventos focus para llenar la lista de autocompletado
        this.loanView.txtControlNumber.addFocusListener(this);
        this.loanView.txtISBN.addFocusListener(this);
        this.loanView.LoanDatePicker.addFocusListener(this);
        
        // Acciones que se debe realizar al principio
        setDatePickers();
        
        // this.loanView.ReturnDatePicker.setDate(date);
        if (Main.Administrator != null) {
            // Se pone en el panel el nombre del administrador logueado
            this.loanView.lblAutorize.setText(Main.Administrator.getNames() + " " + Main.Administrator.getLastNames()); 
        } 
    }
    
    private void getLoanForm() {
        try {
            int CN = Integer.parseInt(this.loanView.txtControlNumber.getText());
            Long Book = Long.parseLong(this.loanView.txtISBN.getText());

            this.loan.setControlNumber(CN);
            this.loan.setISBN(Book);
            this.loan.setLoanDate(this.loanView.LoanDatePicker.getCalendar());// Obtiene un objeto del tipo Calendar
            this.loan.setReturnDate(this.loanView.ReturnDatePicker.getCalendar());
            this.loan.setAutorize(Main.Administrator.getId());
            
            /*
                Gson gson = new Gson();
                String JSON = gson.toJson(this.loan);
                System.out.println(JSON); 
            */
        } catch (NumberFormatException nfe) {
            System.out.println("getLoanForm: "+nfe.getMessage());
        }
    }
    
    private void setDatePickers(){
        Calendar today = Calendar.getInstance();
        this.loanView.LoanDatePicker.setDate(today.getTime());
        Calendar returnDay = Calendar.getInstance();
        returnDay.add(Calendar.DATE, 2);
        this.loanView.ReturnDatePicker.setDate(returnDay.getTime());
    }
    
    // Establece el formulario a sus valores por defecto
    private void cleanLoanForm(){
        this.loanView.txtControlNumber.setText(null);
        this.loanView.txtISBN.setText(null);
        setDatePickers();
        enableForUser();
        // Informacion del usuario
        this.loanView.lblFullName.setText(null);
        this.loanView.lblPhoneNumber.setText(null);
        this.loanView.lblEmail.setText(null);
        this.loanView.txtNLoan.setText(null);
        // Informacion del Libro
         this.loanView.lblBookTitle.setText(null);
        this.loanView.lblBookAuthor.setText(null);
        this.loanView.lblBookEditorial.setText(null);
        // Botones
        this.loanView.btnSave.setEnabled(true);
    }
    
    private void loadInfoUser(int ControlNumber){
        User user = loanUserDAO.getUser(ControlNumber);
        this.loanView.lblFullName.setText(user.getNames()+ " "+user.getLastNames());
        this.loanView.lblPhoneNumber.setText(String.valueOf(user.getPhone()));
        this.loanView.lblEmail.setText(user.getEmail());
    }
    
    private void loadInfoBook(Long ISBN){
        Book book = loanBookDAO.getBook(ISBN);
        this.loanView.lblBookTitle.setText(book.getTitle());
        this.loanView.lblBookAuthor.setText(book.getAuthor());
        this.loanView.lblBookEditorial.setText(book.getEditorial());
    }
    
    private void disableForUser(){
        this.loanView.txtISBN.setEnabled(false);
        this.loanView.LoanDatePicker.setEnabled(false);
        this.loanView.ReturnDatePicker.setEnabled(false);
        this.loanView.btnSave.setEnabled(false);
    }
    
    private void enableForUser(){
        this.loanView.txtISBN.setEnabled(true);
        this.loanView.LoanDatePicker.setEnabled(true);
        this.loanView.ReturnDatePicker.setEnabled(true);
        this.loanView.btnSave.setEnabled(true);
    }
    
    // Carga la un array de sugerencias en el JTextField
    private void loadSuggestions(String[] suggestions, JTextField field) {
        JList options = new JList(suggestions);
        AutoCompleteDecorator.decorate(options, field, ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
    }
    
    private void CargarTipos(String[] suggestions, JComboBox field) {
        field.setModel(new DefaultComboBoxModel<>(suggestions));
    }

    @Override 
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == loanView.btnClean){ 
            cleanLoanForm();
        }// ====================================================================
        
        if(e.getSource() == loanView.btnSave){
            getLoanForm();
            if (loanDAO.registerLoan(loan)) {
                cleanLoanForm();//
                JOptionPane.showMessageDialog(null, "Prestamo realizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Hubo un error al insertar el prestamo");
            }
        }//=====================================================================
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        
        // ENTRANDO: Cuando se va a escribir el numero de control. ================================================
        if (e.getSource() == loanView.txtControlNumber) {
            List<String> suggestions = loanUserDAO.getAllControlNumbers();
            String[] options = new String[suggestions.size()];
            suggestions.toArray(options);
            loadSuggestions(options, this.loanView.txtControlNumber);      
        } //====================================================================================================*/
        
        if(e.getSource() == loanView.txtISBN){
            List<String> suggestions = loanBookDAO.getAllISBN();
            String[] options = new String[suggestions.size()];
            suggestions.toArray(options);
            loadSuggestions(options, this.loanView.txtISBN);
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        
        // SALIENDDO: Cuando se pasa al campo del ISBN - TODO:  Establecer el rango del numero de control de los docentes
        if (e.getSource() == loanView.txtControlNumber && loanView.txtControlNumber.getText().length() > 0) {
            // Comprobar antes que el usuario pueda tener un prestamo
            try {
                int ControlNumber = Integer.parseInt(loanView.txtControlNumber.getText());
                List<Long> loans = loanDAO.isFullLoans(ControlNumber); // Numero de prestamos
                if(loans.size()>=3){
                    // Desabilitar todos exepto el boton de limpiar
                    loadInfoUser(ControlNumber);
                    this.loanView.txtNLoan.setText("3");
                    disableForUser();
                    JOptionPane.showMessageDialog(null, "El Usuario ya ha alcanzado el limite de ejemplares.");
                } else if(loanUserDAO.isBanned(ControlNumber)){
                    loadInfoUser(ControlNumber);
                    disableForUser();
                    JOptionPane.showMessageDialog(null, "El Usuario ingresado se encuentra Baneado, por el incumplimiento de multas.");
                } else {
                    loadInfoUser(ControlNumber);
                    this.loanView.txtNLoan.setText(String.valueOf(loans.size()));
                }  
            } catch (NumberFormatException NumberExcep) {
                System.out.println("Error al convertir a Int: "+ NumberExcep.getMessage());
            }
            
        }// ==================================================================== */
        
        
        // SALIENDO: se consulta si el libro ingresado tiene ejemplares disponibles 
        if (e.getSource() == loanView.txtISBN) {
            
            // Libros que el administrador permite para PRESTAMOS
            if (loanView.txtISBN.getText().length() > 0) {
                try {
                    Long searchBook = Long.parseLong(loanView.txtISBN.getText());
                    int booksAvalible = loanBookDAO.getBooksAvalible(searchBook); 

                    // Libros que realmente estan en prestamos activos (QUE NO HAN SIDO ENTREGADOS)
                    List<Integer> takedBooks = loanDAO.getBooksByISBN(searchBook);

                    if (takedBooks.size() < booksAvalible) {
                        this.loanView.ReturnDatePicker.setEnabled(true);
                        Calendar ReturnDate = Calendar.getInstance();
                        ReturnDate.add(Calendar.DATE, 3);
                        Date ReturnDateP = ReturnDate.getTime();
                        this.loanView.ReturnDatePicker.setDate(ReturnDateP);
                        this.loanView.btnSave.setEnabled(true);
                        loadInfoBook(searchBook);
                    } else {
                        this.loanView.btnSave.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "No se pueden prestar mas libros de este ejemplar.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error al convertir a long: "+ ex.getMessage());
                }
                
            }
            
        }// ==================================================================== */
        
    }
}
