/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.BookView;
import View.Dashboard;
import View.HomeView;
import View.LoanView;
import View.ReportView;
import View.ReturnView;
import View.SettingView;
import View.UserView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPanel;

/**
 *
 * @author Luis
 */
public class DashboardController implements ActionListener, FocusListener {
    
    // Ventana del Controlador
    Dashboard dashboard;
    HomeView home;
    LoanView loan;
    ReturnView returnView;
    UserView user;
    BookView book;
    ReportView report;
    SettingView setting;
     
    public DashboardController(Dashboard dashboard, HomeView home, LoanView loan, ReturnView returnView, UserView user, BookView book, ReportView report, SettingView setting) {
        this.dashboard = dashboard;
        
        // Se establecen las vistas que cambiaran dependiendo de los eventos del menu.
        this.home = home;
        this.loan = loan;
        this.returnView = returnView;
        this.user = user;
        this.book = book;
        this.report = report;
        this.setting = setting;
        
        // Configuracion de los botones del menu.
        this.addActionToButtons();
        
        // Focus
        this.addFocusToButtons();
                
        // Establecemos el Principal por defecto.
        ShowContent(this.home);
    }
    
    private void addActionToButtons(){
        this.dashboard.btnHome.addActionListener(this);
        this.dashboard.btnLoan.addActionListener(this);
        this.dashboard.btnReturn.addActionListener(this);
        this.dashboard.btnUser.addActionListener(this);
        this.dashboard.btnBook.addActionListener(this);
        this.dashboard.btnReport.addActionListener(this);
        this.dashboard.btnSetting.addActionListener(this);
    }
    
    private void addFocusToButtons(){
        this.dashboard.btnHome.addFocusListener(this);
        this.dashboard.btnLoan.addFocusListener(this);
        this.dashboard.btnReturn.addFocusListener(this);
        this.dashboard.btnUser.addFocusListener(this);
        this.dashboard.btnBook.addFocusListener(this);
        this.dashboard.btnReport.addFocusListener(this);
        this.dashboard.btnSetting.addFocusListener(this);
    }
    
    public void init(){
        dashboard.setTitle("Sistema Control de Biblioteca");
        dashboard.setLocationRelativeTo(null);
    }
    
    
    private void ShowContent(JPanel panel){
        panel.setSize(1000, 750);
        this.dashboard.ContentPanel.removeAll();
        this.dashboard.ContentPanel.add(panel, BorderLayout.CENTER);
        this.dashboard.ContentPanel.revalidate();
        this.dashboard.ContentPanel.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== dashboard.btnHome) { ShowContent(this.home); }
        if (e.getSource()== dashboard.btnLoan) { ShowContent(this.loan); }
        if (e.getSource()== dashboard.btnReturn) { ShowContent(this.returnView); }
        if (e.getSource()== dashboard.btnUser) { ShowContent(this.user); }
        if (e.getSource()== dashboard.btnBook) { ShowContent(this.book); }
        if (e.getSource()== dashboard.btnReport) { ShowContent(this.report); }
        if (e.getSource()== dashboard.btnSetting) { ShowContent(this.setting); }
    }

    @Override
    public void focusGained(FocusEvent e) {
        e.getComponent().getParent().setBackground(new Color(36,116,120));
    }

    @Override
    public void focusLost(FocusEvent e) {
        e.getComponent().getParent().setBackground(new Color(32,104,108));
    }

}