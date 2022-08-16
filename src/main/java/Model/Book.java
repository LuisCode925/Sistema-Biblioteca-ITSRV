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
public class Book {
    
    // 13 propiedades
    private long ISBN;
    private String Title;
    private String Author;
    private String Editorial;
    private int Edition;
    private Calendar YearPublication;
    private int Shelf;
    private int Section;
    private int Department;
    private String Category;
    private String Language;
    private int Stock;
    private int BFree;

    public Book(long ISBN, String Title, String Author, String Editorial, int Edition, Calendar YearPublication, int Shelf, int Section, int Department, String Category, String Language, int Stock, int BFree) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Editorial = Editorial;
        this.Edition = Edition;
        this.YearPublication = YearPublication;
        this.Shelf = Shelf;
        this.Section = Section;
        this.Department = Department;
        this.Category = Category;
        this.Language = Language;
        this.Stock = Stock;
        this.BFree = BFree;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

    public int getEdition() {
        return Edition;
    }

    public void setEdition(int Edition) {
        this.Edition = Edition;
    }

    public Calendar getYearPublication() {
        return YearPublication;
    }

    public void setYearPublication(Calendar YearPublication) {
        this.YearPublication = YearPublication;
    }

    public int getShelf() {
        return Shelf;
    }

    public void setShelf(int Shelf) {
        this.Shelf = Shelf;
    }

    public int getSection() {
        return Section;
    }

    public void setSection(int Section) {
        this.Section = Section;
    }

    public int getDepartment() {
        return Department;
    }

    public void setDepartment(int Department) {
        this.Department = Department;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public int getBFree() {
        return BFree;
    }

    public void setBFree(int BFree) {
        this.BFree = BFree;
    }
    
}
