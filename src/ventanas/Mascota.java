package ventanas;

import java.time.LocalDate;
import java.time.Period;

public class Mascota {
    protected int id;
    protected String nombre;
    protected String especie;
    protected String raza;
    protected LocalDate fechaNacimiento;
    protected String color;
    protected double peso;
    protected boolean activo;
    protected int idUsuario;

    public Mascota() {
        this.activo = true;
    }

    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento, 
                  String color, double peso, int idUsuario) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.color = color;
        this.peso = peso;
        this.activo = true;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    // Métodos específicos
     public String obtenerInformacion() {
        return "Mascota: " + nombre + " (" + especie + " - " + raza + ")";
    }

     public int calcularEdad() {
        if (fechaNacimiento == null) return -1;
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, hoy);
        return periodo.getYears();
    }
}


