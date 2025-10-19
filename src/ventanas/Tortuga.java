package ventanas;

import java.time.LocalDate;

public class Tortuga extends Mascota {
    private String tipoHabitat; // terrestre o acuática
    private double longitudCarapacho;

    public Tortuga(String nombre, String raza, LocalDate fechaNacimiento, 
                  String color, double peso, int idUsuario) {
        super(nombre, "Tortuga", raza, fechaNacimiento, color, peso, idUsuario);
    }

    public String getTipoHabitat() { return tipoHabitat; }
    public void setTipoHabitat(String tipoHabitat) { this.tipoHabitat = tipoHabitat; }
    
    public double getLongitudCarapacho() { return longitudCarapacho; }
    public void setLongitudCarapacho(double longitud) { this.longitudCarapacho = longitud; }
}