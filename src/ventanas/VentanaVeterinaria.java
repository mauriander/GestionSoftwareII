/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ventanas;

/**
 *
 * @author @mauriander
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaVeterinaria extends JFrame {
    private JComboBox<String> comboUsuario;
    private JComboBox<String> comboMascota;
    private JTextField txtEspecie; // Campo para mostrar la especie (solo lectura)
    private JComboBox<String> comboTipoConsulta;
    //private JFormattedTextField txtFecha;
    private JTextField txtFecha; 
    private JTextField txtPeso;
    private JTextField txtObservacion;
    private JButton btnAgregar, btnEditar, btnBorrar;
    private JTable tablaConsultas;
    private DefaultTableModel modeloTabla;

    private Map<String, Integer> usuariosMap; // Mapa usuario -> ID_Usuario
    private Map<String, Integer> mascotasMap; // Mapa mascota -> ID_Mascota
    private Map<Integer, String> especiesMap; // Mapa ID_Mascota -> Especie
        
    private Connection conn;
    

    public VentanaVeterinaria(Connection conn) {
        this.conn = conn;
        this.usuariosMap = new HashMap<>();
        this.mascotasMap = new HashMap<>();
        this.especiesMap = new HashMap<>();
        
        setTitle("Gestión de Consultas Veterinarias");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // Inicializar componentes
        comboUsuario = new JComboBox<>();
        comboMascota = new JComboBox<>();
        txtEspecie = new JTextField(10);
        txtEspecie.setEditable(false); // Solo lectura
        comboTipoConsulta = new JComboBox<>(new String[]{"Control", "Urgencia", "Vacunación", "Cirugía"});

      //  SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
       // txtFecha = new JFormattedTextField(formato);
        //txtFecha.setColumns(8);

        txtFecha = new JTextField(10);

        txtPeso = new JTextField(5);
        txtObservacion = new JTextField(15);

        btnAgregar = new JButton("Agregar Consulta");
        btnEditar = new JButton("Editar");
        btnBorrar = new JButton("Borrar");

        String[] columnas = {"ID", "Fecha", "Usuario", "Mascota", "Especie", "Tipo Consulta", "Peso", "Observación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };
        tablaConsultas = new JTable(modeloTabla);

        // Agregar componentes al frame
        add(new JLabel("Fecha:dd/MM/yyyy"));
        add(txtFecha);
        add(new JLabel("Usuario:"));
        add(comboUsuario);
        add(new JLabel("Mascota:"));
        add(comboMascota);
        add(new JLabel("Especie:"));
        add(txtEspecie);
        add(new JLabel("Tipo Consulta:"));
        add(comboTipoConsulta);
        add(new JLabel("Peso (kg):"));
        add(txtPeso);
        add(new JLabel("Observación:"));
        add(txtObservacion);
        add(btnAgregar);
        add(btnEditar);
        add(btnBorrar);
        add(new JScrollPane(tablaConsultas));

        // Cargar datos iniciales
        cargarUsuarios();
        cargarConsultasDesdeBD();

        // Actualizar mascotas según usuario seleccionado
        comboUsuario.addActionListener(e -> actualizarMascotasPorUsuario());

        // Actualizar especie cuando se selecciona una mascota
        comboMascota.addActionListener(e -> actualizarEspecie());

        // Acción Agregar
        btnAgregar.addActionListener(e -> agregarConsulta());

        // Acción Editar
        btnEditar.addActionListener(e -> editarConsulta());

        // Acción Borrar
        btnBorrar.addActionListener(e -> borrarConsulta());

        setVisible(true);
    }

    // Método para cargar usuarios desde la base de datos
    private void cargarUsuarios() {
        try {
            String sql = "SELECT ID_Usuario, Nombre, Apellido FROM Usuarios WHERE Activo = TRUE ORDER BY Nombre, Apellido";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            comboUsuario.removeAllItems();
            usuariosMap.clear();
            
            comboUsuario.addItem("-- Seleccionar Usuario --");
            
            while (rs.next()) {
                int idUsuario = rs.getInt("ID_Usuario");
                String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("Apellido");
                
                comboUsuario.addItem(nombreCompleto);
                usuariosMap.put(nombreCompleto, idUsuario);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para actualizar mascotas según el usuario seleccionado
    private void actualizarMascotasPorUsuario() {
        String usuarioSeleccionado = (String) comboUsuario.getSelectedItem();
        if (usuarioSeleccionado == null || usuarioSeleccionado.equals("-- Seleccionar Usuario --")) {
            comboMascota.removeAllItems();
            txtEspecie.setText("");
            return;
        }

        Integer idUsuario = usuariosMap.get(usuarioSeleccionado);
        if (idUsuario == null) return;

        try {
            String sql = "SELECT ID_Mascota, Nombre, Especie FROM Mascotas WHERE ID_Usuario = ? AND Activo = TRUE ORDER BY Nombre";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            comboMascota.removeAllItems();
            mascotasMap.clear();
            especiesMap.clear();
            
            comboMascota.addItem("-- Seleccionar Mascota --");
            
            while (rs.next()) {
                int idMascota = rs.getInt("ID_Mascota");
                String nombreMascota = rs.getString("Nombre");
                String especie = rs.getString("Especie");
                
                comboMascota.addItem(nombreMascota);
                mascotasMap.put(nombreMascota, idMascota);
                especiesMap.put(idMascota, especie);
            }

            rs.close();
            stmt.close();
            
            // Limpiar campo de especie
            txtEspecie.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mascotas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para actualizar la especie cuando se selecciona una mascota
    private void actualizarEspecie() {
        String mascotaSeleccionada = (String) comboMascota.getSelectedItem();
        if (mascotaSeleccionada == null || mascotaSeleccionada.equals("-- Seleccionar Mascota --")) {
            txtEspecie.setText("");
            return;
        }

        Integer idMascota = mascotasMap.get(mascotaSeleccionada);
        if (idMascota != null) {
            String especie = especiesMap.get(idMascota);
            txtEspecie.setText(especie != null ? especie : "");
        }
    }

    // Método para agregar consulta
    private void agregarConsulta() {
        try {
            // Validaciones
            if (txtFecha.getText().trim().isEmpty() || 
                txtPeso.getText().trim().isEmpty() || 
                txtObservacion.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
                return;
            }

            String usuarioSeleccionado = (String) comboUsuario.getSelectedItem();
            String mascotaSeleccionada = (String) comboMascota.getSelectedItem();
            
            if (usuarioSeleccionado == null || usuarioSeleccionado.equals("-- Seleccionar Usuario --") ||
                mascotaSeleccionada == null || mascotaSeleccionada.equals("-- Seleccionar Mascota --")) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario y una mascota.");
                return;
            }

            // Validar peso
            double peso;
            try {
                peso = Double.parseDouble(txtPeso.getText().trim());
                if (peso <= 0) {
                    JOptionPane.showMessageDialog(this, "El peso debe ser un número positivo.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El peso debe ser un número válido.");
                return;
            }

            // Obtener IDs
            Integer idUsuario = usuariosMap.get(usuarioSeleccionado);
            Integer idMascota = mascotasMap.get(mascotaSeleccionada);
            String especie = especiesMap.get(idMascota);

            // Insertar en la base de datos
            String sql = "INSERT INTO ConsultasVeterinarias (Fecha, ID_Usuario, ID_Mascota, Especie, TipoConsulta, Peso, Observacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            // Convertir fecha (asumiendo formato dd/MM/yyyy)
            String fechaInput = txtFecha.getText().trim();
            if (!validarFecha(fechaInput)) {
                JOptionPane.showMessageDialog(this, 
                    "Formato de fecha inválido. Use DD/MM/YYYY\nEjemplo: 25/12/2020",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert date for SQL
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
                formatoEntrada.setLenient(false);
    
                // Validar el formato de fecha
                if (!validarFecha(fechaInput)) {
                    JOptionPane.showMessageDialog(this, 
                        "Formato de fecha inválido. Use DD/MM/YYYY\nEjemplo: 25/12/2020",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Convertir la fecha
                java.util.Date fechaUtil = formatoEntrada.parse(fechaInput);
                java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
                stmt.setDate(1, fechaSQL);
            } catch (java.text.ParseException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al procesar la fecha: " + e.getMessage(), 
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error inesperado al procesar la fecha: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
            
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idMascota);
            stmt.setString(4, especie);
            stmt.setString(5, (String) comboTipoConsulta.getSelectedItem());
            stmt.setDouble(6, peso);
            stmt.setString(7, txtObservacion.getText().trim());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener ID generado
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                int idConsulta = 0;
                if (generatedKeys.next()) {
                    idConsulta = generatedKeys.getInt(1);
                }
                
                // Agregar a la tabla
                modeloTabla.addRow(new Object[]{
                    idConsulta,
                    txtFecha.getText().trim(),
                    usuarioSeleccionado,
                    mascotaSeleccionada,
                    especie,
                    comboTipoConsulta.getSelectedItem(),
                    txtPeso.getText().trim(),
                    txtObservacion.getText().trim()
                });
                
                JOptionPane.showMessageDialog(this, "Consulta agregada con ID: " + idConsulta);
                limpiarFormulario();
            }
            
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar consulta: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para editar consulta
    
        
   private void editarConsulta() {
    int fila = tablaConsultas.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una consulta para editar.");
        return;
    }

    // Cargar datos en el formulario
    txtFecha.setText((String) modeloTabla.getValueAt(fila, 1));
    comboUsuario.setSelectedItem(modeloTabla.getValueAt(fila, 2));
    comboMascota.setSelectedItem(modeloTabla.getValueAt(fila, 3));
    txtEspecie.setText((String) modeloTabla.getValueAt(fila, 4));
    comboTipoConsulta.setSelectedItem(modeloTabla.getValueAt(fila, 5));
    txtPeso.setText(modeloTabla.getValueAt(fila, 6).toString());
    txtObservacion.setText((String) modeloTabla.getValueAt(fila, 7));

    // Cambiar acción del botón agregar temporalmente para editar
    btnAgregar.setEnabled(false);
    JButton btnGuardar = new JButton("Guardar Cambios");
    add(btnGuardar);

    btnGuardar.addActionListener(e -> {
        try {
            int idConsulta = (int) modeloTabla.getValueAt(fila, 0);
            
            // Validaciones
            String fechaInput = txtFecha.getText().trim();
            if (fechaInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La fecha es obligatoria");
                return;
            }

            if (txtPeso.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El peso es obligatorio");
                return;
            }

            // Validar peso
            double peso;
            try {
                peso = Double.parseDouble(txtPeso.getText().trim());
                if (peso <= 0) {
                    JOptionPane.showMessageDialog(this, "El peso debe ser un número positivo");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El peso debe ser un número válido");
                return;
            }

            // Validar y convertir fecha
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            formatoEntrada.setLenient(false);
            java.sql.Date fechaSQL;
            
            try {
                if (!validarFecha(fechaInput)) {
                    JOptionPane.showMessageDialog(this, 
                        "Formato de fecha inválido. Use DD/MM/YYYY\nEjemplo: 25/12/2020",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                java.util.Date fechaUtil = formatoEntrada.parse(fechaInput);
                fechaSQL = new java.sql.Date(fechaUtil.getTime());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar la fecha: " + ex.getMessage());
                return;
            }

            // Actualizar en base de datos
            String sql = "UPDATE ConsultasVeterinarias SET Fecha = ?, ID_Usuario = ?, " +
                        "ID_Mascota = ?, Especie = ?, TipoConsulta = ?, Peso = ?, " +
                        "Observacion = ? WHERE ID_Consulta = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, fechaSQL);
                stmt.setInt(2, usuariosMap.get(comboUsuario.getSelectedItem()));
                stmt.setInt(3, mascotasMap.get(comboMascota.getSelectedItem()));
                stmt.setString(4, txtEspecie.getText().trim());
                stmt.setString(5, (String) comboTipoConsulta.getSelectedItem());
                stmt.setDouble(6, peso);
                stmt.setString(7, txtObservacion.getText().trim());
                stmt.setInt(8, idConsulta);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    // Actualizar tabla
                    modeloTabla.setValueAt(fechaInput, fila, 1);
                    modeloTabla.setValueAt(comboUsuario.getSelectedItem(), fila, 2);
                    modeloTabla.setValueAt(comboMascota.getSelectedItem(), fila, 3);
                    modeloTabla.setValueAt(txtEspecie.getText(), fila, 4);
                    modeloTabla.setValueAt(comboTipoConsulta.getSelectedItem(), fila, 5);
                    modeloTabla.setValueAt(peso, fila, 6);
                    modeloTabla.setValueAt(txtObservacion.getText().trim(), fila, 7);

                    JOptionPane.showMessageDialog(this, "Consulta actualizada correctamente");
                    limpiarFormulario();
                    btnAgregar.setEnabled(true);
                    remove(btnGuardar);
                    revalidate();
                    repaint();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar consulta: " + ex.getMessage());
            ex.printStackTrace();
        }
    });

    revalidate();
    repaint();
}

    // Método para borrar consulta
    private void borrarConsulta() {
        int fila = tablaConsultas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta para borrar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar esta consulta?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int idConsulta = (int) modeloTabla.getValueAt(fila, 0);
                
                String sql = "DELETE FROM ConsultasVeterinarias WHERE ID_Consulta = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idConsulta);
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    modeloTabla.removeRow(fila);
                    JOptionPane.showMessageDialog(this, "Consulta eliminada correctamente");
                }
                
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar consulta: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Método para cargar consultas desde la base de datos
    private void cargarConsultasDesdeBD() {
        try {
            String sql = "SELECT c.ID_Consulta, c.Fecha, u.Nombre as UsuarioNombre, u.Apellido as UsuarioApellido, " +
                        "m.Nombre as MascotaNombre, c.Especie, c.TipoConsulta, c.Peso, c.Observacion " +
                        "FROM ConsultasVeterinarias c " +
                        "JOIN Usuarios u ON c.ID_Usuario = u.ID_Usuario " +
                        "JOIN Mascotas m ON c.ID_Mascota = m.ID_Mascota " +
                        "ORDER BY c.Fecha DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            modeloTabla.setRowCount(0);

            while (rs.next()) {
                String fecha = new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("Fecha"));
                String usuarioCompleto = rs.getString("UsuarioNombre") + " " + rs.getString("UsuarioApellido");
                
                modeloTabla.addRow(new Object[]{
                    rs.getInt("ID_Consulta"),
                    fecha,
                    usuarioCompleto,
                    rs.getString("MascotaNombre"),
                    rs.getString("Especie"),
                    rs.getString("TipoConsulta"),
                    rs.getDouble("Peso"),
                    rs.getString("Observacion")
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar consultas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para limpiar formulario
    private void limpiarFormulario() {
        txtFecha.setText("");
        txtPeso.setText("");
        txtObservacion.setText("");
        comboUsuario.setSelectedIndex(0);
        comboMascota.removeAllItems();
        txtEspecie.setText("");
        comboTipoConsulta.setSelectedIndex(0);
        tablaConsultas.clearSelection();
    }

    // Método para validar fecha en formato dd/MM/yyyy
   private boolean validarFecha(String fechaStr) {
    if (fechaStr == null || fechaStr.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "La fecha es obligatoria",
            "Error de validación",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // No permite fechas inválidas como 31/02/2023
        
        // Intenta parsear la fecha
        java.util.Date fecha = sdf.parse(fechaStr.trim());
        
        // Validar que la fecha no sea futura
        if (fecha.after(new java.util.Date())) {
            JOptionPane.showMessageDialog(this, 
                "La fecha de consulta no puede ser futura", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Extraer año para validación
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fecha);
        int year = cal.get(java.util.Calendar.YEAR);
        
        // Validar rango de años razonable (por ejemplo, últimos 5 años)
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if (year < (currentYear - 5) || year > currentYear) {
            JOptionPane.showMessageDialog(this,
                "La fecha debe estar dentro de los últimos 5 años",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que no sea más de 3 meses en el pasado
        java.util.Calendar tresMesesAtras = java.util.Calendar.getInstance();
        tresMesesAtras.add(java.util.Calendar.MONTH, -3);
        
        if (fecha.before(tresMesesAtras.getTime())) {
            JOptionPane.showMessageDialog(this,
                "La fecha no puede ser más antigua que 3 meses",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Formato de fecha inválido. Use DD/MM/YYYY\nEjemplo: " + 
            new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()),
            "Error de validación",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
}