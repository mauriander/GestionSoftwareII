/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ventanas;

/**
 *
 * @author @mauriander
 */
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;

public class VentanaMascotas extends JFrame {
    private JTextField txtId, txtNombre, txtRaza,  txtColor, txtPeso;
    private JButton btnAgregar, btnEditar, btnCambiarEstado, btnLimpiar;
      private JTextField txtFechaNacimiento;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboActivo, comboEspecie, comboUsuario;
    
    private Connection conn;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Map<String, Integer> usuariosMap; // Para mapear nombre a ID_Usuario


    private JCheckBox chkVacunado, chkEsterilizado, chkPuedeVolar;
    private JTextField txtTemperamento, txtTipoManto, txtTipoHabitat, txtTipoAgua, txtLongitudCarapacho;
    private JPanel panelEspecifico; // Panel para campos específicos de cada especie
    
    
    public VentanaMascotas(Connection conn) {
        this.conn = conn;
        this.usuariosMap = new HashMap<>();
        
        setTitle("Gestión de Mascotas");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // Inicializar componentes
        txtId = new JTextField(5);
        txtNombre = new JTextField(10);
        txtRaza = new JTextField(10);
        //txtFechaNacimiento = new JTextField(10);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaNacimiento = new JTextField(10);
        txtColor = new JTextField(10);
        txtPeso = new JTextField(5);
        
        // ComboBox para especie, estado activo y usuario
        String[] especies = {"", "Perro", "Gato", "Conejo", "Hámster", "Pájaro", "Pez", "Tortuga", "Otro"};
        comboEspecie = new JComboBox<>(especies);
        comboActivo = new JComboBox<>(new String[]{"Sí", "No"});
        comboUsuario = new JComboBox<>();


           panelEspecifico = new JPanel(new FlowLayout());
        chkVacunado = new JCheckBox("Vacunado");
        chkEsterilizado = new JCheckBox("Esterilizado");
        chkPuedeVolar = new JCheckBox("Puede Volar");
        txtTemperamento = new JTextField(10);
        txtTipoManto = new JTextField(10);
        txtTipoHabitat = new JTextField(10);
        txtTipoAgua = new JTextField(10);
        txtLongitudCarapacho = new JTextField(5);

          // Agregar listener al combobox de especies
        comboEspecie.addActionListener(e -> actualizarCamposEspecificos());
        
        // Agregar el panel específico después de los campos comunes
        add(panelEspecifico);
        
        
        // Cargar usuarios en el ComboBox
        cargarUsuariosEnComboBox();

        // Inicializar botones
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnCambiarEstado = new JButton("Cambiar Estado");
        btnLimpiar = new JButton("Limpiar");


        // Agregar componentes al frame
        add(new JLabel("ID:")); add(txtId);
        txtId.setEditable(false);
        add(new JLabel("Especie:")); add(comboEspecie);
        add(new JLabel("Nombre:")); add(txtNombre);        
        add(new JLabel("Raza:")); add(txtRaza);
        add(new JLabel("Fecha Nacimiento (DD/MM/YYYY):")); add(txtFechaNacimiento);
        add(new JLabel("Color:")); add(txtColor);
        add(new JLabel("Peso (kg):")); add(txtPeso);
        add(new JLabel("Activo:")); add(comboActivo);
        add(new JLabel("Dueño:")); add(comboUsuario);

        // Configurar tabla
        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Edad", "Color", "Peso", "Estado", "Dueño"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };
        tablaMascotas = new JTable(modeloTabla);

        // Agregar botones y tabla
        add(btnAgregar);
        add(btnEditar);
        add(btnCambiarEstado);
        add(new JScrollPane(tablaMascotas));
        add(btnLimpiar);
        

        btnEditar.setEnabled(false);
        btnAgregar.setEnabled(true);

        // Configurar acciones de los botones
        btnAgregar.addActionListener(e -> agregarMascota());
        btnEditar.addActionListener(e -> editarMascota());
        btnCambiarEstado.addActionListener(e -> cambiarEstadoMascota());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        // Configurar selección de tabla
      tablaMascotas.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int selectedRow = tablaMascotas.getSelectedRow();
        btnEditar.setEnabled(selectedRow != -1);
        btnAgregar.setEnabled(selectedRow == -1);
        if (selectedRow != -1) {
            cargarDatosSeleccionados();
        }
    }
});

