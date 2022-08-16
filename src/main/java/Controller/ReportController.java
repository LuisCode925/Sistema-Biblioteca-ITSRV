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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis
 */
public class ReportController implements ActionListener, FocusListener {
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
        
        
        // Acciones que deben ocurrir por defecto
        this.getAllLoans(this.reportView.LoansTable);
    }
    
    public void getAllLoans(JTable Table){ 
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Loan> loans = loanDAO.getAllLoans();
        Object[] loanObj = new Object[10];
        
        for (int i = 0; i < loans.size(); i++) {
            loanObj[0] = loans.get(i).getId();
            loanObj[1] = loans.get(i).getControlNumber();
            loanObj[2] = loans.get(i).getISBN();
            
            SimpleDateFormat LDF = new SimpleDateFormat("dd/MM/yyyy");
            loanObj[3] = LDF.format(loans.get(i).getLoanDate().getTime());
            
            SimpleDateFormat RDF = new SimpleDateFormat("dd/MM/yyyy");
            loanObj[4] = RDF.format(loans.get(i).getReturnDate().getTime());
           
            loanObj[5] = loans.get(i).isDelivered();
            loanObj[6] = loans.get(i).getRenovations();
            loanObj[7] = loans.get(i).getPenaltyFee();
            
            // TODO Falta hacer el cruce para loa nombres de los bibliotecarios en el loanDAO
            loanObj[8] = loans.get(i).getAutorize();
            loanObj[9] = loans.get(i).getSender();
            
            modelo.addRow(loanObj);
        }
        reportView.LoansTable.setModel(modelo);
    }
    
    public void getLoansByState(JTable Table, boolean delivery){ 
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        List<Loan> loans = loanDAO.getLoansByDelivery(delivery);
        Object[] loanObj = new Object[10];
        
        for (int i = 0; i < loans.size(); i++) {
            loanObj[0] = loans.get(i).getId();
            loanObj[1] = loans.get(i).getControlNumber();
            loanObj[2] = loans.get(i).getISBN();
            
            SimpleDateFormat LDF = new SimpleDateFormat("dd/MM/yyyy");
            loanObj[3] = LDF.format(loans.get(i).getLoanDate().getTime());
            
            SimpleDateFormat RDF = new SimpleDateFormat("dd/MM/yyyy");
            loanObj[4] = RDF.format(loans.get(i).getReturnDate().getTime());
           
            loanObj[5] = loans.get(i).isDelivered();
            loanObj[6] = loans.get(i).getRenovations();
            loanObj[7] = loans.get(i).getPenaltyFee();
            
            // TODO Falta hacer el cruce para loa nombres de los bibliotecarios en el loanDAO
            loanObj[8] = loans.get(i).getAutorize();
            loanObj[9] = loans.get(i).getSender();
            
            modelo.addRow(loanObj);
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
    public void focusGained(FocusEvent e) {
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }
    
}
