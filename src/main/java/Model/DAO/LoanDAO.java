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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis
 */
public class LoanDAO extends ConnectionDB {
    
    /**
     * Function to call to UPDATE_PENALTY_FEE and update the value of the field
     * @return true if the call to the event in mysql server finished succesfull
     */
    public static boolean updatePenaltyFee(){
        PreparedStatement ps = null;
        ConnectionDB con = new ConnectionDB();
        String SQL = "CALL updatePenaltyFee()";
        
        try { 
            ps = con.getConnection().prepareStatement(SQL);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al updatePenaltyFee: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * @param ISBN the unique identifier that ever book have 
     * @param ControlNumber the ID for the users students and profesors
     * @return a loan object that mathces with the params 
     */
    public Loan getInfo(Long ISBN, int ControlNumber){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        Calendar LoanDate = Calendar.getInstance();
        Calendar ReturnDate = Calendar.getInstance();
        
        Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate, false, Short.MIN_VALUE, 0, 0, 0);
        
        //String sql = "SELECT * FROM loans WHERE ISBN=?";
        String sql = "SELECT * FROM loans WHERE (ControlNumber=?) AND (ISBN=?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ControlNumber);
            ps.setLong(2, ISBN);
            rs = ps.executeQuery();
               
            if (rs.next()) {
                  
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
                
                loan.setDelivered(rs.getBoolean(6));
                loan.setRenovations(rs.getShort(7));
                loan.setPenaltyFee(rs.getDouble(8));
                
                loan.setAutorize(rs.getInt(9));
                loan.setReceiver(rs.getInt(10));
              
            }
        } catch (SQLException e) {
            loan = null;
            System.out.println("Error getLoan: " + e.getMessage());
        }
        return loan;
    }
    
