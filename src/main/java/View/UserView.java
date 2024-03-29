/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author Luis
 */
public class UserView extends javax.swing.JPanel {

    /**
     * Creates new form UserView
     */
    public UserView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UserPanel = new javax.swing.JPanel();
        btnUserDelete = new javax.swing.JButton();
        btnUserEdit = new javax.swing.JButton();
        btnUserAdd = new javax.swing.JButton();
        tableUsersScroll = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        btnUserSeach = new javax.swing.JButton();
        txtUserSearch = new javax.swing.JTextField();
        lblUserTitle = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 750));
        setMinimumSize(new java.awt.Dimension(1000, 750));

        UserPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUserDelete.setText("Eliminar");
        UserPanel.add(btnUserDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(896, 696, -1, -1));

        btnUserEdit.setText("Editar");
        UserPanel.add(btnUserEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(806, 696, -1, -1));

        btnUserAdd.setText("Nuevo");
        UserPanel.add(btnUserAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(716, 696, -1, -1));

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Control", "Nombres", "Apellidos", "Carrera", "Telefono", "Correo", "Dirección", "Baneado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableUsersScroll.setViewportView(tableUsers);
        if (tableUsers.getColumnModel().getColumnCount() > 0) {
            tableUsers.getColumnModel().getColumn(6).setPreferredWidth(0);
        }

        UserPanel.add(tableUsersScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 96, 943, 577));

        btnUserSeach.setText("Buscar");
        UserPanel.add(btnUserSeach, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, -1, -1));

        txtUserSearch.setMaximumSize(new java.awt.Dimension(6, 23));
        txtUserSearch.setMinimumSize(new java.awt.Dimension(6, 23));
        txtUserSearch.setPreferredSize(null);
        UserPanel.add(txtUserSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 746, -1));

        lblUserTitle.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblUserTitle.setText("Usuarios");
        UserPanel.add(lblUserTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 33, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel UserPanel;
    public javax.swing.JButton btnUserAdd;
    public javax.swing.JButton btnUserDelete;
    public javax.swing.JButton btnUserEdit;
    public javax.swing.JButton btnUserSeach;
    private javax.swing.JLabel lblUserTitle;
    public javax.swing.JTable tableUsers;
    public javax.swing.JScrollPane tableUsersScroll;
    public javax.swing.JTextField txtUserSearch;
    // End of variables declaration//GEN-END:variables
}
