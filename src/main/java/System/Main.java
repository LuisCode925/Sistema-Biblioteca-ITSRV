/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Controller.*;
import Model.DAO.*;
import Model.Book;
import Model.ConnectionDB;
import Model.Loan;
import Model.User;
import Model.Librarian;
import Model.SettingsProperties;
import View.*;
import java.util.Calendar;



/**
 *
 * @author Luis
 */
public class Main {
    
    // Objeto que almacenara el usuario logueado en el sistema
    public static Librarian Administrator;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Se actualizan las multas
        LoanDAO.updatePenaltyFee();
         
        // Aqui se inicializan los modelos, las vistas y  los controladores
        
        // =====================================================================
        Librarian librarian = new Librarian(0, "", "", "", "", "");
        LibrarianDAO librarianDAO = new  LibrarianDAO();
        HomeView homeView = new HomeView();
        HomeController ctrlHome = new HomeController(librarian, librarianDAO, homeView);
        
        // =====================================================================
        Calendar LoanDate = Calendar.getInstance();
        Calendar ReturnDate = Calendar.getInstance();
        Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, null, true, Short.MIN_VALUE, 0, 0, 0);
        LoanDAO loanDAO = new LoanDAO();
        UserDAO loanUserDAO = new UserDAO();
        BookDAO loanBookDAO = new BookDAO();
        LoanView loanView= new LoanView(); //
        LoanController ctrlLoan = new LoanController(loan, loanDAO, loanView, loanUserDAO, loanBookDAO);
        
        // =====================================================================
        ReturnView returnView = new ReturnView();
        ReturnController ctrlReturn = new ReturnController(loan, loanDAO, returnView, loanUserDAO, loanBookDAO);
        
        // =====================================================================
        User user = null; 
        UserDAO userDAO = new UserDAO();
        UserView userView = new UserView();
        AddEditUser addEditUser = new AddEditUser();
        UserController ctrlUser = new UserController(user, userDAO, userView, addEditUser);
        
        // =====================================================================
        // Controlador de los libros
        Calendar YearPublication = Calendar.getInstance();
        Book book = new Book( 0, "", "", "", 0, YearPublication, 0, 0, 0, "", "", 0, 0);
        BookDAO bookDAO = new BookDAO();
        BookView bookView = new BookView();
        AddEditBook addEditBook = new AddEditBook();
        BookController ctrlBook = new BookController(book, bookDAO, bookView, addEditBook);
        
        // =====================================================================
        // Controlador de la configuracion
        SettingsProperties properties = new SettingsProperties();
        ConnectionDB con = new ConnectionDB();
        SettingView settingsView = new SettingView();
        SettingController ctrlSetting = new SettingController(settingsView, properties, con);
        
        // =====================================================================
        ReportView reportView = new ReportView();
        ReportController ctrlReport = new ReportController(loan, loanDAO, reportView);
        
        // =====================================================================
        
  
        
        
        // Controlador Principal 
        Dashboard dashboardView = new Dashboard();
        DashboardController ctrlDashboard = new DashboardController(dashboardView, homeView, loanView, returnView, userView, bookView, reportView, settingsView);
        ctrlDashboard.init();
        dashboardView.setVisible(true);
    }
}
