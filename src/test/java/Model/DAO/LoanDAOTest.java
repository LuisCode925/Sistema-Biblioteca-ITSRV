/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import java.util.List;

import Model.Loan;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Luis
 */
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
    public void updatePenaltyFee(){
        assertTrue(LoanDAO.updatePenaltyFee());
    }

    @Test
    public void getInfo_WithValidParams(){
        // Para indicar que es un Long se pone L al final
        Long ISBN = 9789702611905L;
        loan = loanDAO.getInfo(ISBN, 18220002);
        assertEquals(11, loan.getId());
    }
    
    @Test
    public void getInfo_WithInvalidParams(){
        assertNull(loanDAO.getInfo(97013704053L, 182205402));
    }

    @Test
    public void getAllLoans_Without_Date_Range(){
        List<Object[]> loans = loanDAO.getAllLoans();
        assertEquals( 1, loans.size());
    }

    @Test
    public void getAllLoans_With_Date_Range(){
        Calendar start = new GregorianCalendar(2015,2,31);// No importa la fecha con que sea menor a la del registro
        Calendar end = Calendar.getInstance();// Tendra el dia de hoy (Cuando se ejecute el test)
        List<Object[]> loans = loanDAO.getAllLoans(start, end);
        assertEquals(1, loans.size());
    }

    @Test
    public void getAllLoans_With_Date_OutOfRange(){
        Calendar start = new GregorianCalendar(2015,2,31);
        Calendar end = new GregorianCalendar(2015,6,13);
        List<Object[]> loans = loanDAO.getAllLoans(start, end);
        assertEquals(0, loans.size());
    }

    @Test
    public void getLoansAlreadyDelivered(){
        List<Object[]> loans = loanDAO.getLoansByDelivery(true);
        assertEquals(0, loans.size());
    }

    @Test
    public void getLoansPendingToDeliver(){
        List<Object[]> loans = loanDAO.getLoansByDelivery(false);
        assertEquals(1, loans.size());
    }
    
    @Test
    public void getLoans_Delivered_With_Date_Range(){
        Calendar start = new GregorianCalendar(2015,2,31);// No importa la fecha con que sea menor a la del registro
        Calendar end = Calendar.getInstance();// Tendra el dia de hoy (Cuando se ejecute el test)
        List<Object[]> loans = loanDAO.getLoansByDelivery(true, start, end);
        assertEquals(0, loans.size());
    }
    
    @Test
    public void getLoans_ToDeliver_With_Date_Range(){
        Calendar start = new GregorianCalendar(2015,2,31);// No importa la fecha con que sea menor a la del registro
        Calendar end = Calendar.getInstance();// Tendra el dia de hoy (Cuando se ejecute el test)
        List<Object[]> loans = loanDAO.getLoansByDelivery(false, start, end);
        assertEquals(1, loans.size());
    }
    
    @Test
    public void getLoans_Delivered_With_Date_OutOfRange(){
        Calendar start = new GregorianCalendar(2015,2,31);
        Calendar end = new GregorianCalendar(2015,3,12);
        List<Object[]> loans = loanDAO.getLoansByDelivery(true, start, end);
        assertEquals(0, loans.size());
    }
    
    @Test
    public void getLoans_ToDeliver_With_Date_OutOfRange(){
        Calendar start = new GregorianCalendar(2015,2,31);
        Calendar end = new GregorianCalendar(2015,3,12);
        List<Object[]> loans = loanDAO.getLoansByDelivery(false, start, end);
        assertEquals(0, loans.size());
    }
    
    @Test
    public void bookCheckOut(){
        loan = new Loan(0, 18220002, 9789702611905L, start, end, true, Short.MIN_VALUE, 0, 1, 0);
        assertTrue(loanDAO.bookCheckOut(loan));
    }
    
    @Test
    public void getLoansToFinalize_Today(){
        List<Loan> loans = loanDAO.getLoansToFinalize();
        assertEquals(1, loans.size());
    }
    
    // Test para borrar el prestamo
    @Test
    public void deleteLoan(){
        loan = loanDAO.getInfo(9789702611905L, 18220002);
        
        /*Gson gson = new Gson();
        String JSON = gson.toJson(loan);
        System.out.println(JSON);*/
        
        assertTrue(loanDAO.deleteLoan(loan));
        //assertTrue(loan!=null);
    }
    
    @Test
    public void getLoansExpired(){
        List<Loan> loans = loanDAO.getLoansExpired();
        assertEquals(1, loans.size());
    }
    
    @Test
    public void getUndelivered(){
        assertEquals(1, loanDAO.getUndelivered(18220002).size());
    }
    
    @Test
    public void getActiveLoans(){
        assertEquals(1, loanDAO.getActiveLoans(9701704053L).size());
    }
}
