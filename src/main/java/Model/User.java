/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Luis
 */
public class User {
    
    // Numero de Propiedades: 8
    private int ControlNumber;
    private String Names;
    private String LastNames;
    private Long Phone;
    private String Email;
    private int CollegeCareer;
    private String Address;
    private boolean Banned;

    // Default values: (0, "", "", Long.MIN_VALUE, "", 0, "", false)
    public User(int ControlNumber, String Names, String LastNames, Long Phone, String Email, int CollegeCareer, String Address, boolean Banned) {
        this.ControlNumber = ControlNumber;
        this.Names = Names;
        this.LastNames = LastNames;
        this.Phone = Phone;
        this.Email = Email;
        this.CollegeCareer = CollegeCareer;
        this.Address = Address;
        this.Banned = Banned;
    }

    public int getControlNumber() {
        return ControlNumber;
    }

    public void setControlNumber(int ControlNumber) {
        this.ControlNumber = ControlNumber;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String Names) {
        this.Names = Names;
    }

    public String getLastNames() {
        return LastNames;
    }

    public void setLastNames(String LastNames) {
        this.LastNames = LastNames;
    }

    public Long getPhone() {
        return Phone;
    }

    public void setPhone(Long Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getCollegeCareer() {
        return CollegeCareer;
    }

    public void setCollegeCareer(int CollegeCareer) {
        this.CollegeCareer = CollegeCareer;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public boolean isBanned() {
        return Banned;
    }

    public void setBanned(boolean Banned) {
        this.Banned = Banned;
    }
    
}
