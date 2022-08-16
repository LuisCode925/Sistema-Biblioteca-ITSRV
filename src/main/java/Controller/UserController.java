/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.User;
import Model.DAO.UserDAO;
import View.AddEditUser;
import View.UserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis
 */
public class UserController implements ActionListener{
    
    private User user;
    private UserDAO userDAO;
    private UserView userView;
    private AddEditUser addEditUser;
    DefaultTableModel modelo = new DefaultTableModel();

    public UserController(User user, UserDAO userDAO, UserView userView, AddEditUser addEditUser) {
        this.user = user;
        this.userDAO = userDAO;
        this.userView = userView;
        this.addEditUser = addEditUser;
        
        // Todos los botones que deben tenener una interaccion
        this.userView.btnUserAdd.addActionListener(this);
        this.userView.btnUserDelete.addActionListener(this);
        this.userView.btnUserEdit.addActionListener(this);
        this.userView.btnUserSeach.addActionListener(this);
        
        // Acciones de la Vista Secundaria para Agregar y Editar informacion
        this.addEditUser.btnSave.addActionListener(this);
        this.addEditUser.btnCancel.addActionListener(this);
          
        // Instrucciones que deben seguirse al iniciar
        this.toList(this.userView.tableUsers);
        
        // Ocultacion de las columnas que no queremos mostrar
        this.userView.tableUsers.getColumnModel().getColumn(6).setMinWidth(0);
        this.userView.tableUsers.getColumnModel().getColumn(6).setMaxWidth(0);
        
    }
    
    // Muestra todos los usuarios que hay en la Base de Datos
    public void toList(JTable Table){
        modelo = (DefaultTableModel) Table.getModel();
        List<User> list_of_users = userDAO.getAllUsers();
        Map<Integer, String> careers = userDAO.getAllCollegeCareers();
        
        // Numero de Propiedades: 8
        Object[] userObject = new Object[8];
        for (int i = 0; i < list_of_users.size(); i++) {
            
            userObject[0] = list_of_users.get(i).getControlNumber();
            userObject[1] = list_of_users.get(i).getNames();
            userObject[2] = list_of_users.get(i).getLastNames();
            
            // Se obtiene en base a la lista de carreras Disponibles
            userObject[3] = careers.get(list_of_users.get(i).getCollegeCareer());
            
            userObject[4] = list_of_users.get(i).getPhone();
            userObject[5] = list_of_users.get(i).getEmail();
            
            userObject[6] = list_of_users.get(i).getAddress();
            userObject[7] = list_of_users.get(i).isBanned();
                        
            modelo.addRow(userObject);
        }
        userView.tableUsers.setModel(modelo);
    }
    
    public void getUsersFiltered(JTable TableUsers, String column, String word){ 
        modelo = (DefaultTableModel) TableUsers.getModel();
        List<User> userList = userDAO.searchByColumn(column, word);
        Map<Integer, String> careers = userDAO.getAllCollegeCareers();
        
        Object[] userObject = new Object[8];
        for (int i = 0; i < userList.size(); i++) {
            
            userObject[0] = userList.get(i).getControlNumber();
            userObject[1] = userList.get(i).getNames();
            userObject[2] = userList.get(i).getLastNames();
            
            userObject[3] = careers.get(userList.get(i).getCollegeCareer());
            userObject[4] = userList.get(i).getPhone();
            userObject[5] = userList.get(i).getEmail();
            
            userObject[6] = userList.get(i).getAddress();
            userObject[7] = userList.get(i).isBanned();
                        
            modelo.addRow(userObject);
        }
        userView.tableUsers.setModel(modelo);
    }
    
