/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import Model.Loan;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;

/**
 *
 * @author Luis
 */
public class LoanDAOTest {
    
    public LoanDAO loanDAO = new LoanDAO();
    
    @Test
    public void updatePenaltyFee(){
        assertTrue(LoanDAO.updatePenaltyFee());
    }

    @Test
    public void getLoanObject(){
        int ControlNumber = 18220002;
        Long ISBN = 9701704053L; // Para indicar que es un Long
        Loan loan = loanDAO.getLoan(ISBN, ControlNumber);
        assertEquals(6, loan.getId());
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
}
