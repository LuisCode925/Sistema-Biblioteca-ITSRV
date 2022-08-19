/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Controller.DashboardController;
import Model.Librarian;
import Model.DAO.LoanDAO;
import View.Dashboard;

/**
 *
 * @author Luis
 */
public class Main {
    
    // Objeto que almacenara el usuario logueado en el sistema
    public static Librarian Administrator;
    
    public static Dashboard dashboard; // Vista
    public static DashboardController ctrlDashboard; // Controlador
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Se actualizan las multas
        LoanDAO.updatePenaltyFee();
        
        // Controlador Principal 
        dashboard = new Dashboard();
        ctrlDashboard = new DashboardController(dashboard);
        ctrlDashboard.init(); // Aqui se van ha iniciar los Controladores 
        dashboard.setVisible(true);
    }
}
