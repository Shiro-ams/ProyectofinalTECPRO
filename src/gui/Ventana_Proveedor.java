package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

import java.sql.*;

public class Ventana_Proveedor extends JFrame {
    private JPanel contentPane;
    private JTextField textField, textField_1, textField_2;
    private JTable table;
    private JLabel lblIdProveedor, lblNombre, lblNroContacto;
    private JLabel lblProveedores;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana_Proveedor frame = new Ventana_Proveedor();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ventana_Proveedor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 551, 449);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField(); textField.setBounds(111, 102, 86, 20); contentPane.add(textField);
        textField_1 = new JTextField(); textField_1.setBounds(111, 133, 86, 20); contentPane.add(textField_1);
        textField_2 = new JTextField(); textField_2.setBounds(111, 164, 86, 20); contentPane.add(textField_2);

        JButton btnMostrar = new JButton("MOSTRAR");
        btnMostrar.setBounds(416, 89, 109, 23);
        contentPane.add(btnMostrar);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(217, 83, 189, 121);
        contentPane.add(btnAgregar);

        JButton btnModificar = new JButton("MODIFICAR");
        btnModificar.setBounds(416, 136, 109, 23);
        contentPane.add(btnModificar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(416, 181, 109, 23);
        contentPane.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 235, 521, 164);
        contentPane.add(scrollPane);
        String[] columnNames = {"ID Proveedor", "Nombre", "Nro Contacto"};
        Object[][] data = {};
        table = new JTable(new DefaultTableModel(data, columnNames));
        scrollPane.setViewportView(table);

        lblIdProveedor = new JLabel("ID PROVEEDOR :");
        lblIdProveedor.setBounds(10, 105, 100, 14);
        contentPane.add(lblIdProveedor);

        lblNombre = new JLabel("NOMBRE :");
        lblNombre.setBounds(10, 138, 74, 14);
        contentPane.add(lblNombre);

        lblNroContacto = new JLabel("NRO CONTACTO :");
        lblNroContacto.setBounds(10, 167, 100, 14);
        contentPane.add(lblNroContacto);
        
        lblProveedores = new JLabel("PROVEEDORES");
        lblProveedores.setFont(new Font("Tahoma", Font.BOLD, 35));
        lblProveedores.setBounds(154, 22, 282, 31);
        contentPane.add(lblProveedores);

        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarProveedores();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProveedor();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarProveedor();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProveedor();
            }
        });
    }
    
    //Metodo para mostrar proveedor
    private void mostrarProveedores() {
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM proveedor")) {
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Limpia la tabla antes de cargar los nuevos datos
            while (rs.next()) {
                Object[] row = {
                    rs.getString("idproveedor"),
                    rs.getString("nombre"),
                    rs.getString("numero_contacto")
                };
                model.addRow(row); // Agrega cada registro como una fila en la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Metodo para agregar proveedor
    private void agregarProveedor() {
        String idproveedor = textField.getText();
        String nombre = textField_1.getText();
        String contacto = textField_2.getText();

        String query = "INSERT INTO proveedor (idproveedor, nombre, numero_contacto) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idproveedor);
            ps.setString(2, nombre);
            ps.setString(3, contacto);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Proveedor agregado correctamente");
            mostrarProveedores(); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el proveedor");
        }
    }

    //Metodo para modificar proveedor
    private void modificarProveedor() {
        int selectedRow = table.getSelectedRow();
        
        // Verifica si se seleccion� un proveedor
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un proveedor de la tabla para modificar.");
            return;
        }

        String idproveedor = textField.getText();

        String nombre = textField_1.getText();
        String contacto = textField_2.getText();

        if (idproveedor.trim().isEmpty() || nombre.trim().isEmpty() || contacto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return;
        }


        String query = "UPDATE proveedor SET idproveedor = ?, nombre = ?, numero_contacto = ? WHERE idproveedor = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, idproveedor);
            ps.setString(2, nombre);     
            ps.setString(3, contacto);    
            ps.setString(4, (String) table.getValueAt(selectedRow, 0));  //Identifica al proveedor

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente.");
                mostrarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontr� el proveedor para modificar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el proveedor.");
        }
    }

    //Metodo para modificar proveedor
    private void eliminarProveedor() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un proveedor de la tabla para eliminar.");
            return;
        }
        
        String idproveedor = (String) table.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, "�Est� seguro de que desea eliminar el proveedor?", "Confirmar eliminaci�n", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM proveedor WHERE idproveedor = ?";
            
            try (Connection conn = Conexion.getConexion();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
            	// Le asigna un idproveedor a la consulta
                ps.setString(1, idproveedor);
                
                int rowsDeleted = ps.executeUpdate();
                
                // Verifica si se elimin� el dato insertado a la tabla proveedor
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                    mostrarProveedores(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontr� el proveedor para eliminar.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor.");
            }
        }
    }
}
