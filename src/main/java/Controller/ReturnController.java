/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.DAO.BookDAO;
import Model.Loan;
import Model.DAO.LoanDAO;
import Model.DAO.UserDAO;
import View.ReturnView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
import org.apache.commons.lang3.time.DateUtils;
import System.Main;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JSpinner;

/**
 *
 * @author Luis
 */
public class ReturnController implements ActionListener, FocusListener {
    
    private Loan loan;
    private LoanDAO loanDAO;
    private ReturnView returnView;
    private UserDAO loanUserDAO;
    private BookDAO loanBookDAO;
    
    // loan, loanDAO, returnView, loanUserDAO, loanBookDAO
    public ReturnController(Loan loan, LoanDAO loanDAO, ReturnView returnView,UserDAO loanUserDAO, BookDAO loanBookDAO) {
        this.loan = loan;
        this.loanDAO = loanDAO;
        this.returnView = returnView;
        this.loanUserDAO = loanUserDAO;
        this.loanBookDAO = loanBookDAO;
        
        // Botones para renovar y finalizar un prestamo
        this.returnView.btnRenovate.addActionListener(this);
        this.returnView.btnFinalize.addActionListener(this);
        this.returnView.btnClean.addActionListener(this);
        this.returnView.cboxBookISBN.addActionListener(this);
        
        // Eventos focus para llenar la lista de autocompletado
        this.returnView.txtControlNumber.addFocusListener(this);
        
        
        // Listener de las tablas para 
        this.returnView.LoansToFinalize.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override public void valueChanged(ListSelectionEvent e) {
                try {
                    Long ISBN = Long.parseLong(returnView.LoansToFinalize.getValueAt(returnView.LoansToFinalize.getSelectedRow(), 1).toString());
                    int ControlNumber = Integer.parseInt(returnView.LoansToFinalize.getValueAt(returnView.LoansToFinalize.getSelectedRow(), 2).toString());
                    loadRowToForm(ISBN, ControlNumber);
                    returnView.LoansToFinalize.getSelectionModel().clearSelection();
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("Error valueChanged: " +ae.getMessage());
                }
            }  
        });
        
        this.returnView.loansExpired.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override public void valueChanged(ListSelectionEvent e) {
                try {
                    Long ISBN = Long.parseLong(returnView.loansExpired.getValueAt(returnView.loansExpired.getSelectedRow(), 1).toString());
                    int ControlNumber = Integer.parseInt(returnView.loansExpired.getValueAt(returnView.loansExpired.getSelectedRow(), 2).toString());
                    loadRowToForm(ISBN, ControlNumber);
                    returnView.loansExpired.getSelectionModel().clearSelection();
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("Error valueChanged: "+ae.getMessage());
                }
            }
        });
        
        this.listLoansToday(this.returnView.LoansToFinalize);
        this.listLoansExpired(this.returnView.loansExpired);
        try {
            // Return 
            this.returnView.lbLibrarianSender.setText(Main.Administrator.getNames()+ " "+Main.Administrator.getLastNames()); 
            this.returnView.lblLibrarianId.setVisible(false);
            this.returnView.lblLibrarianId.setText(String.valueOf(Main.Administrator.getId()));
        } catch (NullPointerException e) {
            System.out.println("Librarian Administrator es "+e.getMessage());
        }
        
    }
    
    private void cleanFormReturn(){
        this.disableLoanInformation(true);
        
        // Autocompletado de campos
        this.returnView.txtControlNumber.setText(null);
        this.returnView.cboxBookISBN.removeAllItems();
        
        // Informacion solo lectura
        this.returnView.txtReturnDate.setDate(null);
        returnView.lblReturnDate.setForeground(Color.black);
        this.returnView.txtRenovations.setText(null);
        this.returnView.txtPenalty.setText(null);
        
        // Vaciado de las anteriores filas
        cleanTable(this.returnView.LoansToFinalize);
        cleanTable(this.returnView.loansExpired);
        
        // Consultar las tablas
        this.listLoansToday(this.returnView.LoansToFinalize);
        this.listLoansExpired(this.returnView.loansExpired);
    }
    
    private void disableLoanInformation(boolean state){       
        this.returnView.txtReturnDate.setEnabled(state);
        this.returnView.txtRenovations.setEditable(state);
        this.returnView.txtPenalty.setEditable(state);
    }
    
    public void cleanTable(JTable Table) {
        int rows = Table.getRowCount();
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        for (int i = rows - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }
    
    // Muestra todos los prestamos que finalizaran HOY (Fecha Actual)
    public void listLoansToday(JTable Table){
        DefaultTableModel tab_model = (DefaultTableModel) Table.getModel();
        List<Loan> list_of_loans = loanDAO.getLoansToFinalize();
 
        // Numero de Propiedades: 3
        Object[] loanObject = new Object[3];
        for (int i = 0; i < list_of_loans.size(); i++) {
            
            loanObject[0] = list_of_loans.get(i).getId();
            loanObject[1] = list_of_loans.get(i).getISBN();
            loanObject[2] = list_of_loans.get(i).getControlNumber();
                        
            tab_model.addRow(loanObject);
        }
        returnView.LoansToFinalize.setModel(tab_model);
    }
    
    // Muestra todos los prestamos que finalizaron
    public void listLoansExpired(JTable Table){
        DefaultTableModel tab_model = (DefaultTableModel) Table.getModel();
        List<Loan> list_of_loans = loanDAO.getLoansExpired();
 
        // Numero de Propiedades: 3
        Object[] loanObject = new Object[3];
        for (int i = 0; i < list_of_loans.size(); i++) {
            
            loanObject[0] = list_of_loans.get(i).getId();
            loanObject[1] = list_of_loans.get(i).getISBN();
            loanObject[2] = list_of_loans.get(i).getControlNumber();
                        
            tab_model.addRow(loanObject);
        }
        returnView.loansExpired.setModel(tab_model);
    }
    
    private void loadRowToForm(Long ISBN, int ControlNumber){
        returnView.txtControlNumber.setText(String.valueOf(ControlNumber));
        // returnView.txtControlNumber.transferFocus(); // Mueve el focus al siguiente componente
        returnView.cboxBookISBN.removeAllItems();
        returnView.cboxBookISBN.addItem(ISBN.toString());
        returnView.cboxBookISBN.setSelectedIndex(0);
        returnView.cboxBookISBN.transferFocus();
    }
    
    // Carga la un array de sugerencias en el JTextField
    private void loadSuggestions(String[] suggestions, JTextField field) {
        JList options = new JList(suggestions);
        AutoCompleteDecorator.decorate(options, field, ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
    }
    
    private void loadSuggestionsCBox(String[] suggestions, JComboBox field) {
        field.setModel(new DefaultComboBoxModel<>(suggestions));
    }
    
    public static boolean isAfterReturnDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.isAfter(localDate2);
    }
    public static boolean isBeforeReturnDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.isBefore(localDate2);
    }
    
    public void renewLoan(){
        try {
            int ControlNumber = Integer.parseInt(this.returnView.txtControlNumber.getText());
            Long ISBN = Long.parseLong(this.returnView.cboxBookISBN.getSelectedItem().toString());
            short Renovations = (short) (Short.parseShort(this.returnView.txtRenovations.getText()) + 1);
            
            Calendar returnDate = Calendar.getInstance();
            returnDate.setTime(new Date());
            returnDate.add(Calendar.DATE, 3);
            
            if (this.loanDAO.updateReturnDate(ISBN, ControlNumber, returnDate, Renovations)){ 
                this.cleanFormReturn();
                JOptionPane.showMessageDialog(null, "Renovacion exitosa");
            } else { 
                JOptionPane.showMessageDialog(null, "Hubo un error intentelo despues"); 
            }
        } catch ( NumberFormatException nfe) {
            System.out.println("Error renewLoan: " + nfe.getMessage());
        }
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == returnView.btnFinalize) {
            // Funcion para mostrar finalizar el prestamo con el Administrador que recibio el libro.
            int ControlNumber = Integer.parseInt(returnView.txtControlNumber.getText());
            Long ISBN= Long.parseLong((String) returnView.cboxBookISBN.getSelectedItem());
            int Sender = Integer.parseInt(returnView.lblLibrarianId.getText());
            
            if (loanDAO.finalizeLoan(ISBN, ControlNumber, Sender)) {
                cleanFormReturn();
                JOptionPane.showMessageDialog(null, "Prestamo finalizado Correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error, no se pudo finalizar el prestamo.");
            }
        }
        
        if (e.getSource() == returnView.btnRenovate) {
            if (DateUtils.isSameDay(new Date(), returnView.txtReturnDate.getDate()) || 
                isAfterReturnDay(new Date(), returnView.txtReturnDate.getDate())) {
                renewLoan();
            } else if (isBeforeReturnDay(new Date(), returnView.txtReturnDate.getDate())) {
                // Cuando es muy pronto para renovar
                JOptionPane.showMessageDialog(null, "Es muy pronto para renovar el prestamo.");
            }
        }
        
        // Para escuchar la seleccion del ComboBox de los ISBN
        if (e.getSource() == returnView.cboxBookISBN) {
            //if (!returnView.cboxBookISBN.getSelectedItem().equals("--- Seleccionar ISBN ---")) {
            try{
                Long ISBN_book = Long.parseLong((String) returnView.cboxBookISBN.getSelectedItem());
                Loan book = loanDAO.getInfo(ISBN_book, Integer.parseInt(returnView.txtControlNumber.getText()));
                Date expectedDate = book.getReturnDate().getTime();
                // System.out.println(expectedDate); System.out.println(new Date());
                returnView.txtReturnDate.setDate(expectedDate);
                if (expectedDate.compareTo(new Date()) < 0 ) {
                    //System.out.println("Te pasaste de la fecha establecida");
                    returnView.lblReturnDate.setForeground(Color.red);
                } else {
                    returnView.lblReturnDate.setForeground(Color.black);
                }
                returnView.txtRenovations.setText(String.valueOf(book.getRenovations()));
                returnView.txtPenalty.setText(String.valueOf(book.getPenaltyFee()));
                this.disableLoanInformation(false);
            } catch(NumberFormatException nfe){
                // Daba un error cuando se hacia la limpieza del formulario
                System.out.println("Error en el item, "+ nfe.getMessage());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        if (e.getSource() == returnView.btnClean) {
            cleanFormReturn();
        }
     
    }

    @Override
    public void focusGained(FocusEvent e) {
        
        // ENTRANDO: Cuando se va a escribir el numero de control. ================================================
        if (e.getSource() == returnView.txtControlNumber) {
            List<String> suggestions = loanUserDAO.getAllControlNumbers();
            String[] options = new String[suggestions.size()];
            suggestions.toArray(options);
            loadSuggestions(options, this.returnView.txtControlNumber);      
        } //====================================================================================================*/
       
    }

    @Override
    public void focusLost(FocusEvent e) {
                
        // SALIENDDO: Cuando se pasa al campo del ISBN - TODO:  Establecer el rango del numero de control de los docentes
        if (e.getSource() == returnView.txtControlNumber && !"".equals(returnView.txtControlNumber.getText())) {
            // Comprobar antes que el usuario pueda tener un prestamo
            try {
                int ControlNumber = Integer.parseInt(returnView.txtControlNumber.getText());
                List<Long> longs = loanDAO.getUndelivered(ControlNumber); // Numero de prestamos
                List<String> loans = new ArrayList<>(longs.size()+1);
                loans.add("--- Seleccionar ISBN ---");
                for(Long loan_item: longs){
                    loans.add(String.valueOf(loan_item));
                }
                String[] options = new String[loans.size()];
                loans.toArray(options);
                loadSuggestionsCBox(options, this.returnView.cboxBookISBN);
            } catch (NumberFormatException NumberExcep) {
                System.out.println("Error al convertir a Int: "+ NumberExcep.getMessage());
            }
            
        }// ==================================================================== */        
    }   
    
}
