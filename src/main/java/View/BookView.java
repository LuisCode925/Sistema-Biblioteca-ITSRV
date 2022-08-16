/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.ComboItems;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Luis
 */
public class BookView extends javax.swing.JPanel {

    /**
     * Creates new form BookView
     */
    public BookView() {
        initComponents();
        addItemsSearch();
    }
    
    private void addItemsSearch(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        model.addElement(new ComboItems( 1, "TODO", ""));
        model.addElement(new ComboItems( 2,"ISBN","ISBN"));
        model.addElement(new ComboItems( 3,"Titulo", "Title"));
        model.addElement(new ComboItems( 4,"Autor", "Author"));
        model.addElement(new ComboItems( 5,"Editorial", "Editorial"));
        model.addElement(new ComboItems( 6,"Edicion", "Edition"));
        model.addElement(new ComboItems( 7,"Año Publicacion", "YearPublication"));
        model.addElement(new ComboItems( 8,"Estante", "Shelf"));
        model.addElement(new ComboItems( 9,"Idioma","Language"));
        model.addElement(new ComboItems( 10,"Sección", "Section"));
        model.addElement(new ComboItems( 11,"Departamento","Department"));
        model.addElement(new ComboItems( 12,"Categoria", "Category"));

        this.btnSearchFilter.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgBook = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        lblSearchFor = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSearchFilter = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBooks = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 750));
        setMinimumSize(new java.awt.Dimension(1000, 750));
        setPreferredSize(new java.awt.Dimension(1000, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgBook.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.setText("Editar");
        bgBook.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 710, -1, -1));

        lblTitle.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblTitle.setText("Libros");
        bgBook.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        btnAdd.setText("Nuevo");
        bgBook.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 710, -1, -1));

        lblSearchFor.setText("Buscar por ");
        bgBook.add(lblSearchFor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        btnSearch.setText("Buscar");
        bgBook.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, -1, -1));
        bgBook.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 670, -1));

        bgBook.add(btnSearchFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

        tableBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Titulo", "Autor", "Editorial", "Edicion", "Año Publicacion", "Estante", "Sección", "Departamento", "Categoria", "Idioma", "Stock", "Disponibles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableBooks);

        bgBook.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 980, 580));

        btnDelete.setText("Eliminar");
        bgBook.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 710, -1, -1));

        add(bgBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 750));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel bgBook;
    public javax.swing.JButton btnAdd;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnEdit;
    public javax.swing.JButton btnSearch;
    public javax.swing.JComboBox<String> btnSearchFilter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearchFor;
    private javax.swing.JLabel lblTitle;
    public javax.swing.JTable tableBooks;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
