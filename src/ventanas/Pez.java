package ventanas;

import java.time.LocalDate;

public class Pez extends Mascota {
    private String tipoAgua; // dulce o salada


    public Pez(String nombre, String raza, LocalDate fechaNacimiento, 
              String color, double peso, int idUsuario) {
        super(nombre, "Pez", raza, fechaNacimiento, color, peso, idUsuario);
    }

    public String getTipoAgua() { return tipoAgua; }
    public void setTipoAgua(String tipoAgua) { this.tipoAgua = tipoAgua; }
    
}