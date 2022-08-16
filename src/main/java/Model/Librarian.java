/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Luis
 */
public class Librarian {
    
    private int Id;
    private String Names;
    private String LastNames;
    private String NickName;
    private String Email;
    private String Password;

    public Librarian(int Id, String Names, String LastNames, String NickName, String Email, String Password) {
        this.Id = Id;
        this.Names = Names;
        this.LastNames = LastNames;
        this.NickName = NickName;
        this.Email = Email;
        this.Password = Password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
        
}
