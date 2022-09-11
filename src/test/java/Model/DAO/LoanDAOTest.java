/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import java.util.List;

import Model.Loan;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoanDAOTest {
    
    public LoanDAO loanDAO;
    public Loan loan;
    public Calendar start;
    public Calendar end;
    
    @BeforeEach
    public void before(){
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        loanDAO = new LoanDAO();
        loan = new Loan(0, 0, Long.MIN_VALUE, start, end, false, Short.MIN_VALUE, 0, 0, 0);
    }

    @Test
    @Order(1)
    public void updatePenaltyFee() throws SQLException {;
        assertTrue(LoanDAO.updatePenaltyFee(5));
    }

    @Test
    @Order(2)
    public void getInfo_WithValidParams() throws SQLException{
        // Para indicar que es un Long se pone L al final
        loan = loanDAO.getInfo(9701704053L, 18220002);
        assertEquals(13, loan.getId());
    }
    
    @Test
    @Order(3)
    public void getInfo_WithInvalidParams() throws SQLException{
        assertNull(loanDAO.getInfo(97013704053L, 182205402));
    }

    @Test
    @Order(4)
    public void getAllLoans_Without_Date_Range(){
        List<Object[]> loans = loanDAO.getAllLoans();
        assertEquals( 2, loans.size());
    }

    @Test
    @Order(5)
    public void getAllLoans_With_Date_Range(){
        Calendar start = new GregorianCalendar(2015,2,31);// No importa la fecha con que sea menor a la del registro
        Calendar end = new GregorianCalendar(2022,6,5);
        List<Object[]> loans = loanDAO.getAllLoans(start, end);
        assertEquals(1, loans.size());
    }

    @Test
    @Order(6)
    public void getAllLoans_With_Date_OutOfRange(){
        Calendar start = new GregorianCalendar(2015,2,31);
        Calendar end = new GregorianCalendar(2015,6,13);
        List<Object[]> loans = loanDAO.getAllLoans(start, end);
        assertEquals(0, loans.size());
    }

    @Test
    @Order(7)
    public void getLoansAlreadyDelivered(){
        List<Object[]> loans = loanDAO.getLoansByDelivery(true);
        assertEquals(0, loans.size());
    }

    @Test
    @Order(8)
    public void getLoansPendingToDeliver(){
        List<Object[]> loans = loanDAO.getLoansByDelivery(false);
        assertEquals(2, loans.size());
    }
    
    @Test
    @Order(9)
    public void getLoans_Delivered_With_Date_Range(){
        Calendar start = new GregorianCalendar(2015,2,31);// No importa la fecha con que sea menor a la del registro
        Calendar end = Calendar.getInstance();// Tendra el dia de hoy (Cuando se ejecute el test)
        List<Object[]> loans = loanDAO.getLoansByDelivery(true, start, end);
        assertEquals(0, loans.size());
    }
    
    @Test
    @Order(10)
    public void getLoans_ToDeliver_With_Date_Range(){
        Calendar start = new GregorianCalendar(2015,2,31);// No importa la fecha con que sea menor a la del registro
        Calendar end = Calendar.getInstance();// Tendra el dia de hoy (Cuando se ejecute el test)
        List<Object[]> loans = loanDAO.getLoansByDelivery(false, start, end);
        assertEquals(2, loans.size());
    }
    
    @Test
    @Order(11)
    public void getLoans_Delivered_With_Date_OutOfRange(){
        Calendar start = new GregorianCalendar(2015,2,31);
        Calendar end = new GregorianCalendar(2015,3,12);
        List<Object[]> loans = loanDAO.getLoansByDelivery(true, start, end);
        assertEquals(0, loans.size());
    }
    
    @Test
    @Order(12)
    public void getLoans_ToDeliver_With_Date_OutOfRange(){
        Calendar start = new GregorianCalendar(2015,2,31);
        Calendar end = new GregorianCalendar(2015,3,12);
        List<Object[]> loans = loanDAO.getLoansByDelivery(false, start, end);
        assertEquals(0, loans.size());
    }
    
    @Test
    @Order(13)
    public void bookCheckOut(){
        loan = new Loan(0, 18220002, 9789702611905L, start, end, true, Short.MIN_VALUE, 0, 1, 0);
        assertTrue(loanDAO.bookCheckOut(loan));
    }
    
    @Test
    @Order(14)
    public void getLoansToFinalize_Today(){
        List<Loan> loans = loanDAO.getLoansToFinalize();
        assertEquals(1, loans.size());
    }
    
    @Test
    @Order(15)
    public void deleteLoan() throws SQLException{
        loan = loanDAO.getInfo(9789702611905L, 18220002);
        assertTrue(loanDAO.deleteLoan(loan));
    }
    
    @Test
    @Order(16)
    public void getLoansExpired(){
        List<Loan> loans = loanDAO.getLoansExpired();
        assertEquals(2, loans.size());
    }
    
    @Test
    @Order(17)
    public void getUndelivered(){
        assertEquals(1, loanDAO.getUndelivered(18220002).size());
    }
    
    @Test
    @Order(18)
    public void getActiveLoans(){
        assertEquals(1, loanDAO.getActiveLoans(9701704053L).size());
    }
    
    @Test
    public void getBestUsers(){
        assertEquals( 3, loanDAO.getBestUsers().size());
    }
}
