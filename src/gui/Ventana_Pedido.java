package gui;

import Conexion.Conexion;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Ventana_Pedido extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JTextField txtIDPedido;
    private JTextField txtFecha;
    private JTextField txtCodProducto;
    private JTextField txtCantProducto;
    private JTextField txtIDProveedor;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana_Pedido frame = new Ventana_Pedido();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ventana_Pedido() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 660, 377);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdpedido = new JLabel("ID PEDIDO :");
        lblIdpedido.setBounds(10, 46, 77, 14);
        contentPane.add(lblIdpedido);

        JLabel lblIdProveedor = new JLabel("ID PROVEEDOR :");
        lblIdProveedor.setBounds(10, 82, 95, 14);
        contentPane.add(lblIdProveedor);

        JLabel lblFecha = new JLabel("FECHA :");
        lblFecha.setBounds(10, 122, 77, 14);
        contentPane.add(lblFecha);

        JLabel lblCodigoProducto = new JLabel("CODIGO PRODUCTO :");
        lblCodigoProducto.setBounds(10, 158, 132, 14);
        contentPane.add(lblCodigoProducto);

        JLabel lblCantidad = new JLabel("CANTIDAD :");
        lblCantidad.setBounds(276, 161, 77, 14);
        contentPane.add(lblCantidad);

        JButton btnNuevoPedido = new JButton("NUEVO PEDIDO");
        btnNuevoPedido.setBounds(256, 46, 193, 90);
        contentPane.add(btnNuevoPedido);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(502, 45, 132, 23);
        contentPane.add(btnAgregar);

        JButton btnModificar = new JButton("MODIFICAR");
        btnModificar.setBounds(502, 115, 132, 23);
        contentPane.add(btnModificar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(502, 149, 132, 23);
        contentPane.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 189, 629, 133);
        contentPane.add(scrollPane);

        table = new JTable();
        String[] columnNames = {"ID Pedido", "Proveedor", "ID Producto", "Producto", "Cantidad", "Precio", "Categoria", "Fecha Pedido"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        table.setModel(model);
        scrollPane.setViewportView(table);

        txtIDPedido = new JTextField();
        txtIDPedido.setBounds(119, 43, 115, 20);
        contentPane.add(txtIDPedido);
        txtIDPedido.setColumns(10);

        txtFecha = new JTextField();
        txtFecha.setColumns(10);
        txtFecha.setBounds(119, 119, 115, 20);
        contentPane.add(txtFecha);

        txtCodProducto = new JTextField();
        txtCodProducto.setColumns(10);
        txtCodProducto.setBounds(148, 155, 86, 20);
        contentPane.add(txtCodProducto);

        txtCantProducto = new JTextField();
        txtCantProducto.setColumns(10);
        txtCantProducto.setBounds(363, 158, 86, 20);
        contentPane.add(txtCantProducto);

        txtIDProveedor = new JTextField();
        txtIDProveedor.setColumns(10);
        txtIDProveedor.setBounds(119, 79, 115, 20);
        contentPane.add(txtIDProveedor);

        java.util.Date fechaActual = new java.util.Date();
        java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("yyyy-MM-dd");
        txtFecha.setText(formatoFecha.format(fechaActual));

        JButton btnMostrar = new JButton("MOSTRAR");
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mostrarPedidos();
            }
        });
        btnMostrar.setBounds(502, 81, 132, 23);
        contentPane.add(btnMostrar);

        JLabel lblPedidos = new JLabel("PEDIDOS");
        lblPedidos.setFont(new Font("Tahoma", Font.BOLD, 35));
        lblPedidos.setBounds(263, 4, 186, 31);
        contentPane.add(lblPedidos);

        btnNuevoPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nuevoPedido();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarDetallePedido();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarPedido();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarPedido();
            }
        });
    }

    private void mostrarPedidos() {
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.idpedido, pr.nombre AS proveedor, dp.idproductoref, prd.nombre AS producto, dp.cantidad, prd.precio, prd.categoria, p.fecha " +
                     "FROM pedido p " +
                     "JOIN detalle_pedido dp ON p.idpedido = dp.idpedidoref " +
                     "JOIN producto prd ON dp.idproductoref = prd.idproducto " +
                     "JOIN proveedor pr ON p.idproveedorref = pr.idproveedor")) {

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                        rs.getString("idpedido"),
                        rs.getString("proveedor"),
                        rs.getString("idproductoref"),
                        rs.getString("producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getDate("fecha")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al mostrar los pedidos.");
        }
    }

    private void nuevoPedido() {
        String idPedido = txtIDPedido.getText();
        String idProveedor = txtIDProveedor.getText();
        String fecha = txtFecha.getText();  

        try (Connection conn = Conexion.getConexion()) {
            String sql = "INSERT INTO pedido (idpedido, idproveedorref, fecha) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idPedido);
                ps.setString(2, idProveedor);
                ps.setString(3, fecha);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Pedido creado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear el pedido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear el pedido.");
        }
    }
    

    private void agregarDetallePedido() {
        String idPedido = txtIDPedido.getText();
        String idProducto = txtCodProducto.getText();
        
        try {
            int cantidad = Integer.parseInt(txtCantProducto.getText());

            // Validar que la cantidad no sea negativa
            if (cantidad < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.", "Error de validaci�n", JOptionPane.ERROR_MESSAGE);
                return; // Salir del m�todo
            }

            try (Connection conn = Conexion.getConexion()) {
                String sqlDetalle = "INSERT INTO detalle_pedido (idpedidoref, idproductoref, cantidad) VALUES (?, ?, ?)";
                try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
                    psDetalle.setString(1, idPedido);
                    psDetalle.setString(2, idProducto);
                    psDetalle.setInt(3, cantidad);

                    int rowsAffected = psDetalle.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Detalle de pedido agregado correctamente.");
                        
                        // Actualizar el stock del producto
                        String sqlActualizarStock = "UPDATE producto SET sotck = sotck + ? WHERE idproducto = ?";
                        try (PreparedStatement psActualizarStock = conn.prepareStatement(sqlActualizarStock)) {
                            psActualizarStock.setInt(1, cantidad);
                            psActualizarStock.setString(2, idProducto);
                            psActualizarStock.executeUpdate();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al agregar el detalle del pedido.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un n�mero v�lido para la cantidad.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el detalle del pedido.", "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarPedido() {
        String idPedido = txtIDPedido.getText();
        String idProveedor = txtIDProveedor.getText();
        String fecha = txtFecha.getText();  

        try (Connection conn = Conexion.getConexion()) {
            String sql = "UPDATE pedido SET idproveedorref = ?, fecha = ? WHERE idpedido = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idProveedor);
                ps.setString(2, fecha);
                ps.setString(3, idPedido);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Pedido modificado correctamente.");
                    mostrarPedidos();  
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar el pedido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el pedido.");
        }
    }

    private void eliminarPedido() {
        String idPedido = txtIDPedido.getText();
        String idProducto = txtCodProducto.getText();
        
        try (Connection conn = Conexion.getConexion()) {
            // Obtiene los detalles de los productos asociados al pedido
            String sqlObtenerDetalles = "SELECT idproductoref, cantidad FROM detalle_pedido WHERE idpedidoref = ?";
            PreparedStatement psDetalles = conn.prepareStatement(sqlObtenerDetalles);
            psDetalles.setString(1, idPedido);
            
            try (ResultSet rs = psDetalles.executeQuery()) {
                // Se obtiene la lista de productos y sus cantidades
                while (rs.next()) {
                    String idProd = rs.getString("idproductoref");
                    int cantidadVendida = rs.getInt("cantidad");

                    // Actualiza el stock de cada producto eliminado
                    String sqlActualizarStock = "UPDATE producto SET sotck = sotck - ? WHERE idproducto = ?";
                    try (PreparedStatement psActualizarStock = conn.prepareStatement(sqlActualizarStock)) {
                        psActualizarStock.setInt(1, cantidadVendida);
                        psActualizarStock.setString(2, idProd);
                        psActualizarStock.executeUpdate(); // Actualizar el stock de cada producto
                    }
                }
            }

            // Elimina todos los detalles del pedido
            String sqlEliminarDetalle = "DELETE FROM detalle_pedido WHERE idpedidoref = ?";
            String sqlEliminarPedido = "DELETE FROM pedido WHERE idpedido = ?";

            try (PreparedStatement psEliminarDetalle = conn.prepareStatement(sqlEliminarDetalle);
                 PreparedStatement psEliminarPedido = conn.prepareStatement(sqlEliminarPedido)) {
                
                // Elimina los detalles del pedido
                psEliminarDetalle.setString(1, idPedido);
                int rowsAffectedDetalle = psEliminarDetalle.executeUpdate();
                
                if (rowsAffectedDetalle > 0) {
                    // Ahora eliminamos el pedido
                    psEliminarPedido.setString(1, idPedido);
                    int rowsAffectedPedido = psEliminarPedido.executeUpdate();
                    
                    if (rowsAffectedPedido > 0) {
                        JOptionPane.showMessageDialog(this, "Pedido y detalles eliminados y stock actualizado.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el pedido.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar los detalles del pedido.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el pedido y actualizar el stock.");
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}