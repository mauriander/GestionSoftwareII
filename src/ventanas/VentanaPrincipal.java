package ventanas;
/**
 *
 * @author @mauriander
 */
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import ventanas.VentanaMascotas;
import ventanas.VentanaUsuarios;
import ventanas.VentanaVeterinaria;

public class VentanaPrincipal extends JFrame {
    private Connection conn;
    
    public VentanaPrincipal(Connection conn) {
            this.conn = conn;
        // luego podés pasarla a VentanaUsuarios
        
        setTitle("Veterinaria - Menú Principal");
        setSize(1200, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Cargar imágenes
        ImageIcon iconUsuarios = new ImageIcon(getClass().getResource("/images/usuario48.png"));
        ImageIcon iconMascotas = new ImageIcon(getClass().getResource("/images/mascota48.png"));
        ImageIcon iconConsultas = new ImageIcon(getClass().getResource("/images/consulta48.png"));
        ImageIcon iconAyuda = new ImageIcon(getClass().getResource("/images/ayuda.png"));

        

        // Botones con íconos
        JButton btnUsuarios = new JButton("Gestionar Usuarios", iconUsuarios);
        JButton btnMascotas = new JButton("Gestionar Mascotas", iconMascotas);
        JButton btnConsultas = new JButton("Gestionar Consultas", iconConsultas);
        JButton btnAyuda = new JButton("Ayuda", iconAyuda);
        btnAyuda.setBackground(Color.blue);

        
           

        btnUsuarios.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUsuarios.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnMascotas.setHorizontalTextPosition(SwingConstants.CENTER);
        btnMascotas.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnConsultas.setHorizontalTextPosition(SwingConstants.CENTER);
        btnConsultas.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        btnAyuda.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAyuda.setVerticalTextPosition(SwingConstants.BOTTOM);
       
        btnUsuarios.addActionListener(e -> new VentanaUsuarios(conn));
        btnMascotas.addActionListener(e -> new VentanaMascotas(conn));
        btnConsultas.addActionListener(e -> new VentanaVeterinaria(conn));
        btnAyuda.addActionListener(e ->abrirManualUsuario() );
        
        add(btnUsuarios);
        add(btnMascotas);
        add(btnConsultas);
        add(btnAyuda);

        setVisible(true);
    }

private void abrirManualUsuario() {
    try {
        // Ruta relativa al archivo PDF
        String pdfPath = "images/ManualVet.pdf";
        File pdfFile = new File(pdfPath);
        
        // Verificar si el archivo existe
        if (pdfFile.exists()) {
            // Abrir el PDF con el programa predeterminado del sistema
            Desktop.getDesktop().open(pdfFile);
        } else {
            // Si no encuentra en la ruta relativa, buscar en el classpath
            InputStream pdfStream = getClass().getClassLoader().getResourceAsStream("images/ManualVet.pdf");
            if (pdfStream != null) {
                // Crear archivo temporal
                File tempFile = File.createTempFile("ManualVet", ".pdf");
                tempFile.deleteOnExit();
                
                // Copiar el stream al archivo temporal
                Files.copy(pdfStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Desktop.getDesktop().open(tempFile);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo encontrar el manual de usuario.\n" +
                    "Asegúrese de que el archivo 'ManualVet.pdf' esté en la carpeta 'images'", 
                    "Archivo no encontrado", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al abrir el manual de usuario: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } catch (UnsupportedOperationException ex) {
        JOptionPane.showMessageDialog(this, 
            "No se puede abrir el PDF. No hay una aplicación asociada para abrir archivos PDF.", 
            "Operación no soportada", 
            JOptionPane.WARNING_MESSAGE);
    }
}
}
