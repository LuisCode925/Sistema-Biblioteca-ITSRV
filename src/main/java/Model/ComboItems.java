/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Luis
 */
public class ComboItems {
    private int key;
    private String value;
    private String column;

    public ComboItems(int key, String value, String column) {
        this.key = key;
        this.value = value;
        this.column = column;
    }

    @Override
    public String toString() {
        return value;
    }
    
    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
    public String getColumn() {
        return column;
    }
    
}
