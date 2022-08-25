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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        this.setAllLoans(this.reportView.LoansTable);
        //searchByRange();
    }
    
    public void setAllLoans(JTable Table){ 
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getAllLoans();
        for (Object[] loan : loans) {
            modelo.addRow(loan);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    // SOBRECARGA: Para espesificar el rango de fechas 
    public void setAllLoans(JTable Table, Calendar start, Calendar end){
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getAllLoans(start, end);
        for (Object[] loan_range : loans) {
            modelo.addRow(loan_range);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    public void setLoansByState(JTable Table, boolean delivery){
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Object[]> loans = loanDAO.getLoansByDelivery(delivery);
        for (Object[] loan : loans) {
            modelo.addRow(loan);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    public void setLoansByState(JTable Table, boolean delivery, Calendar start, Calendar end){
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
        try {
            Calendar start = reportView.StartDatePicker.getCalendar();
            Calendar end = reportView.FinishDatePicker.getCalendar();
            
            if( ((start!=null) && (end==null)) || ((start==null) && (end==null)) || (!start.before(end)) || ((start==null) && (end!=null)) ){ 
                this.cleanTable(this.reportView.LoansTable);        
                if (reportView.btnBoth.isSelected()) {
                    this.setAllLoans(this.reportView.LoansTable);
                    System.out.println("Ambos");
                } else if (reportView.btnOnlySlopes.isSelected()) {
                    this.setLoansByState(this.reportView.LoansTable, false);
                    System.out.println("Solo pendientes");
                } else {
                    this.setLoansByState(this.reportView.LoansTable, true);
                    System.out.println("Solo Enytregados");
                }
            } else { // Entonces si hay fechas validas
                this.cleanTable(this.reportView.LoansTable);
                if (reportView.btnBoth.isSelected()) {
                    this.setAllLoans(this.reportView.LoansTable, start, end);
                } else if (reportView.btnOnlySlopes.isSelected()) {
                    this.setLoansByState(this.reportView.LoansTable, false, start, end);
                } else {
                    this.setLoansByState(this.reportView.LoansTable, true, start, end);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error searchByRange: "+e.getMessage());
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
        
        // Radio button Group
        if (e.getSource() == reportView.btnBoth 
            || e.getSource() == reportView.btnOnlyDelivered 
            || e.getSource() == reportView.btnOnlySlopes) { 
                searchByRange(); 
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == reportView.StartDatePicker) { searchByRange(); }
        if(evt.getSource() == reportView.FinishDatePicker){ searchByRange(); }     
    }
    
}