    // Limpia la tabla de todas las filas
    public void cleanTable(JTable Table) {
        int rows = Table.getRowCount();
        modelo = (DefaultTableModel) Table.getModel();
        for (int i = rows - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }
    
    // Cierra correctamente el JFrame secundario
    public void closeAddEdit(){
        addEditUser.setVisible(false);
        addEditUser.dispose();
    }
    
    // Obtiene los datos del JFrame de Agregar o Editar
    public void getUserForm(){
        user.setControlNumber(Integer.parseInt(addEditUser.txtControlNumber.getText()));
        user.setNames(addEditUser.txtNames.getText());
        user.setLastNames(addEditUser.txtLastNames.getText());
        user.setPhone(Long.parseLong(addEditUser.txtPhone.getText()));
        user.setEmail(addEditUser.txtEmail.getText());
        // Se agrega uno porque el index empieza desde cero
        user.setCollegeCareer(addEditUser.btnCollegeCareer.getSelectedIndex()+1); 
        user.setAddress(addEditUser.txtAddress.getText());
        user.setBanned(addEditUser.btnBanned.isSelected());
    }
     
    // Limpia el JFrame para Agregar nuevos elementos
    public void cleanUserForm(){
        this.addEditUser.txtControlNumber.setText(null);
        this.addEditUser.txtNames.setText(null);
        this.addEditUser.txtLastNames.setText(null);
        this.addEditUser.txtPhone.setText(null);
        this.addEditUser.txtEmail.setText(null);
        this.addEditUser.txtAddress.setText(null);
        this.addEditUser.btnBanned.setSelected(false);
    }
    
    // Carga las carreras disponibles en el combobox
    public void addItemsCollegeCareer(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.removeAllElements();
        Map<Integer, String> careers = userDAO.getAllCollegeCareers();      
        for (int i = 0; i < careers.size(); i++) {
            model.addElement(careers.get(i+1));
        }
        addEditUser.btnCollegeCareer.setModel(model);
    }
    
    public void loadEditionUserForm(int row, JTable TableUsers){
        modelo = (DefaultTableModel) TableUsers.getModel();
        
        user = userDAO.getUser((int) modelo.getValueAt(row, 0));
        
        addEditUser.txtControlNumber.setText(String.valueOf(user.getControlNumber()));
        addEditUser.txtNames.setText(user.getNames());
        addEditUser.txtLastNames.setText(user.getLastNames());
        addEditUser.btnCollegeCareer.setSelectedIndex(user.getCollegeCareer() - 1 );
        addEditUser.txtPhone.setText(String.valueOf(user.getPhone()));
        addEditUser.txtEmail.setText(user.getEmail());
        addEditUser.txtAddress.setText(user.getAddress());
        addEditUser.btnBanned.setSelected(user.isBanned());
    }
    
    // Comprueba que un String se pueda convertir a integer
    public static boolean isStringInteger(String stringToCheck, int radix) {
        Scanner sc = new Scanner(stringToCheck.trim());
        if(!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        //======================================================================
        if (e.getSource() == addEditUser.btnCancel) { closeAddEdit(); }
        //======================================================================
        
        //======================================================================
        if (e.getSource() == addEditUser.btnSave) {
            String operation = addEditUser.lblTitle.getText();
            this.getUserForm();
            
            // Para Agregar un nuevo usuario
            if (operation.equals("Agregar un nuevo usuario")) {
                if(userDAO.registerUser(user)){
                    this.closeAddEdit();
                    this.cleanTable(this.userView.tableUsers);
                    this.toList(this.userView.tableUsers);
                    JOptionPane.showMessageDialog(null, "Se ha agregado el usuario correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Hubo un problema al agregar el usuario.");
                }
            } else {
                // Para editar un libro existente
                if (operation.equals("Actualizar informacion del usuario")) {
                    if (userDAO.updateUserInfo(user)) {
                        this.closeAddEdit();
                        this.cleanTable(this.userView.tableUsers);
                        this.toList(this.userView.tableUsers);
                        JOptionPane.showMessageDialog(null, "Se ha actualizado la informacion del usuario");
                    } else {
                        JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar la informacion.");
                    }
                }
            }
        }
        //======================================================================
       
        if (e.getSource() == userView.btnUserAdd) {
            addEditUser.lblTitle.setText("Agregar un nuevo usuario");
            this.addItemsCollegeCareer();
            addEditUser.setVisible(true);
            addEditUser.btnBanned.setSelected(false);
            addEditUser.btnBanned.setVisible(false);
            addEditUser.txtControlNumber.setEditable(true);
            this.cleanUserForm(); // Limpiar el formulario
        }//=====================================================================
                
        if (e.getSource() == userView.btnUserEdit) {
            if (userView.tableUsers.getSelectedRow() >= 0) {
                addEditUser.lblTitle.setText("Actualizar informacion del usuario");
                this.addItemsCollegeCareer();
                addEditUser.setVisible(true);
                addEditUser.btnBanned.setVisible(true);
                addEditUser.txtControlNumber.setEditable(false);
                loadEditionUserForm(userView.tableUsers.getSelectedRow(), userView.tableUsers);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor si desea editar, seleccione una fila.");
            } 
        }
        
        if (e.getSource() == userView.btnUserSeach) { 
            if(!userView.txtUserSearch.getText().equals("")){
                try {
                    Integer.parseInt(userView.txtUserSearch.getText());
                    // Cuando se ingresa un numero de control
                    this.cleanTable(this.userView.tableUsers);
                    this.getUsersFiltered(this.userView.tableUsers, "ControlNumber", userView.txtUserSearch.getText());
                } catch (NumberFormatException n) {
                    // Cuando se busca un nombre
                    this.cleanTable(this.userView.tableUsers);
                    this.getUsersFiltered(this.userView.tableUsers, "Names", userView.txtUserSearch.getText());
                }
            } else {
                this.cleanTable(this.userView.tableUsers);
                this.toList(this.userView.tableUsers);
            }
        }
        
        if (e.getSource() == userView.btnUserDelete) { 
            if (userView.tableUsers.getSelectedRow() >= 0 && JOptionPane.showConfirmDialog(null, "¿Desea eleminar el elemento seleccionado?") == 0) {
                int ControlNumber_ = (int) userView.tableUsers.getModel().getValueAt(userView.tableUsers.getSelectedRow(), 0);
                user.setControlNumber(ControlNumber_);
                
                if(userDAO.deleteUser(user)){
                    JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                    this.cleanTable(this.userView.tableUsers);
                    this.toList(this.userView.tableUsers);
                } else {
                    JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila.");
            } 
        }//=======================================================================
    }
    

}
