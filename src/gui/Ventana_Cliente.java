package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

import java.sql.*;

public class Ventana_Cliente extends JFrame {
    private JPanel contentPane;
    private JTextField textField, textField_1, textField_2, textField_3, textField_4, textField_5, textField_6, textField_7, textField_8;
    private JTable table;
    private JLabel lblIdCliente;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblDni;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblDireccion;
    private JLabel lblCiudad;
    private JLabel lblDistrito;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana_Cliente frame = new Ventana_Cliente();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ventana_Cliente() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 551, 449);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField(); textField.setBounds(83, 47, 86, 20); contentPane.add(textField);
        textField_1 = new JTextField(); textField_1.setBounds(83, 75, 86, 20); contentPane.add(textField_1);
        textField_2 = new JTextField(); textField_2.setBounds(83, 104, 86, 20); contentPane.add(textField_2);
        textField_3 = new JTextField(); textField_3.setBounds(83, 129, 86, 20); contentPane.add(textField_3);
        textField_4 = new JTextField(); textField_4.setBounds(83, 154, 86, 20); contentPane.add(textField_4);
        textField_5 = new JTextField(); textField_5.setBounds(83, 179, 86, 20); contentPane.add(textField_5);
        textField_6 = new JTextField(); textField_6.setBounds(83, 204, 86, 20); contentPane.add(textField_6);
        textField_7 = new JTextField(); textField_7.setBounds(296, 179, 86, 20); contentPane.add(textField_7);
        textField_8 = new JTextField(); textField_8.setBounds(296, 204, 86, 20); contentPane.add(textField_8);

        JButton btnMostrar = new JButton("MOSTRAR");
        btnMostrar.setBounds(416, 59, 109, 23);
        contentPane.add(btnMostrar);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(190, 57, 192, 115);
        contentPane.add(btnAgregar);

        JButton btnModificar = new JButton("MODIFICAR");
        btnModificar.setBounds(416, 106, 109, 23);
        contentPane.add(btnModificar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(416, 151, 109, 23);
        contentPane.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 234, 521, 165);
        contentPane.add(scrollPane);
        String[] columnNames = {"ID Cliente", "Nombre", "Apellido", "DNI", "Telefono", "Correo", "Direccion", "Ciudad", "Distrito"};
        Object[][] data = {};
        table = new JTable(new DefaultTableModel(data, columnNames));
        table.setSurrendersFocusOnKeystroke(true);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        scrollPane.setViewportView(table);
        
        lblIdCliente = new JLabel("ID CLIENTE :");
        lblIdCliente.setBounds(10, 50, 74, 14);
        contentPane.add(lblIdCliente);
        
        lblNombre = new JLabel("NOMBRE :");
        lblNombre.setBounds(10, 78, 74, 14);
        contentPane.add(lblNombre);
        
        lblApellido = new JLabel("APELLIDO :");
        lblApellido.setBounds(10, 107, 74, 14);
        contentPane.add(lblApellido);
        
        lblDni = new JLabel("DNI :");
        lblDni.setBounds(10, 132, 74, 14);
        contentPane.add(lblDni);
        
        lblTelefono = new JLabel("TEL\u00C9FONO :");
        lblTelefono.setBounds(10, 157, 74, 14);
        contentPane.add(lblTelefono);
        
        lblCorreo = new JLabel("CORREO :");
        lblCorreo.setBounds(10, 182, 74, 14);
        contentPane.add(lblCorreo);
        
        lblDireccion = new JLabel("DIRECCI\u00D3N :");
        lblDireccion.setBounds(10, 207, 74, 14);
        contentPane.add(lblDireccion);
        
        lblCiudad = new JLabel("CIUDAD :");
        lblCiudad.setBounds(212, 182, 74, 14);
        contentPane.add(lblCiudad);
        
        lblDistrito = new JLabel("DISTRITO :");
        lblDistrito.setBounds(212, 207, 74, 14);
        contentPane.add(lblDistrito);
        
        JLabel lblCliente = new JLabel("CLIENTES");
        lblCliente.setFont(new Font("Tahoma", Font.BOLD, 35));
        lblCliente.setBounds(196, 11, 186, 31);
        contentPane.add(lblCliente);

        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });
    }

    private void mostrarClientes() {
        try (Connection conn = Conexion.getConexion(); // Realiza la Conexion con la base de datos
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("SELECT * FROM cliente")) {                   
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            // Recorre los resultados de la consulta y agregar las filas a la tabla
            while (rs.next()) {
                Object[] row = {
                    rs.getString("idcliente"), 
                    rs.getString("nombre"), 
                    rs.getString("apellido"), 
                    rs.getString("dni"), 
                    rs.getString("telefono"), 
                    rs.getString("correo"), 
                    rs.getString("direccion"), 
                    rs.getString("ciudad"), 
                    rs.getString("distrito") 
                };
                model.addRow(row); // Agregar la fila con los datos a la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error si ocurre una excepci�n
        }
    }

    private void agregarCliente() throws Exception {
        String idcliente = textField.getText();
        String nombre = textField_1.getText();
        String apellido = textField_2.getText();
        String dni = textField_3.getText();
        String telefono = textField_4.getText();
        String correo = textField_5.getText();
        String direccion = textField_6.getText();
        String ciudad = textField_7.getText();
        String distrito = textField_8.getText();

        String query = "INSERT INTO cliente (idcliente, nombre, apellido, dni, telefono, correo, direccion, ciudad, distrito) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConexion(); // Establecer la conexi�n con la base de datos
             PreparedStatement ps = conn.prepareStatement(query)) {           
       
            ps.setString(1, idcliente);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, dni);
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setString(7, direccion);
            ps.setString(8, ciudad);
            ps.setString(9, distrito);

            ps.executeUpdate(); // Ejecuta la actualizaci�n de la base de datos

            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente"); 
            mostrarClientes(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Error al agregar el cliente"); 
        }
    }

    private void modificarCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente de la tabla para modificar.");
            return;
        }        
        String idcliente = (String) table.getValueAt(selectedRow, 0);
        // Obtetiene los nuevos valores de los campos de texto
        String nombre = textField_1.getText();
        String apellido = textField_2.getText();
        String dni = textField_3.getText();
        String telefono = textField_4.getText();
        String correo = textField_5.getText();
        String direccion = textField_6.getText();
        String ciudad = textField_7.getText();
        String distrito = textField_8.getText();       
        // Consulta al SQL para actualizar los datos del cliente
        String query = "UPDATE cliente SET nombre = ?, apellido = ?, dni = ?, telefono = ?, correo = ?, direccion = ?, "
                + "ciudad = ?, distrito = ?, idcliente = ? WHERE idcliente = ?";     
        try (Connection conn = Conexion.getConexion(); 
             PreparedStatement ps = conn.prepareStatement(query)) {         
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, dni);
            ps.setString(4, telefono);
            ps.setString(5, correo);
            ps.setString(6, direccion);
            ps.setString(7, ciudad);
            ps.setString(8, distrito);
            ps.setString(9, textField.getText());
            ps.setString(10, idcliente); 
            
            // Ejecuta la actualizaci�n
            int rowsUpdated = ps.executeUpdate();            
            // Verifica si la actualizaci�n fue exitosa
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.");
                mostrarClientes(); // Actualiza la tabla de clientes
            } else {
                JOptionPane.showMessageDialog(this, "No se encontr� el cliente para modificar.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error si ocurre una excepci�n
            JOptionPane.showMessageDialog(this, "Error al modificar el cliente"); // Muestra mensaje de error
        }
    }

    private void eliminarCliente() {
        // Obtiene la fila seleccionada en la tabla
        int selectedRow = table.getSelectedRow();          
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente de la tabla para eliminar.");
            return;
        }       
        String idcliente = (String) table.getValueAt(selectedRow, 0);        
        // Confirma si el usuario est� seguro de eliminar el cliente
        int confirm = JOptionPane.showConfirmDialog(this, "�Est� seguro de que desea eliminar el cliente?", "Confirmar eliminaci�n",
                JOptionPane.YES_NO_OPTION);        
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Consulta SQL para eliminar un cliente
            String query = "DELETE FROM cliente WHERE idcliente = ?";
            
            try (Connection conn = Conexion.getConexion(); 
                 PreparedStatement ps = conn.prepareStatement(query)) {                 
              
                ps.setString(1, idcliente);                
              
                int rowsDeleted = ps.executeUpdate();                

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                    mostrarClientes(); // Actualizaa la tabla de clientes
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontr� el cliente para eliminar.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente."); 
            }
        }
    }
}