/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Calendar;

/**
 *
 * @author Luis
 */
public class Loan{
    
    private int Id;
    private int ControlNumber;
    private Long ISBN;
    private Calendar LoanDate;
    private Calendar ReturnDate;
    private boolean Delivered;
    private short Renovations;
    private double PenaltyFee;
    private int Autorize;
    private int Receiver;
    
    public Loan(int Id, int ControlNumber, Long ISBM, Calendar LoanDate, Calendar ReturnDate, boolean Delivered, short Renovations, double PenaltyFee, int Autorize, int Receiver) {
        this.Id = Id;
        this.ControlNumber = ControlNumber;
        this.ISBN = ISBM;
        this.LoanDate = LoanDate;
        this.ReturnDate = ReturnDate;
        this.Delivered = Delivered;
        this.Renovations = Renovations;
        this.PenaltyFee = PenaltyFee;
        this.Autorize = Autorize;
        this.Receiver = Receiver;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getControlNumber() {
        return ControlNumber;
    }

    public void setControlNumber(int ControlNumber) {
        this.ControlNumber = ControlNumber;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public Calendar getLoanDate() {
        return LoanDate;
    }

    public void setLoanDate(Calendar LoanDate) {
        this.LoanDate = LoanDate;
    }

    public Calendar getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Calendar ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    public boolean isDelivered() {
        return Delivered;
    }

    public void setDelivered(boolean Delivered) {
        this.Delivered = Delivered;
    }

    public short getRenovations() {
        return Renovations;
    }

    public void setRenovations(short Renovations) {
        this.Renovations = Renovations;
    }

    public double getPenaltyFee() {
        return PenaltyFee;
    }

    public void setPenaltyFee(double PenaltyFee) {
        this.PenaltyFee = PenaltyFee;
    }

    public int getAutorize() {
        return Autorize;
    }

    public void setAutorize(int Autorize) {
        this.Autorize = Autorize;
    }

    public int getReceiver() {
        return Receiver;
    }

    public void setReceiver(int Receiver) {
        this.Receiver = Receiver;
    }
  
}
