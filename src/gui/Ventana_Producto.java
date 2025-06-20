package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Conexion.Conexion;

public class Ventana_Producto extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldCodigo;
    private JTextField textFieldNombre;
    private JTextField textFieldStock;
    private JTextField textFieldPrecio;
    private JTextField textFieldCategoria;
    private JTable table;
    
    public Ventana_Producto() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 519, 371);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCodigo = new JLabel("C\u00D3DIGO PRODUCTO :");
        lblCodigo.setBounds(10, 45, 120, 14);
        contentPane.add(lblCodigo);

        JLabel lblNombre = new JLabel("NOMBRE :");
        lblNombre.setBounds(10, 80, 70, 14);
        contentPane.add(lblNombre);

        JLabel lblStock = new JLabel("STOCK :");
        lblStock.setBounds(10, 114, 70, 14);
        contentPane.add(lblStock);

        JLabel lblPrecio = new JLabel("PRECIO :");
        lblPrecio.setBounds(10, 145, 62, 14);
        contentPane.add(lblPrecio);

        JLabel lblCategoria = new JLabel("CATEGOR\u00CDA :");
        lblCategoria.setBounds(244, 148, 98, 14);
        contentPane.add(lblCategoria);

        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(131, 42, 86, 20);
        contentPane.add(textFieldCodigo);
        textFieldCodigo.setColumns(10);

        textFieldNombre = new JTextField();
        textFieldNombre.setColumns(10);
        textFieldNombre.setBounds(131, 77, 86, 20);
        contentPane.add(textFieldNombre);

        textFieldStock = new JTextField();
        textFieldStock.setColumns(10);
        textFieldStock.setBounds(131, 111, 86, 20);
        contentPane.add(textFieldStock);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setColumns(10);
        textFieldPrecio.setBounds(131, 142, 86, 20);
        contentPane.add(textFieldPrecio);

        textFieldCategoria = new JTextField();
        textFieldCategoria.setColumns(10);
        textFieldCategoria.setBounds(326, 145, 86, 20);
        contentPane.add(textFieldCategoria);

        JButton btnMostrar = new JButton("MOSTRAR");
        btnMostrar.setBounds(389, 45, 109, 23);
        contentPane.add(btnMostrar);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(234, 36, 132, 92);
        contentPane.add(btnAgregar);

        JButton btnModificar = new JButton("MODIFICAR");
        btnModificar.setBounds(389, 80, 109, 23);
        contentPane.add(btnModificar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(389, 114, 109, 23);
        contentPane.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 173, 488, 145);
        contentPane.add(scrollPane);

        String[] columnNames = {"C�digo Producto", "Nombre", "Stock", "Precio", "Categor�a"};
        table = new JTable(new DefaultTableModel(new Object[][] {}, columnNames));
        scrollPane.setViewportView(table);

        JLabel lblProductos = new JLabel("PRODUCTOS");
        lblProductos.setFont(new Font("Tahoma", Font.BOLD, 35));
        lblProductos.setBounds(167, 0, 245, 31);
        contentPane.add(lblProductos);

        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MostrarProduct();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AgregarProduct();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ModificarProduct();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	EliminarProduct();
            }
        });
    }

    // M�todo para mostrar los productos en una tabla
    private void MostrarProduct() {
       
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 
        
        try {
            String query = "SELECT * FROM producto";
            Connection conn = Conexion.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                String id = rs.getString("idproducto");
                String name = rs.getString("nombre");
                int stock = rs.getInt("sotck"); 
                double price = rs.getDouble("precio");
                String category = rs.getString("categoria");
                model.addRow(new Object[] { id, name, stock, price, category });
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar errores de SQL
        }
    }
    // M�todo para agregar un producto a la base de datos
    private void AgregarProduct() {
        try {
            String codigo = textFieldCodigo.getText();
            String nombre = textFieldNombre.getText();
            int stock = Integer.parseInt(textFieldStock.getText());
            double precio = Double.parseDouble(textFieldPrecio.getText());
            String categoria = textFieldCategoria.getText();

            // Validar que el stock y el precio no sean negativos
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.", "Error de validacion", JOptionPane.ERROR_MESSAGE);
                return; // Salir del m�todo
            }

            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.", "Error de validacion", JOptionPane.ERROR_MESSAGE);
                return; // Salir del m�todo
            }

            String query = "INSERT INTO producto (idproducto, nombre, sotck, precio, categoria) VALUES (?, ?, ?, ?, ?)";
            Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setInt(3, stock);
            ps.setDouble(4, precio);
            ps.setString(5, categoria);

            ps.executeUpdate();
            MostrarProduct(); // Actualizar la tabla
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos v�lidos para el stock y el precio.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el producto.", "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    // M�todo para modificar un producto existente
    private void ModificarProduct() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la tabla para modificar.");
            return;
        }

        String idProducto = (String) table.getValueAt(selectedRow, 0);

        String nuevoCodigo = textFieldCodigo.getText(); 
        String nombre = textFieldNombre.getText();
        int stock = Integer.parseInt(textFieldStock.getText());
        double precio = Double.parseDouble(textFieldPrecio.getText());
        String categoria = textFieldCategoria.getText();
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro de que desea modificar este producto?", "Confirmar modificacion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Consulta SQL para actualizar un producto
            String query = "UPDATE producto SET idproducto = ?, nombre = ?, sotck = ?, precio = ?, categoria = ? WHERE idproducto = ?";
            Connection conn = Conexion.getConexion();
            
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, nuevoCodigo);  
                ps.setString(2, nombre);
                ps.setInt(3, stock);
                ps.setDouble(4, precio);
                ps.setString(5, categoria);
                ps.setString(6, idProducto); 
                
                // Ejecutar la consulta
                int rowsUpdated = ps.executeUpdate();
                
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");
                    MostrarProduct(); // Actualizar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontro el producto para modificar.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al modificar el producto.");
            }
        }
    }

    // M�todo para eliminar un producto
    private void EliminarProduct() {
        int selectedRow = table.getSelectedRow();        
        // Verificar si se selecciona un producto
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la tabla para eliminar.");
            return;
        }

        String idProducto = (String) table.getValueAt(selectedRow, 0);        
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro de que desea eliminar este producto?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM producto WHERE idproducto = ?";
            Connection conn = Conexion.getConexion();
            
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, idProducto);             
                int rowsDeleted = ps.executeUpdate();               
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                    MostrarProduct(); // Actualizar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontro el producto para eliminar.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto.");
            }
        }
    }

    // Metodo principal para ejecutar la aplicacion
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana_Producto frame = new Ventana_Producto();
                    frame.setVisible(true); // Mostrar la ventana principal
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
            }
        });
    }
}