/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.Dashboard;
import Controller.*;
import Model.DAO.*;
import Model.Book;
import Model.ConnectionDB;
import Model.Loan;
import Model.User;
import Model.Librarian;
import Model.SettingsProperties;
import System.Main;
import View.*;
import java.util.Calendar;
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
    public Dashboard dashboard;
    public HomeView homeView;
    public LoanView loanView;
    public ReturnView returnView;
    public UserView userView;
    public BookView bookView;
    public ReportView reportView;
    public SettingView settingView;
     
    public DashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
        this.addActionToButtons(); // Evento Action para cambiar los paneles (Views).
        this.addFocusToButtons();// Evento Focus para hacer el cambio color del background.
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
        if(Main.Administrator!=null){
            disableSidebar(false);
            initAllControllers();
        } else {
            disableSidebar(true);
            homeController();
            settingController();
        }
        ShowContent(this.homeView); // Se establce el panel de login por defecto.
    }
    
    public void disableSidebar(boolean state){
        this.dashboard.btnLoan.setEnabled(state);
        this.dashboard.btnReturn.setEnabled(state);
        this.dashboard.btnUser.setEnabled(state);
        this.dashboard.btnBook.setEnabled(state);
        this.dashboard.btnReport.setEnabled(state);
    }
    
    public void initAllControllers(){
        homeController();
        loanController();
        returnController();
        userController();
        bookController();
        settingController();
        reportController();
    }
    
    private void homeController(){
        Librarian librarian = new Librarian(0, "", "", "", "", "");
        LibrarianDAO librarianDAO = new  LibrarianDAO();
        this.homeView = new HomeView();
        HomeController ctrlHome = new HomeController(librarian, librarianDAO, homeView);
    }
    
    private void loanController(){
        Calendar LoanDate = Calendar.getInstance();
        Calendar ReturnDate = Calendar.getInstance();
        Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, null, true, Short.MIN_VALUE, 0, 0, 0);
        LoanDAO loanDAO = new LoanDAO();
        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        this.loanView= new LoanView(); //
        LoanController ctrlLoan = new LoanController(loan, loanDAO, loanView, userDAO, bookDAO);
    }
    
    private void reportController(){
        Calendar LoanDate = Calendar.getInstance();
        Calendar ReturnDate = Calendar.getInstance();
        Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, null, true, Short.MIN_VALUE, 0, 0, 0);
        LoanDAO loanDAO = new LoanDAO();
        this.reportView = new ReportView();
        ReportController ctrlReport = new ReportController(loan, loanDAO, reportView);
    }
    
    private void returnController(){
        Calendar LoanDate = Calendar.getInstance();
        Calendar ReturnDate = Calendar.getInstance();
        Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, null, true, Short.MIN_VALUE, 0, 0, 0);
        LoanDAO loanDAO = new LoanDAO();
        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        this.returnView = new ReturnView();
        ReturnController ctrlReturn = new ReturnController(loan, loanDAO, returnView, userDAO, bookDAO);

    }
    
    private void bookController(){
        Calendar YearPublication = Calendar.getInstance();
        Book book = new Book( 0, "", "", "", 0, YearPublication, 0, 0, 0, "", "", 0, 0);
        BookDAO bookDAO = new BookDAO();
        this.bookView = new BookView();
        AddEditBook addEditBook = new AddEditBook();
        BookController ctrlBook = new BookController(book, bookDAO, bookView, addEditBook);
    }
    
    private void userController(){
        User user = null; 
        UserDAO userDAO = new UserDAO();
        this.userView = new UserView();
        AddEditUser addEditUser = new AddEditUser();
        UserController ctrlUser = new UserController(user, userDAO, userView, addEditUser);
    }
    
    private void settingController(){
        SettingsProperties properties = new SettingsProperties();
        ConnectionDB con = new ConnectionDB();
        this.settingView = new SettingView();
        SettingController ctrlSetting = new SettingController(settingView, properties, con);
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
        if (e.getSource()== dashboard.btnHome) { 
            homeController(); ShowContent(this.homeView); 
        }
        if (e.getSource()== dashboard.btnLoan) { 
            loanController(); ShowContent(this.loanView); 
        }
        if (e.getSource()== dashboard.btnReturn) { 
            returnController(); ShowContent(this.returnView); 
        }
        if (e.getSource()== dashboard.btnUser) { 
            userController(); ShowContent(this.userView); 
        }
        if (e.getSource()== dashboard.btnBook) { 
            bookController(); ShowContent(this.bookView); 
        }
        if (e.getSource()== dashboard.btnReport) { 
            reportController(); ShowContent(this.reportView); 
        }
        if (e.getSource()== dashboard.btnSetting) { 
            ShowContent(this.settingView); 
        }
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