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
    private Calendar lastUpdate;
    private boolean Delivered;
    private short Renovations;
    private double PenaltyFee;
    private int Autorize;
    private int Sender;

    // 0, 0, Long.MIN_VALUE, new Date(), new Date(), Short.MIN_VALUE, 0, 0
    public Loan(int Id, int ControlNumber, Long ISBM, Calendar LoanDate, Calendar ReturnDate,Calendar lastUpdate, boolean Delivered, short Renovations, double PenaltyFee, int Autorize, int Sender) {
        this.Id = Id;
        this.ControlNumber = ControlNumber;
        this.ISBN = ISBM;
        this.LoanDate = LoanDate;
        this.ReturnDate = ReturnDate;
        this.lastUpdate = lastUpdate;
        this.Delivered = Delivered;
        this.Renovations = Renovations;
        this.PenaltyFee = PenaltyFee;
        this.Autorize = Autorize;
        this.Sender = Sender;
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

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public int getSender() {
        return Sender;
    }

    public void setSender(int Sender) {
        this.Sender = Sender;
    }
    
}
