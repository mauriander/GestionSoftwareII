/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package ventanas;
/**
 *
 * @author @mauriander
 */

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class VentanaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private JLabel lblTitulo;
   
    public VentanaLogin() {
        setTitle("Login");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1)); // 3 filas: usuario, clave, botón
        
         // Título
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("SF Pro Display", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(30, 30, 30));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 40, 0));


       // Campo de usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("SF Pro Text", Font.BOLD, 14));
        lblUsuario.setForeground(new Color(60, 60, 60));
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblUsuario.setBorder(new EmptyBorder(0, 5, 5, 0));
        
        txtUsuario = new JTextField(20);
        personalizarCampoTexto(txtUsuario);
        txtUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        // Campo de contraseña
        JLabel lblClave = new JLabel("Contraseña");
        lblClave.setFont(new Font("SF Pro Text", Font.BOLD, 14));
        lblClave.setForeground(new Color(60, 60, 60));
        lblClave.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblClave.setBorder(new EmptyBorder(20, 5, 5, 0));
        
        txtClave = new JPasswordField(20);
       personalizarCampoTexto(txtClave);
        txtClave.setAlignmentX(Component.LEFT_ALIGNMENT);


        // Botón ingresar
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIngresar.setVerticalTextPosition(SwingConstants.BOTTOM);
         btnIngresar.addActionListener(e -> validarLogin());
         

        // Agregar todo
       add(lblUsuario);
       add(txtUsuario);
        
       add(lblClave);
       add(txtClave);
       
       
         
        btnIngresar.setFont(new Font("SF Pro Text", Font.BOLD, 16));
       // btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBorder(new EmptyBorder(15, 30, 15, 30));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setBorderPainted(false);
       
       
       add(btnIngresar);

        setVisible(true);
    }
    
    private void personalizarCampoTexto(JTextField campo) {
        campo.setFont(new Font("SF Pro Text", Font.PLAIN, 16));
        campo.setForeground(new Color(30, 30, 30));
        campo.setBorder(BorderFactory.createCompoundBorder(
                //200,200,200 Gris
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(12, 15, 12, 15)
        ));
        campo.setMaximumSize(new Dimension(350, 50));
        
        // Efecto de enfoque
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    //0,122,125 Azul
                    BorderFactory.createLineBorder(new Color(0, 122, 255), 2),
                    // 1 menos 
                        new EmptyBorder(11, 14, 11, 14)
                ));
            }
            
            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    new EmptyBorder(12, 15, 12, 15)
                ));
            }
        });
    }

    private void validarLogin() {
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());
       // JOptionPane.showMessageDialog(this, "Entro a login");
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/MAURICIO/Desktop/Database2.accdb");

            String sql = "SELECT * FROM Usuarios WHERE usuario = ? AND clave = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, clave);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login exitoso");
                dispose();
                //new VentanaPrincipal();
                new VentanaPrincipal(conn); // pasamos la conexión
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos");
            }

         //   conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }
    }
}