// Add mouse listener to the frame to handle clicks outside the table
  this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMascotas.clearSelection();
                btnAgregar.setEnabled(true);
                btnEditar.setEnabled(false);
            }
        });
         btnLimpiar.addActionListener(e -> limpiarFormulario());


        // Cargar datos iniciales
        cargarMascotasDesdeBD();
        setVisible(true);
    }

 private void actualizarCamposEspecificos() {
        panelEspecifico.removeAll();
        String especieSeleccionada = (String) comboEspecie.getSelectedItem();
        switch(especieSeleccionada) {
            case "Perro":
                panelEspecifico.add(chkEsterilizado);
                break;
            case "Gato":
                panelEspecifico.add(chkEsterilizado);
                break;
            case "Pájaro":
                panelEspecifico.add(chkPuedeVolar);
                break;
            case "Pez":
                panelEspecifico.add(new JLabel("Tipo de Agua:"));
                panelEspecifico.add(txtTipoAgua);
                break;
            case "Tortuga":
                panelEspecifico.add(new JLabel("Longitud Caparazón (cm):"));
                panelEspecifico.add(txtLongitudCarapacho);
                break;
        }
        
        panelEspecifico.revalidate();
        panelEspecifico.repaint();
    }
    
      // Método para cargar usuarios en el ComboBox
    private void cargarUsuariosEnComboBox() {
        try {
            String sql = "SELECT ID_Usuario, Nombre, Apellido FROM Usuarios WHERE Activo = TRUE ORDER BY Nombre, Apellido";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            comboUsuario.removeAllItems();
            usuariosMap.clear();
            
            // Agregar opción vacía
            comboUsuario.addItem("-- Seleccionar Dueño --");
            
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
    
    // Método para obtener el ID del usuario seleccionado
    private Integer obtenerIdUsuarioSeleccionado() {
        String usuarioSeleccionado = (String) comboUsuario.getSelectedItem();
        if (usuarioSeleccionado == null || usuarioSeleccionado.equals("-- Seleccionar Dueño --")) {
            return null;
        }
        return usuariosMap.get(usuarioSeleccionado);
    }
    
    // Método para obtener el nombre del usuario por ID
    private String obtenerNombreUsuarioPorId(int idUsuario) {
        try {
            String sql = "SELECT Nombre, Apellido FROM Usuarios WHERE ID_Usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("Nombre") + " " + rs.getString("Apellido");
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Usuario no encontrado";
    }
    
    // Método para calcular la edad a partir de la fecha de nacimiento
    private String calcularEdad(String fechaNacimientoStr) {
        if (fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty()) {
            return "Desconocida";
        }
        
        try {
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, dateFormatter);
            LocalDate fechaActual = LocalDate.now();
            Period periodo = Period.between(fechaNacimiento, fechaActual);
            
            int años = periodo.getYears();
            int meses = periodo.getMonths();
            
            if (años == 0 && meses == 0) {
                return "Recién nacido";
            } else if (años == 0) {
                return meses + " mes" + (meses > 1 ? "es" : "");
            } else if (meses == 0) {
                return años + " año" + (años > 1 ? "s" : "");
            } else {
                return años + " año" + (años > 1 ? "s" : "") + " y " + meses + " mes" + (meses > 1 ? "es" : "");
            }
        } catch (Exception e) {
            return "Fecha inválida";
        }
    }
    
    private boolean validarFecha(String fechaStr) {
    if (fechaStr == null || fechaStr.trim().isEmpty()) {
        return false; // Fecha es obligatoria
    }
    
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // No permite fechas inválidas como 31/02/2023
        // Intenta parsear la fecha
        java.util.Date fecha = sdf.parse(fechaStr.trim());
        
        // Validar que la fecha no sea futura
        if (fecha.after(new java.util.Date())) {
            JOptionPane.showMessageDialog(this, 
                "La fecha de nacimiento no puede ser futura", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Extraer año para validación
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fecha);
        int year = cal.get(java.util.Calendar.YEAR);
        
        // Validar rango de años razonable (por ejemplo, últimos 30 años)
        if (year < 1990 || year > java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)) {
            JOptionPane.showMessageDialog(this,
                "El año debe estar entre 1990 y el presente",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Formato de fecha inválido. Use DD/MM/YYYY\nEjemplo: 25/12/2020",
            "Error de validación",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
    
    private void agregarMascota() {
   try {
        // Validaciones básicas
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es un campo obligatorio");
            return;
        }

        // Validar fecha
        String fechaInput = txtFechaNacimiento.getText().trim();
        if (!validarFecha(fechaInput)) {
            return; // El mensaje de error ya se muestra en validarFecha()
        }
        
        Integer idUsuario = obtenerIdUsuarioSeleccionado();
            if (idUsuario == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un dueño para la mascota");
                return;
            }

        // Convertir fecha
            // Convertir fecha (asumiendo formato dd/MM/yyyy)
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fechaUtil = formatoEntrada.parse(txtFechaNacimiento.getText().trim());
            java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());

        // Convert decimal separator from comma to dot
        String pesoStr = txtPeso.getText().trim().replace(",", ".");
        double peso = pesoStr.isEmpty() ? 0.0 : Double.parseDouble(pesoStr);

        // Prepare SQL with proper date format
        String sql = "INSERT INTO Mascotas (Nombre, Especie, Raza, FechaNacimiento, " +
                    "Color, Peso, Activo, ID_Usuario) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, txtNombre.getText().trim());
        stmt.setString(2, comboEspecie.getSelectedItem().toString());
        stmt.setString(3, txtRaza.getText().trim());
        stmt.setDate(4, fechaSQL);
        stmt.setString(5, txtColor.getText().trim());
        stmt.setDouble(6, peso);
        stmt.setBoolean(7, true); // Active by default (-1 in Access represents True)
        stmt.setInt(8, obtenerIdUsuarioSeleccionado());

        int filasAfectadas = stmt.executeUpdate();
        
        if (filasAfectadas > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idGenerado = generatedKeys.getInt(1);
                JOptionPane.showMessageDialog(this, "Mascota agregada con ID: " + idGenerado);
            }
            
            limpiarFormulario();
            cargarMascotasDesdeBD();
        }
        
        stmt.close();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al agregar mascota: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
    private void editarMascota() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota de la tabla para editar");
            return;
        }
        
        try {
            // Validar campos obligatorios
              if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es un campo obligatorio");
            return;       
           
        }

        // Validar fecha
        //String fechaInput = txtFechaNacimiento.getText().trim();
       
            // Validar que se haya seleccionado un usuario
            Integer idUsuario = obtenerIdUsuarioSeleccionado();
            if (idUsuario == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un dueño para la mascota");
                return;
            }
            
            // Convertir fecha
            String fechaInput = txtFechaNacimiento.getText().trim();
            java.sql.Date fechaSQL = null;
            if (!fechaInput.isEmpty()) {
                try {
                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
                    formatoEntrada.setLenient(false);
                    java.util.Date fechaUtil = formatoEntrada.parse(fechaInput);
                    fechaSQL = new java.sql.Date(fechaUtil.getTime());
                   // stmt.setDate(4, fechaSQL);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Formato de fecha inválido. Use DD/MM/YYYY\nEjemplo: 25/12/2020",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                //stmt.setNull(4, java.sql.Types.DATE);
            }
            
              
            if (!validarFecha(fechaInput)) {
            return; // El mensaje de error ya se muestra en validarFecha()
                }
            
            // Validar y convertir peso
            double peso = 0.0;
            if (!txtPeso.getText().trim().isEmpty()) {
                try {
                    peso = Double.parseDouble(txtPeso.getText().trim().replace(",", "."));
                    if (peso <= 0) {
                        JOptionPane.showMessageDialog(this, "El peso debe ser un número positivo");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El peso debe ser un número decimal válido");
                    return;
                }
            }
            
            // Obtener ID de la mascota seleccionada
            int idMascota = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            // Preparar la consulta SQL de actualización
            String sql = "UPDATE Mascotas SET Nombre = ?, Especie = ?, Raza = ?, FechaNacimiento = ?, " +
                    "Color = ?, Peso = ?, Activo = ?, ID_Usuario = ? WHERE ID_Mascota = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, txtNombre.getText().trim());
            stmt.setString(2, comboEspecie.getSelectedItem().toString());
            stmt.setString(3, txtRaza.getText().trim());            
            
            // Manejar la fecha            
            if (fechaSQL != null) {
                stmt.setDate(4, fechaSQL);
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }            
            stmt.setString(5, txtColor.getText().trim());
            
            // Manejar el peso
            if (txtPeso.getText().trim().isEmpty()) {
                stmt.setNull(6, java.sql.Types.DECIMAL);
            } else {
                stmt.setDouble(6, peso);
            }
            
            stmt.setBoolean(7, comboActivo.getSelectedItem().equals("Sí"));
            stmt.setInt(8, idUsuario);
            stmt.setInt(9, idMascota);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Mascota actualizada correctamente");
                cargarMascotasDesdeBD();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la mascota");
            }
            
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al editar mascota: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void cambiarEstadoMascota() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila != -1) {
            try {
                int idMascota = (int) modeloTabla.getValueAt(fila, 0);
                String estadoActual = (String) modeloTabla.getValueAt(fila, 7);
                String nuevoEstado = estadoActual.equals("Activo") ? "Inactivo" : "Activo";
                
                // Actualizar en la base de datos
                String sql = "UPDATE Mascotas SET Activo = ? WHERE ID_Mascota = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setBoolean(1, nuevoEstado.equals("Activo"));
                stmt.setInt(2, idMascota);
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    // Actualizar la tabla
                    modeloTabla.setValueAt(nuevoEstado, fila, 7);
                    JOptionPane.showMessageDialog(this, "Estado actualizado correctamente");
                }
                
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota para cambiar el estado.");
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int idMascota = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                
                // Cargar datos completos de la mascota desde la base de datos
                String sql = "SELECT * FROM Mascotas WHERE ID_Mascota = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idMascota);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    txtId.setText(String.valueOf(rs.getInt("ID_Mascota")));
                    txtNombre.setText(rs.getString("Nombre"));
                    
                    // Especie (ComboBox)
                    String especie = rs.getString("Especie");
                    if (especie != null) {
                        comboEspecie.setSelectedItem(especie);
                    } else {
                        comboEspecie.setSelectedIndex(0);
                    }
                    
                    txtRaza.setText(rs.getString("Raza"));
                    
                    // Fecha de nacimiento
                  java.sql.Date fechaBD = rs.getDate("FechaNacimiento");
                    if (fechaBD != null) {
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        txtFechaNacimiento.setText(formato.format(fechaBD));
                    } else {
                        txtFechaNacimiento.setText("");
                            }
                    txtColor.setText(rs.getString("Color"));
                    
                    double peso = rs.getDouble("Peso");
                    if (!rs.wasNull()) {
                        txtPeso.setText(String.valueOf(peso));
                    } else {
                        txtPeso.setText("");
                    }
                    
                    comboActivo.setSelectedItem(rs.getBoolean("Activo") ? "Sí" : "No");
                    
                    // Usuario dueño
                    int idUsuario = rs.getInt("ID_Usuario");
                    if (!rs.wasNull()) {
                        String nombreUsuario = obtenerNombreUsuarioPorId(idUsuario);
                        comboUsuario.setSelectedItem(nombreUsuario);
                    } else {
                        comboUsuario.setSelectedIndex(0);
                    }
                }
                
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar datos de la mascota: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void cargarMascotasDesdeBD() {
        try {
            String sql = "SELECT * FROM Mascotas LEFT JOIN Usuarios ON Mascotas.ID_Usuario = Usuarios.ID_Usuario";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            modeloTabla.setRowCount(0);

            while (rs.next()) {
                // Convert date format from YYYY-MM-DD to DD/MM/YY
                java.sql.Date fechaBD = rs.getDate("FechaNacimiento");
                String fechaFormateada = "";
               if (fechaBD != null) {
    LocalDate fecha = fechaBD.toLocalDate();
    fechaFormateada = String.format("%02d-%02d-%04d", 
        fecha.getDayOfMonth(), 
        fecha.getMonthValue(), 
        fecha.getYear());
}

                // Convert boolean (-1/0) to "Activo"/"Inactivo"
                boolean activo = rs.getBoolean("Activo");
                String estadoStr = activo ? "Activo" : "Inactivo";

                // Format peso with comma as decimal separator
                double peso = rs.getDouble("Peso");
                String pesoStr = String.format("%.2f", peso).replace(".", ",");

                modeloTabla.addRow(new Object[]{
                    rs.getInt("ID_Mascota"),
                    rs.getString("Nombre"),
                    rs.getString("Especie"),
                    rs.getString("Raza"),
                    fechaFormateada,
                    rs.getString("Color"),
                    pesoStr,
                    estadoStr,
                    rs.getInt("ID_Usuario")
                });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mascotas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
   private void limpiarFormulario() {
    txtId.setText("");
    txtNombre.setText("");
    comboEspecie.setSelectedIndex(0);
    txtRaza.setText("");
    txtFechaNacimiento.setText("");
    txtColor.setText("");
    txtPeso.setText("");
    comboActivo.setSelectedItem("Sí");
    comboUsuario.setSelectedIndex(0);
    tablaMascotas.clearSelection();
    
    // Reset button states
    btnAgregar.setEnabled(true);
    btnEditar.setEnabled(false);
    
    // Clear specific fields panel
    panelEspecifico.removeAll();
    panelEspecifico.revalidate();
    panelEspecifico.repaint();
}
}