    /**
     * This function joins the loans table with the librarian table
     * @return a list of loans with the names of the autorizer and Receiver
     */
    public List<Object[]> getAllLoans(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> datos = new ArrayList<>();
        String sql = "SELECT loans.Id, loans.ControlNumber, loans.ISBN, loans.LoanDate, loans.ReturnDate, loans.Delivered, loans.Renovations, loans.PenaltyFee, "
                + "CONCAT(librarians.Names, \" \", librarians.LastNames) as Autorize, CONCAT(librarians.Names, \" \", librarians.LastNames) as Receiver "
                + "FROM loans INNER JOIN librarians ON loans.Autorize=librarians.Id";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                
                Object[] loanObj = new Object[10];
                
                // Se comineza desde la columna 1 no del 0.
                loanObj[0] = rs.getInt(1); // Id
                loanObj[1] = rs.getInt(2); // Numero de control
                loanObj[2] = rs.getLong(3); // ISBN
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));   
                SimpleDateFormat LDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[3] = LDF.format(LoanDate.getTime());
             
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                SimpleDateFormat RDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[4] = RDF.format(ReturnDate.getTime());
                
                loanObj[5] = rs.getBoolean(6);
                loanObj[6] = rs.getShort(7);
                loanObj[7] = rs.getDouble(8);
                
                loanObj[8] = rs.getString(9);
                loanObj[9] = rs.getString(10);
              
                datos.add(loanObj);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllLoans: " + e.getMessage());
        }
        return datos;
    }
    
    /**
     * 
     * @param start this Calendar Object define the start of the date
     * @param end this Calendar Object define the end of the date
     * @return a list of Loans these match the established range.
     */
    public List<Object[]> getAllLoans(Calendar start,Calendar end){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> datos = new ArrayList<>();
        String sql = "SELECT loans.Id, loans.ControlNumber, loans.ISBN, loans.LoanDate, loans.ReturnDate, loans.Delivered, loans.Renovations, loans.PenaltyFee, "
                + "CONCAT(librarians.Names, \" \", librarians.LastNames) as Autorize, CONCAT(librarians.Names, \" \", librarians.LastNames) as Sender "
                + "FROM loans INNER JOIN librarians ON loans.Autorize=librarians.Id"
                + " WHERE (loans.LoanDate BETWEEN ? AND ?)";
        
        try {
            ps = con.prepareStatement(sql);       
            ps.setDate(1, new java.sql.Date(start.getTimeInMillis()));
            ps.setDate(2, new java.sql.Date(end.getTimeInMillis()));
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                
                Object[] loanObj = new Object[10];
                
                // Se comineza desde la columna 1 no del 0.
                loanObj[0] = rs.getInt(1); // Id
                loanObj[1] = rs.getInt(2); // Numero de control
                loanObj[2] = rs.getLong(3); // ISBN
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));   
                SimpleDateFormat LDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[3] = LDF.format(LoanDate.getTime());
             
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                SimpleDateFormat RDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[4] = RDF.format(ReturnDate.getTime());
                
                loanObj[5] = rs.getBoolean(6);
                loanObj[6] = rs.getShort(7);
                loanObj[7] = rs.getDouble(8);
                
                loanObj[8] = rs.getString(9);
                loanObj[9] = rs.getString(10);
              
                datos.add(loanObj);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllLoans: " + e.getMessage());
        }
        return datos;
    }
    
    /**
     * 
     * @param delivery will filter the search acording fi the loan is delivered or pending
     * @return a list of Loans these match this boolean.
     */
    public List<Object[]> getLoansByDelivery(boolean delivery){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> datos = new ArrayList<>();
        String sql = "SELECT loans.Id, loans.ControlNumber, loans.ISBN, loans.LoanDate, loans.ReturnDate, loans.Delivered, loans.Renovations, loans.PenaltyFee, "
                    + "CONCAT(librarians.Names, \" \", librarians.LastNames) AS Autorize, CONCAT(librarians.Names, \" \", librarians.LastNames) " 
                    + "FROM loans loans INNER JOIN librarians ON loans.Autorize=librarians.Id " 
                    +"WHERE (loans.Delivered=?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, delivery);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                
                Object[] loanObj = new Object[10];
                
                // Se comineza desde la columna 1 no del 0.
                loanObj[0] = rs.getInt(1); // Id
                loanObj[1] = rs.getInt(2); // Numero de control
                loanObj[2] = rs.getLong(3); // ISBN
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));   
                SimpleDateFormat LDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[3] = LDF.format(LoanDate.getTime());
             
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                SimpleDateFormat RDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[4] = RDF.format(ReturnDate.getTime());
                
                loanObj[5] = rs.getBoolean(6);
                loanObj[6] = rs.getShort(7);
                loanObj[7] = rs.getDouble(8);
                
                loanObj[8] = rs.getString(9);
                loanObj[9] = rs.getString(10);
              
                datos.add(loanObj);
            }
        } catch (SQLException e) {
            System.out.println("Error getLoansByDelivery: " + e.getMessage());
        }
        return datos;
    }
    
    /**
     * 
     * @param delivery filter for delivered or pending
     * @param start this Calendar Object define the start of the date
     * @param end this Calendar Object define the end of the date
     * @return a list of Loans these match the established range.
     */
    public List<Object[]> getLoansByDelivery(boolean delivery, Calendar start, Calendar end){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> datos = new ArrayList<>();
        String sql = "SELECT loans.Id, loans.ControlNumber, loans.ISBN, loans.LoanDate, loans.ReturnDate, loans.Delivered, loans.Renovations, loans.PenaltyFee, "
                    + "CONCAT(librarians.Names, \" \", librarians.LastNames) AS Autorize, CONCAT(librarians.Names, \" \", librarians.LastNames) " 
                    + "FROM loans loans INNER JOIN librarians ON loans.Autorize=librarians.Id " 
                    + "WHERE (loans.Delivered=?) "
                    + "AND (loans.LoanDate BETWEEN ? AND ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, delivery);       
            ps.setDate(2, new java.sql.Date(start.getTimeInMillis()));
            ps.setDate(3, new java.sql.Date(end.getTimeInMillis()));
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Calendar LoanDate = Calendar.getInstance();
                Calendar ReturnDate = Calendar.getInstance();
                
                Object[] loanObj = new Object[10];
                
                // Se comineza desde la columna 1 no del 0.
                loanObj[0] = rs.getInt(1); // Id
                loanObj[1] = rs.getInt(2); // Numero de control
                loanObj[2] = rs.getLong(3); // ISBN
                
                // sql.date to Calendar
                LoanDate.setTime(rs.getDate(4));   
                SimpleDateFormat LDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[3] = LDF.format(LoanDate.getTime());
             
                // sql.date to Calendar
                ReturnDate.setTime(rs.getDate(5));
                SimpleDateFormat RDF = new SimpleDateFormat("dd/MM/yyyy");
                loanObj[4] = RDF.format(ReturnDate.getTime());
                
                loanObj[5] = rs.getBoolean(6);
                loanObj[6] = rs.getShort(7);
                loanObj[7] = rs.getDouble(8);
                
                loanObj[8] = rs.getString(9);
                loanObj[9] = rs.getString(10);
              
                datos.add(loanObj);
            }
        } catch (SQLException e) {
            System.out.println("Error getLoansByDelivery: " + e.getMessage());
        }
        return datos;
    }
    
    /**
     * Using the CURRENT_DATE() (today)
     * @return a list of Loans these match in the field ReturnDate
     */
    public List<Loan> getLoansToFinalize(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Loan> datos = new ArrayList<>();
        String sql = "SELECT Id, ISBN, ControlNumber FROM loans WHERE (ReturnDate = CURRENT_DATE()) AND (Delivered=false)";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, null, null, false, Short.MIN_VALUE, 0, 0, 0);
      
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
    }
    
    /**
     * Using the field ReturnDate compare if already expired
     * @return a list of Loans with a Penalty
     */
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
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, null, null, false, Short.MIN_VALUE, 0, 0, 0);
                
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
    }
    
    //==========================================================================
    
    public List<Object[]> getTopBooks(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> books = new ArrayList<>();
        String sql = "SELECT ISBN, COUNT(ISBN) FROM loans WHERE Delivered=true GROUP BY ISBN";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Object[] topBook = new Object[2];
                
                topBook[0] = rs.getLong(1); // Obtiene el ISBN
                topBook[1] = rs.getInt(2); // Numero de veces que fue solicitado
               
                books.add(topBook);
            }
        } catch (SQLException e) {
            System.out.println("Error getTopBooks: " + e.getMessage());
        }
        return books;
    }
    
    public List<Object[]> getMoreActiveCareers(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> books = new ArrayList<>();
        String sql = "SELECT collegecareers.Carrer, COUNT(SUBSTRING(ControlNumber, 3, 3)) AS no_loans "
                + "FROM loans "
                + "INNER JOIN `collegecareers` ON SUBSTRING(ControlNumber, 3, 3)=collegecareers.Code "
                + "WHERE Delivered=true GROUP BY collegecareers.Carrer";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Object[] topBook = new Object[2];
                
                topBook[0] = rs.getString(1); // Obtiene el nombre de la carrera en cuestion 
                topBook[1] = rs.getInt(2); // Contador de los prestamos
               
                books.add(topBook);
            }
        } catch (SQLException e) {
            System.out.println("Error getMoreActiveCareers: " + e.getMessage());
        }
        return books;
    }
    
    public List<Object[]> getMostPenalizedUsers(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> books = new ArrayList<>();
        String sql = "SELECT ControlNumber, COUNT(ISBN) AS 'Prestamos Activos', SUM(PenaltyFee) AS 'Total Multas 'FROM `loans` GROUP BY ControlNumber";
        // TODO falta el where entrega este en falso y el limite entre fechas
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Object[] topBook = new Object[2];
                
                topBook[0] = rs.getInt(1); // Obtiene el numero de control del usuario
                topBook[1] = rs.getInt(2); // Contador de los prestamos
                topBook[1] = rs.getDouble(3); // Acumulador de la multa
               
                books.add(topBook);
            }
        } catch (SQLException e) {
            System.out.println("Error getMostPenalizedUsers: " + e.getMessage());
        }
        return books;
    }
    
    public List<Object[]> getBestUsers(){
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        
        List<Object[]> books = new ArrayList<>();
        String sql = "SELECT loans.ControlNumber, collegecareers.Carrer AS 'Carrera', "
                + "CONCAT(users.Names, \" \",users.LastNames) AS 'Nombre Completo', "
                + "COUNT(ISBN) 'Total Prestamos', SUM(PenaltyFee) AS 'Total de multas' "
                + "FROM `loans` "
                + "INNER JOIN collegecareers ON SUBSTRING(ControlNumber, 3, 3)=collegecareers.Code "
                + "INNER JOIN users ON loans.ControlNumber=users.ControlNumber;";
        // TODO order by 
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Object[] topUser = new Object[5];
                
                topUser[0] = rs.getInt(1); // No. Control
                topUser[1] = rs.getString(2); // Carrera
                topUser[2] = rs.getString(3); // Nombre Completo
                topUser[3] = rs.getInt(4); // Total Prestamos
                topUser[4] = rs.getDouble(5); // Total Multas 
               
                books.add(topUser);
            }
        } catch (SQLException e) {
            System.out.println("Error getBestUsers: " + e.getMessage());
        }
        return books;
    }
    
    //==========================================================================
    
    /**
     * Userd to check if the user can get more loans
     * @param ControlNumber the identifier of the user
     * @return a list of the books without deliver (max 43 for students).
     */
    public List<Long> getUndelivered(int ControlNumber){
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
    }
    
    /**
     * Used like counter for each loan without deliver
     * @param ISBN the unique id for each book
     * @return a list of the users with a loan of the book.
     */
    public List<Integer> getActiveLoans(Long ISBN){
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
    }
    
    /**
     * Used to delete a registry in the database
     * @param loan object that contains the data (id)
     * @return true if the operatios is completed successful.
     */
    public boolean deleteLoan(Loan loan){
        PreparedStatement ps;
        Connection con = getConnection();
        String SQL = "DELETE FROM loans WHERE loans.Id=?";
        
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, loan.getId());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleteLoan: "+e.getMessage());
            return false;
        }
    }
    
    /**
     * Used to insert a registry in the database
     * @param loan object that contains the data
     * @return true if the operatios is completed successful.
     */
    public boolean bookCheckOut(Loan loan){
        PreparedStatement ps;
        Connection con = getConnection();
        
        String SQL = "INSERT INTO loans (Id, ControlNumber, ISBN, LoanDate, "
                + "ReturnDate, Delivered, Renovations, PenaltyFee, "
                + "Autorize, Receiver) VALUES (null , ?, ?, ?, ?, false, 0, 0, ?, null)";
        
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setInt(1, loan.getControlNumber());
            ps.setLong(2, loan.getISBN()); 
            
            // Calendar to sql.date
            java.sql.Date loanDate = new java.sql.Date(loan.getLoanDate().getTimeInMillis());
            ps.setDate(3, loanDate);
            
            // Calendar to sql.date
            java.sql.Date returnDate = new java.sql.Date(loan.getReturnDate().getTimeInMillis());
            ps.setDate(4, returnDate);
            
            ps.setInt(5, loan.getAutorize());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error registerLoan: "+e.getMessage());   
        }
        return false;
    }
    
    // Actualiza la informacion del prestamo
    public boolean updateUserInfo(Loan loan){
        PreparedStatement ps = null;
        Connection con = getConnection();
        
        String SQL = "UPDATE loans SET ControlNumber=?, ISBN=?, LoanDate=?, ReturnDate=?, Delivered=?, Renovations=?, PenaltyFee=?, Autorize=?, Receiver=? WHERE Id=?";
        
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
    public boolean finalizeLoan(Long ISBN, int ControlNumber, int Receiver){
        PreparedStatement ps = null;
        Connection con = getConnection();
        String SQL = "UPDATE loans SET Delivered=true, Receiver=? WHERE (ISBN=?) AND (ControlNumber=?)";
        try {
            ps = con.prepareStatement(SQL);
            
            ps.setInt(1, Receiver);
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
                
                Loan loan = new Loan(0, 0, Long.MIN_VALUE, LoanDate, ReturnDate,  false, Short.MIN_VALUE, 0, 0, 0);

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

                loan.setDelivered(rs.getBoolean(6));
                loan.setRenovations(rs.getShort(7));
                loan.setPenaltyFee(rs.getDouble(8));
                
                loan.setAutorize(rs.getInt(9));
                loan.setReceiver(rs.getInt(10));
                datos.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Error searchByColumn: "+e.getMessage());
        }
        return datos;
    }
}
