package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import procesos.Stock;
import sub_procesos.Productos;
import Conexion.Conexion;

public class Ventana_Stock extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTextArea textStock;
    private JButton btnMostrar;
    private JButton btnBuscarCategoria;
    private JTextField textCategoria;
    private Stock stockExistente;
    private JButton btnBuscarNombre;
    private JTextField textNombre;

    public Ventana_Stock(Stock s) {
        this.stockExistente = s;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 709, 400);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 673, 250);
        contentPane.add(scrollPane);

        textStock = new JTextArea();
        scrollPane.setViewportView(textStock);
        textStock.setEditable(false);

        btnMostrar = new JButton("Mostrar Todo");
        btnMostrar.addActionListener(this);
        btnMostrar.setBounds(10, 280, 120, 25);
        contentPane.add(btnMostrar);

        JLabel lblCategoria = new JLabel("Categor�a:");
        lblCategoria.setBounds(150, 280, 80, 25);
        contentPane.add(lblCategoria);

        textCategoria = new JTextField();
        textCategoria.setBounds(230, 280, 120, 25);
        contentPane.add(textCategoria);

        btnBuscarCategoria = new JButton("Buscar");
        btnBuscarCategoria.addActionListener(this);
        btnBuscarCategoria.setBounds(360, 280, 100, 25);
        contentPane.add(btnBuscarCategoria);
        
        JLabel lblNombre = new JLabel("Buscar por nombre:");
        lblNombre.setBounds(150, 320, 130, 25);
        contentPane.add(lblNombre);

        textNombre = new JTextField();
        textNombre.setBounds(280, 320, 150, 25);
        contentPane.add(textNombre);

        btnBuscarNombre = new JButton("Buscar");
        btnBuscarNombre.setBounds(440, 320, 100, 25);
        btnBuscarNombre.addActionListener(this);
        contentPane.add(btnBuscarNombre);

    }

    void Imprimir(String s) {
        textStock.append(s + "\n");
    }

    void Listado() {
        Imprimir("C�digo\tNombre\tCantidad\tPrecio\tPrecio Venta\tGanancia\tCategor�a");
        Imprimir("-----------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMostrar) {
            mostrarDatosDesdeBaseDeDatos(null, null); 
        } else if (e.getSource() == btnBuscarCategoria) {
            String categoria = textCategoria.getText().trim();
            if (!categoria.isEmpty()) {
                mostrarDatosDesdeBaseDeDatos("categoria", categoria);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese una categor�a.");
            }
        } else if (e.getSource() == btnBuscarNombre) {
            String nombre = textNombre.getText().trim();
            if (!nombre.isEmpty()) {
                mostrarDatosDesdeBaseDeDatos("nombre", nombre);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre.");
            }
        }
    }

    protected void do_btnMostrar_actionPerformed(ActionEvent arg0) {
        textStock.setText("");
        Listado();
        DecimalFormat df = new DecimalFormat("#.##");

        for (int i = 0; i < stockExistente.Tamaño(); i++) {
            Productos productoActual = stockExistente.Obtener(i);

            double precioVenta = productoActual.getPreC() * 1.2;
            double ganancia = precioVenta - productoActual.getPreC();

            Imprimir(productoActual.getCod() + "\t" + productoActual.getNom() + "\t" +
                     productoActual.getCant() + "\t" + productoActual.getPreC() + "\t" +
                     df.format(precioVenta) + "\t" + df.format(ganancia) + "\t" +
                     productoActual.getCategoria());
        }
    }

    private void buscarPorCategoria() {
        String categoria = textCategoria.getText();
        DecimalFormat df = new DecimalFormat("#.##");

        if (categoria.isEmpty()) {
            do_btnMostrar_actionPerformed(null);
            return;
        }

        ArrayList<Productos> resultados = stockExistente.BuscarPorCategoria(categoria);
        textStock.setText("");
        Listado();

        if (resultados.isEmpty()) {
            Imprimir("No se encontraron productos en la categor�a: " + categoria);
        } else {
            for (Productos p : resultados) {
                double precioVenta = p.getPreC() * 1.2;
                double ganancia = precioVenta - p.getPreC();

                Imprimir(p.getCod() + "\t" + p.getNom() + "\t" + p.getCant() + "\t" +
                         p.getPreC() + "\t" + df.format(precioVenta) + "\t" +
                         df.format(ganancia) + "\t" + p.getCategoria());
            }
        }
    }

    private void buscarPorNombre() {
        String nombre = textNombre.getText().trim().toLowerCase();
        DecimalFormat df = new DecimalFormat("#.##");

        if (nombre.isEmpty()) {
            Imprimir("Por favor, ingrese el nombre del producto a buscar.");
            return;
        }

        ArrayList<Productos> resultados = stockExistente.BuscarPorNombre(nombre);
        textStock.setText("");
        Listado();

        if (resultados.isEmpty()) {
            Imprimir("No se encontraron productos con el nombre: " + nombre);
        } else {
            for (Productos p : resultados) {
                double precioVenta = p.getPrecio() * 1.2;
                double ganancia = precioVenta - p.getPrecio();

                Imprimir(p.getCod() + "\t" + p.getNom() + "\t" + p.getCant() + "\t" +
                         p.getPreC() + "\t" + df.format(precioVenta) + "\t" +
                         df.format(ganancia) + "\t" + p.getCategoria());
            }
        }
    }
    
    private void mostrarDatosDesdeBaseDeDatos(String filtroColumna, String filtroValor) {
        textStock.setText(""); 
        Listado(); 

        Connection conexion = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conexion = Conexion.getConexion();
            stmt = conexion.createStatement();
            
            String query = "SELECT idproducto AS codigo, nombre, sotck, precio, categoria FROM producto";

            if (filtroColumna != null && filtroValor != null) {
                query += " WHERE " + filtroColumna + " LIKE '%" + filtroValor + "%'";
            }

            // Ejecutar la consulta
            rs = stmt.executeQuery(query);

            DecimalFormat df = new DecimalFormat("#.##");

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("sotck");  
                double precioCompra = rs.getDouble("precio");
                String categoria = rs.getString("categoria");

                double precioVenta = precioCompra * 1.2;
                double ganancia = precioVenta - precioCompra;

                Imprimir(codigo + "\t" + nombre + "\t" + cantidad + "\t" +
                         df.format(precioCompra) + "\t" +
                         df.format(precioVenta) + "\t" +
                         df.format(ganancia) + "\t" + categoria);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexion != null) conexion.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


