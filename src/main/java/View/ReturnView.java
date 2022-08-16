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
public class ReturnView extends javax.swing.JPanel {

    /**
     * Creates new form ReturnView
     */
    public ReturnView() {
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

        bgReturn = new javax.swing.JPanel();
        addReturn = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblControlNumber = new javax.swing.JLabel();
        txtControlNumber = new javax.swing.JTextField();
        lblISBN = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cboxBookISBN = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        lblReturnDate = new javax.swing.JLabel();
        btnRenovate = new javax.swing.JButton();
        btnFinalize = new javax.swing.JButton();
        lblPenalty = new javax.swing.JLabel();
        txtPenalty = new javax.swing.JTextField();
        lblRenovations = new javax.swing.JLabel();
        txtReturnDate = new com.toedter.calendar.JDateChooser();
        txtRenovations = new javax.swing.JTextField();
        btnClean = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        lbLibrarianSender = new javax.swing.JLabel();
        lblLibrarianId = new javax.swing.JLabel();
        infoReturn = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        LoansToFinalize = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        loansExpired = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 750));
        setMinimumSize(new java.awt.Dimension(1000, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgReturn.setBackground(new java.awt.Color(0, 0, 0));
        bgReturn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addReturn.setBackground(new java.awt.Color(204, 204, 204));
        addReturn.setMaximumSize(new java.awt.Dimension(500, 750));
        addReturn.setMinimumSize(new java.awt.Dimension(500, 750));
        addReturn.setPreferredSize(new java.awt.Dimension(500, 750));
        addReturn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel1.setText("Realizar Entrega / Ampliar fecha");
        addReturn.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        lblControlNumber.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblControlNumber.setText("Numero de Control:");
        addReturn.add(lblControlNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        txtControlNumber.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtControlNumber.setMaximumSize(new java.awt.Dimension(59, 30));
        txtControlNumber.setMinimumSize(new java.awt.Dimension(59, 30));
        txtControlNumber.setPreferredSize(new java.awt.Dimension(59, 30));
        addReturn.add(txtControlNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 420, -1));

        lblISBN.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblISBN.setText("Identificador del libro (ISBN):");
        addReturn.add(lblISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));
        addReturn.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 360, 10));

        cboxBookISBN.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        cboxBookISBN.setMaximumSize(new java.awt.Dimension(28, 30));
        cboxBookISBN.setMinimumSize(new java.awt.Dimension(28, 30));
        cboxBookISBN.setPreferredSize(new java.awt.Dimension(28, 30));
        addReturn.add(cboxBookISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 420, -1));
        addReturn.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 420, 10));

        lblReturnDate.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblReturnDate.setText("Fecha Devolucion:");
        addReturn.add(lblReturnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        btnRenovate.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnRenovate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sincronizar.png"))); // NOI18N
        btnRenovate.setText("Renovar");
        btnRenovate.setContentAreaFilled(false);
        btnRenovate.setDefaultCapable(false);
        btnRenovate.setFocusable(false);
        btnRenovate.setIconTextGap(12);
        addReturn.add(btnRenovate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 540, -1, -1));

        btnFinalize.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnFinalize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/finish-flag.png"))); // NOI18N
        btnFinalize.setText("Finalizar");
        btnFinalize.setContentAreaFilled(false);
        btnFinalize.setFocusable(false);
        btnFinalize.setIconTextGap(12);
        addReturn.add(btnFinalize, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 540, -1, -1));

        lblPenalty.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblPenalty.setText("Multas por Atraso:");
        addReturn.add(lblPenalty, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, -1, -1));

        txtPenalty.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtPenalty.setPreferredSize(new java.awt.Dimension(59, 30));
        addReturn.add(txtPenalty, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 240, -1));

        lblRenovations.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblRenovations.setText("Numero de Renovaciones:");
        addReturn.add(lblRenovations, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        txtReturnDate.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtReturnDate.setPreferredSize(new java.awt.Dimension(90, 30));
        addReturn.add(txtReturnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, 180, -1));

        txtRenovations.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtRenovations.setMaximumSize(new java.awt.Dimension(6, 30));
        txtRenovations.setMinimumSize(new java.awt.Dimension(6, 30));
        txtRenovations.setPreferredSize(new java.awt.Dimension(6, 30));
        addReturn.add(txtRenovations, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 180, -1));

        btnClean.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clean.png"))); // NOI18N
        btnClean.setText("Limpiar");
        btnClean.setContentAreaFilled(false);
        btnClean.setFocusable(false);
        btnClean.setIconTextGap(12);
        addReturn.add(btnClean, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, -1, -1));
        addReturn.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 442, 420, 10));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel4.setText("Autorizo:");
        addReturn.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, -1, -1));

        lbLibrarianSender.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lbLibrarianSender.setText("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        addReturn.add(lbLibrarianSender, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, -1, -1));
        addReturn.add(lblLibrarianId, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 500, -1, -1));

        bgReturn.add(addReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        infoReturn.setMaximumSize(new java.awt.Dimension(500, 750));
        infoReturn.setMinimumSize(new java.awt.Dimension(500, 750));
        infoReturn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel2.setText("Entregas que vencen hoy");
        infoReturn.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));
        infoReturn.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 430, 10));

        LoansToFinalize.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ISBN", "No. Control"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Long.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(LoansToFinalize);

        infoReturn.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 430, 240));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel3.setText("Entregas que vencieron");
        infoReturn.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        loansExpired.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ISBN", "No. Control"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Long.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(loansExpired);

        infoReturn.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 437, 430, 280));
        infoReturn.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 422, 430, 10));

        bgReturn.add(infoReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 500, 750));

        add(bgReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 750));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable LoansToFinalize;
    public javax.swing.JPanel addReturn;
    private javax.swing.JPanel bgReturn;
    public javax.swing.JButton btnClean;
    public javax.swing.JButton btnFinalize;
    public javax.swing.JButton btnRenovate;
    public javax.swing.JComboBox<String> cboxBookISBN;
    public javax.swing.JPanel infoReturn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    public javax.swing.JLabel lbLibrarianSender;
    private javax.swing.JLabel lblControlNumber;
    private javax.swing.JLabel lblISBN;
    public javax.swing.JLabel lblLibrarianId;
    private javax.swing.JLabel lblPenalty;
    private javax.swing.JLabel lblRenovations;
    public javax.swing.JLabel lblReturnDate;
    public javax.swing.JTable loansExpired;
    public javax.swing.JTextField txtControlNumber;
    public javax.swing.JTextField txtPenalty;
    public javax.swing.JTextField txtRenovations;
    public com.toedter.calendar.JDateChooser txtReturnDate;
    // End of variables declaration//GEN-END:variables
}