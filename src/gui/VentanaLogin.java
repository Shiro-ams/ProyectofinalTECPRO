package gui;

import javax.swing.*;
import java.awt.event.*;

public class VentanaLogin extends JFrame {
    private JTextField textUsuario;
    private JPasswordField textContrasena;
    private JButton btnIngresar;

    private Usuarios usuarios;  // usuario para validar

    public VentanaLogin() {
        usuarios = new Usuarios("admin", "mysql"); // ejemplo: usuario creado aquí

        setTitle("Login");
        setSize(328, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(20, 20, 80, 25);
        getContentPane().add(lblUsuario);

        textUsuario = new JTextField();
        textUsuario.setBounds(100, 20, 160, 25);
        getContentPane().add(textUsuario);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(20, 60, 80, 25);
        getContentPane().add(lblContrasena);

        textContrasena = new JPasswordField();
        textContrasena.setBounds(100, 60, 160, 25);
        getContentPane().add(textContrasena);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(100, 100, 100, 25);
        getContentPane().add(btnIngresar);

        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = textUsuario.getText();
                String contra = new String(textContrasena.getPassword());

                if (usuario.validar(nombre, contra)) {
                    JOptionPane.showMessageDialog(VentanaLogin.this, "Login correcto");
                } else {
                    JOptionPane.showMessageDialog(VentanaLogin.this, "Usuario o contraseña incorrectos");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}




