package ventanas;
/**
 *
 * @author @mauriander
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaUsuarios extends JFrame {
    private JTextField txtId, txtNombre, txtApellido, txtTelefono;
    private JButton btnAgregar, btnEditar, btnCambiarEstado,btnLimpiar;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtDNI, txtEmail;
    private JComboBox<String> comboActivo;
    
    private Connection conn;
    
    public VentanaUsuarios(Connection conn) {
        this.conn = conn;
        
        setTitle("Gestión de Usuarios");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize components
        txtId = new JTextField(5);
        txtNombre = new JTextField(10);
        txtApellido = new JTextField(10);
        txtTelefono = new JTextField(10);
        txtDNI = new JTextField(10);
        txtEmail = new JTextField(15);
        comboActivo = new JComboBox<>(new String[]{"Sí", "No"});
        
        // Initialize buttons
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnCambiarEstado = new JButton("Cambiar Estado");
          btnEditar.setEnabled(false);
           btnAgregar.setEnabled(true);
   btnLimpiar = new JButton("Limpiar");
        // Add components to frame
        add(new JLabel("DNI:")); add(txtDNI);
        add(new JLabel("Teléfono:")); add(txtTelefono);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("Activo:")); add(comboActivo);

        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        tablaUsuarios = new JTable(modeloTabla);

        add(new JLabel("ID:"));
        add(txtId);
        txtId.setEditable(false);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(btnAgregar);
        add(btnEditar);
        add(btnCambiarEstado);
        add(new JScrollPane(tablaUsuarios));
        add(btnLimpiar);
        // Configurar acciones de los botones
        btnAgregar.addActionListener(e -> agregarUsuario());
        btnEditar.addActionListener(e -> editarUsuario());
        btnCambiarEstado.addActionListener(e -> cambiarEstadoUsuario());
        
        
        // Configurar selección de tabla para edición
       tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablaUsuarios.getSelectedRow();
                btnEditar.setEnabled(selectedRow != -1);
                btnAgregar.setEnabled(selectedRow == -1);
                if (selectedRow != -1) {
                    cargarDatosSeleccionados();
                }
            }
        });

           this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuarios.clearSelection();
                btnAgregar.setEnabled(true);
                btnEditar.setEnabled(false);
            }
        });
         btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Cargar datos iniciales
        cargarUsuariosDesdeBD();
        setVisible(true);
    }
    
    private void agregarUsuario() {
        try {
            // Validar campos obligatorios
            if (txtNombre.getText().trim().isEmpty() || 
                txtApellido.getText().trim().isEmpty() ||
                txtDNI.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, Apellido y DNI son campos obligatorios");
                return;
            }
            
            // Preparar la consulta SQL
            String sql = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Telefono, Email, Activo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, txtDNI.getText().trim());
            stmt.setString(2, txtNombre.getText().trim());
            stmt.setString(3, txtApellido.getText().trim());
            stmt.setString(4, txtTelefono.getText().trim());
            stmt.setString(5, txtEmail.getText().trim());
            stmt.setBoolean(6, comboActivo.getSelectedItem().equals("Sí"));
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener el ID generado
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    JOptionPane.showMessageDialog(this, "Usuario agregado con ID: " + idGenerado);
                }
                
                // Limpiar formulario y actualizar tabla
                limpiarFormulario();
                cargarUsuariosDesdeBD();
            }
            
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void editarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para editar");
            return;
        }
        
        try {
            // Validar campos obligatorios
            if (txtNombre.getText().trim().isEmpty() || 
                txtApellido.getText().trim().isEmpty() ||
                txtDNI.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, Apellido y DNI son campos obligatorios");
                return;
            }
            
            // Obtener ID del usuario seleccionado
            int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            // Preparar la consulta SQL de actualización
            String sql = "UPDATE Usuarios SET DNI = ?, Nombre = ?, Apellido = ?, Telefono = ?, Email = ?, Activo = ? WHERE ID_Usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, txtDNI.getText().trim());
            stmt.setString(2, txtNombre.getText().trim());
            stmt.setString(3, txtApellido.getText().trim());
            stmt.setString(4, txtTelefono.getText().trim());
            stmt.setString(5, txtEmail.getText().trim());
            stmt.setBoolean(6, comboActivo.getSelectedItem().equals("Sí"));
            stmt.setInt(7, idUsuario);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                
                // Actualizar tabla
                cargarUsuariosDesdeBD();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario");
            }
            
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al editar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void cambiarEstadoUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            try {
                int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
                String estadoActual = (String) modeloTabla.getValueAt(fila, 4);
                String nuevoEstado = estadoActual.equals("Activo") ? "Inactivo" : "Activo";
                
                // Actualizar en la base de datos
                String sql = "UPDATE Usuarios SET Activo = ? WHERE ID_Usuario = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setBoolean(1, nuevoEstado.equals("Activo"));
                stmt.setInt(2, idUsuario);
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    // Actualizar la tabla
                    modeloTabla.setValueAt(nuevoEstado, fila, 4);
                    JOptionPane.showMessageDialog(this, "Estado actualizado correctamente");
                }
                
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para cambiar el estado.");
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                
                // Cargar datos completos del usuario desde la base de datos
                String sql = "SELECT * FROM Usuarios WHERE ID_Usuario = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idUsuario);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    txtId.setText(String.valueOf(rs.getInt("ID_Usuario")));
                    txtDNI.setText(rs.getString("DNI"));
                    txtNombre.setText(rs.getString("Nombre"));
                    txtApellido.setText(rs.getString("Apellido"));
                    txtTelefono.setText(rs.getString("Telefono"));
                    txtEmail.setText(rs.getString("Email"));
                    comboActivo.setSelectedItem(rs.getBoolean("Activo") ? "Sí" : "No");
                }
                
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar datos del usuario: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void cargarUsuariosDesdeBD() {
        try {
            String sql = "SELECT ID_Usuario, Nombre, Apellido, Telefono, Activo FROM Usuarios";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Clear existing rows
            modeloTabla.setRowCount(0);

            while (rs.next()) {
                String estado = rs.getBoolean("Activo") ? "Activo" : "Inactivo";
                modeloTabla.addRow(new Object[]{
                    rs.getInt("ID_Usuario"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Telefono"),
                    estado
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        comboActivo.setSelectedItem("Sí");
        tablaUsuarios.clearSelection();

             
        // Reset button states
        btnAgregar.setEnabled(true);
        btnEditar.setEnabled(false);
    
    }
}