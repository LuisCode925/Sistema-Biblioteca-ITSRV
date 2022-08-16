/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.ConnectionDB;
import Model.Loan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis
 */
public class LoanDAO extends ConnectionDB {
    
    public static boolean updatePenaltyFee(){
        PreparedStatement ps = null;
        ConnectionDB con = new ConnectionDB();
        String SQL = "CALL updatePenaltyFee(@PenaltyFeeValue)";
        
        try { 
            ps = con.getConnection().prepareStatement(SQL);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al updatePenaltyFee: " + e.getMessage());
            return false;
        }
    } //======================================================
    
    // Obtiene solo un prestamo espesificando el Numero de Control y el ISBN
    public Loan getLoan(Long ISBN, int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        Calendar LoanDate = Calendar.getInstance();
        Calendar ReturnDate = Calendar.getInstance();
        Calendar lastUpdate = Calendar.getInstance();
        
        Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, lastUpdate, false, Short.MIN_VALUE, 0, 0, 0);
        
        String sql = "SELECT * FROM loans WHERE (ControlNumber=?) AND (ISBN=?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            ps.setLong(2, ISBN);
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
             
                // Se comineza desde la columna 1 no del 0.
                loan.setId(rs.getInt(1));
                loan.setControlNumber(rs.getInt(2));
                loan.setISBN(rs.getLong(3));
        
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));
                loan.setLoanDate(LoanDate);
                
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                loan.setReturnDate(ReturnDate);
                
                // sql.date to Calendar
                lastUpdate.setTime(rs.getDate(6));
                loan.setLastUpdate(lastUpdate);
                
                loan.setDelivered(rs.getBoolean(7));
                loan.setRenovations(rs.getShort(8));
                loan.setPenaltyFee(rs.getDouble(9));
                
                loan.setAutorize(rs.getInt(10));
                loan.setSender(rs.getInt(11));
              
            }
        } catch (SQLException e) {
            System.out.println("Error getLoan: " + e.getMessage());
        }
        return loan;
    }// =============================================
    
    // Obtiene todos los prestamos de la Base de Datos.
    public List<Loan> getAllLoans(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Loan> datos = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                Calendar lastUpdate = Calendar.getInstance();
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, lastUpdate, false, Short.MIN_VALUE, 0, 0, 0);
               
                // Se comineza desde la columna 1 no del 0.
                loan.setId(rs.getInt(1));
                loan.setControlNumber(rs.getInt(2));
                loan.setISBN(rs.getLong(3));
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));
                loan.setLoanDate(LoanDate);
              
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                loan.setReturnDate(ReturnDate);
                
                // sql.date to Calendar
                if (rs.getDate(6)!=null) {
                    lastUpdate.setTime(rs.getDate(6));
                    loan.setLastUpdate(lastUpdate);
                }
                
                loan.setDelivered(rs.getBoolean(7));
                loan.setRenovations(rs.getShort(8));
                loan.setPenaltyFee(rs.getDouble(9));
                
                loan.setAutorize(rs.getInt(10));
                loan.setSender(rs.getInt(11));
              
                datos.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllLoans: " + e.getMessage());
        }
        return datos;
    }// ====================================================================
    
    // Obtiene todos los prestamos de la Base de Datos.
    public List<Loan> getLoansByDelivery(boolean delivery){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Loan> datos = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE Delivered=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, delivery);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                Calendar lastUpdate = Calendar.getInstance();
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, lastUpdate, false, Short.MIN_VALUE, 0, 0, 0);
               
                // Se comineza desde la columna 1 no del 0.
                loan.setId(rs.getInt(1));
                loan.setControlNumber(rs.getInt(2));
                loan.setISBN(rs.getLong(3));
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));
                loan.setLoanDate(LoanDate);
              
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                loan.setReturnDate(ReturnDate);
                
                // sql.date to Calendar
                if (rs.getDate(6)!=null) {
                    lastUpdate.setTime(rs.getDate(6));
                    loan.setLastUpdate(lastUpdate);
                }
                
                loan.setDelivered(rs.getBoolean(7));
                loan.setRenovations(rs.getShort(8));
                loan.setPenaltyFee(rs.getDouble(9));
                
                loan.setAutorize(rs.getInt(10));
                loan.setSender(rs.getInt(11));
              
                datos.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Error getLoansByDelivery: " + e.getMessage());
        }
        return datos;
    }// ====================================================================
    
    // Obtiene todos los prestamos de la Base de Datos que vencen HOY
    public List<Loan> getLoansToFinalize(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Loan> datos = new ArrayList<>();
        // Solo se necesitan 3 campos para la tabla: Id, ISBN y Numero de Control
        String sql = "SELECT Id, ISBN, ControlNumber FROM loans WHERE (ReturnDate = CURRENT_DATE()) AND (Delivered=false)";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                // Calendar LoanDate = Calendar.getInstance();
                // Calendar ReturnDate = Calendar.getInstance();
      
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, null, null, null, false, Short.MIN_VALUE, 0, 0, 0);
                
                // Se comineza desde la columna 1 no del 0.
                loan.setId(rs.getInt(1));
                loan.setISBN(rs.getLong(2));
                loan.setControlNumber(rs.getInt(3));
              
                datos.add(loan); 
            }
        } catch (SQLException e) {
            System.out.println("Error getLoansToFinalize(): "+e.getMessage());
        }
        return datos;
    } //==============================================================
    
    public List<Loan> getLoansExpired(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Loan> datos = new ArrayList<>();
        String sql = "SELECT Id, ISBN, ControlNumber FROM loans WHERE (ReturnDate < CURRENT_DATE()) AND (Delivered=false)";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                //Calendar LoanDate = Calendar.getInstance();
                // Calendar ReturnDate = Calendar.getInstance();
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, null, null, null, false, Short.MIN_VALUE, 0, 0, 0);
                
                // Se comineza desde la columna 1 no del 0.
                loan.setId(rs.getInt(1));
                loan.setISBN(rs.getLong(2));
                loan.setControlNumber(rs.getInt(3));
              
                datos.add(loan); 
            }
        } catch (SQLException e) {
            System.out.println("Error getLoansExpired(): "+e.getMessage());
        }
        return datos;
    }//===================================================================
    
    // Comprueba que el usuario pueda realizar un prestamo
    public List<Long> isFullLoans(int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Long> datos = new ArrayList<>();
        String sql = "SELECT ISBN FROM loans WHERE ControlNumber=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            rs = ps.executeQuery();
            
            while(rs.next()) {                
                // Se comineza desde la columna 1 no del 0.
                datos.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("Error isFullLoans: "+e.getMessage());
        }
        return datos;
    } //=====================================================
    
    // Comprueba que el usuario pueda realizar un prestamo
    public List<Integer> getBooksByISBN(Long ISBN){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Integer> datos = new ArrayList<>();
        String sql = "SELECT ControlNumber FROM loans WHERE (ISBN=?) AND (Delivered=false)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, ISBN);
            rs = ps.executeQuery();
            
            while(rs.next()) {                
                datos.add(rs.getInt(1));// Se comineza desde la columna 1 no del 0.
            }
        } catch (SQLException e) {
            System.out.println("Error getBooksByISBN: "+e);
        }
        return datos;
    }// ======================================================
    
    // Elimina un prestamo de la base de datos
    public boolean deleteLoan(Loan loan){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "DELETE FROM loans WHERE Id=?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, loan.getId());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleteLoan: "+e.getMessage());
            return false;
        }
    } //===================================================================
    
    // Registra el prestamo en la base de datos
    public boolean registerLoan(Loan loan){
        PreparedStatement ps = null;
        Connection con = getConnection();
        
        String SQL = "INSERT INTO loans (Id, ControlNumber, ISBN, LoanDate, ReturnDate, lastUpdate, Delivered, Renovations, PenaltyFee, Autorize, Sender) VALUES (null , ?, ?, ?, ?, null, false, 0, 0, ?, null)";
        
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setInt(1, loan.getControlNumber());
            ps.setLong(2, loan.getISBN()); 
            
            // Calendar to sql.date
            java.sql.Date loanDate = new java.sql.Date(loan.getLoanDate().getTimeInMillis());
            ps.setDate(3, loanDate);
            
            // Calendar to sql.date
            java.sql.Date returnDate = new java.sql.Date(loan.getReturnDate().getTimeInMillis());
            ps.setDate(4, (java.sql.Date) returnDate);
            
            ps.setInt(5, loan.getAutorize());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error registerLoan: "+e.getMessage());
            return false;
        }
    } //==================================================================
    
    // Actualiza la informacion del prestamo
    public boolean updateUserInfo(Loan loan){
        PreparedStatement ps = null;
        Connection con = getConnection();
        
        String SQL = "UPDATE loans SET ControlNumber=?, ISBN=?, LoanDate=?, ReturnDate=?, Delivered=?, Renovations=?, PenaltyFee=?, Autorize=?, Sender=? WHERE Id=?";
        
        try {
            ps = con.prepareStatement(SQL);
                    
            ps.setInt(1, loan.getControlNumber());
            ps.setLong(2, loan.getISBN());
            
            ps.setDate(3, (java.sql.Date) loan.getLoanDate().getTime());
            ps.setDate(4, (java.sql.Date) loan.getReturnDate().getTime());
          
            
            ps.setBoolean(5, loan.isDelivered());
            ps.setShort(6, loan.getRenovations());
            ps.setDouble(7, loan.getPenaltyFee());
            
            ps.setInt(8, loan.getAutorize());
            ps.setInt(9, loan.getId());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updateUserInfo: "+e.getMessage());
            return false;
        }
    } // ===============================================================
    
    // Actualiza la informacion del prestamo
    public boolean updateReturnDate(Long ISBN, int ControlNumber, Calendar returnDate, Short Returns){
        PreparedStatement ps = null;
        Connection con = getConnection();
        
        String SQL = "UPDATE loans SET ReturnDate=?, Renovations=? WHERE (ISBN=?) AND (ControlNumber=?)";
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setDate(1, (java.sql.Date) returnDate.getTime() ); // Calendar to sql.date
            ps.setShort(2, Returns++); // Se aumenta en 1 el numero de renovaciones
            ps.setLong(3, ISBN);
            ps.setInt(4, ControlNumber);
           
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updateReturnDate: "+e.getMessage());
            return false;
        }
    } //==============================================
    
    // Finaliza el prestamo y establece que administrador recibio el libro
    public boolean finalizeLoan(Long ISBN, int ControlNumber, int Sender){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "UPDATE loans SET Delivered=true, Sender=? WHERE (ISBN=?) AND (ControlNumber=?)";
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setInt(1, Sender);
            ps.setLong(2, ISBN);
            ps.setInt(3, ControlNumber);
       
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error finalizeLoan: "+e.getMessage());
            return false;
        }
    } //=============================================================================
    
    public List searchByColumn(String column, String word){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;

        List<Loan> datos = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE " + column + " LIKE ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + word + "%");

            rs = ps.executeQuery();

            while (rs.next()) { 
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                Calendar lastUpdate = Calendar.getInstance();
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, lastUpdate, false, Short.MIN_VALUE, 0, 0, 0);

                // Se comineza desde la columna 1 no del 0.
                loan.setId(rs.getInt(1));
                loan.setControlNumber(rs.getInt(2));
                loan.setISBN(rs.getLong(3));
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));
                loan.setLoanDate(LoanDate);
                
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                loan.setReturnDate(ReturnDate);

                // sql.date to Calendar
                lastUpdate.setTime(rs.getDate(6));
                loan.setLastUpdate(lastUpdate);
             
                loan.setDelivered(rs.getBoolean(7));
                loan.setRenovations(rs.getShort(8));
                loan.setPenaltyFee(rs.getDouble(9));
                
                loan.setAutorize(rs.getInt(10));
                loan.setSender(rs.getInt(11));
                datos.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Error searchByColumn: "+e.getMessage());
        }
        return datos;
    }
}
