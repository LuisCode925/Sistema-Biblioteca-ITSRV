/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Book;
import Model.ExportToExcel;
import Model.Loan;
import Model.DAO.LoanDAO;
import View.ReportView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
public class ReportController implements ActionListener, PropertyChangeListener {
    private Loan loan;
    private LoanDAO loanDAO;
    private ReportView reportView;

    public ReportController(Loan loan, LoanDAO loanDAO, ReportView reportView) {
        this.loan = loan;
        this.loanDAO = loanDAO;
        this.reportView = reportView;
        
        // Botones que se deben escuchar 
        this.reportView.btnExportTab1.addActionListener(this);
        this.reportView.btnBoth.addActionListener(this);
        this.reportView.btnOnlyDelivered.addActionListener(this);
        this.reportView.btnOnlySlopes.addActionListener(this);
        
        // Eventos focus para actualizar automaticamente la tabla
        this.reportView.StartDatePicker.addPropertyChangeListener(this);
        this.reportView.FinishDatePicker.addPropertyChangeListener(this);
        
        
        // Acciones que deben ocurrir por defecto
        this.reportView.StartDatePicker.setMaxSelectableDate(new Date());
        this.reportView.FinishDatePicker.setMaxSelectableDate(new Date());
        this.getAllLoans(this.reportView.LoansTable);
    }
    
    public void getAllLoans(JTable Table){ 
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getAllLoans();
        for (Object[] loan : loans) {
            modelo.addRow(loan);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    // SOBRECARGA: Para espesificar el rango de fechas 
    public void getAllLoans(JTable Table, java.sql.Date start, java.sql.Date end){
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getAllLoans(start, end);
        for (Object[] loan_range : loans) {
            modelo.addRow(loan_range);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    public void getLoansByState(JTable Table, boolean delivery){ 
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getLoansByDelivery(delivery);
        for (Object[] loan : loans) {
            modelo.addRow(loan);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    public void getLoansByState(JTable Table, boolean delivery, Calendar start, Calendar end){ 
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getLoansByDelivery(delivery, start, end);
        for (Object[] loan : loans) {
            modelo.addRow(loan);
        }
        reportView.LoansTable.setModel(modelo);
    }
            
    // Limpia la tabla de todas las filas ======================================
    public void cleanTable(JTable Table) {
        int rows = Table.getRowCount();
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        for (int i = rows - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }
    
    private void searchByRange(){ 
        if ((reportView.StartDatePicker.getCalendar()!=null) && (reportView.FinishDatePicker.getCalendar()!=null)) {
            try {
                java.sql.Date start = new java.sql.Date(reportView.StartDatePicker.getCalendar().getTimeInMillis());
                java.sql.Date end = new java.sql.Date(reportView.FinishDatePicker.getCalendar().getTimeInMillis());
                if (reportView.btnBoth.isSelected()) {
                    getAllLoans(this.reportView.LoansTable, start, end);
                } else {
                    System.out.println("Consulta parcial");
                    getLoansByState(this.reportView.LoansTable, 
                            reportView.btnOnlyDelivered.isSelected(), // Determina se se quieren solo los entregados
                            reportView.StartDatePicker.getCalendar(), 
                            reportView.FinishDatePicker.getCalendar());
                }
            } catch (Exception e) {
                System.out.println("Error searchByRange: "+e.getMessage());
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Todas las acciones para los botones
        if (e.getSource() == reportView.btnExportTab1) {
            ExportToExcel obj;
            try {
                obj = new ExportToExcel();
                obj.toXLS(reportView.LoansTable);
            } catch (IOException ex) {
                System.out.println("Error en btnExportTab1: " + ex.getMessage());
            }
        }// ====================================================================
        
        if (e.getSource() == reportView.btnBoth) {
            this.cleanTable(this.reportView.LoansTable);
            this.getAllLoans(this.reportView.LoansTable);
        }// ====================================================================
        
        if (e.getSource() == reportView.btnOnlyDelivered) {
            this.cleanTable(this.reportView.LoansTable);
            this.getLoansByState(this.reportView.LoansTable, true);
        }
        if (e.getSource() == reportView.btnOnlySlopes) {
            this.cleanTable(this.reportView.LoansTable);
            this.getLoansByState(this.reportView.LoansTable, false);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == reportView.StartDatePicker) { searchByRange(); }
        if(evt.getSource() == reportView.FinishDatePicker){ searchByRange(); }     
    }
    
}
