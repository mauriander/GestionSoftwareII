package ventanas;

import java.time.LocalDate;

public class Hamster extends Mascota {
 
    private boolean tieneRueda;

    public Hamster(String nombre, String raza, LocalDate fechaNacimiento, 
                  String color, double peso, int idUsuario) {
        super(nombre, "Hámster", raza, fechaNacimiento, color, peso, idUsuario);
        this.tieneRueda = true;
    }
   
    public boolean isTieneRueda() { return tieneRueda; }
    public void setTieneRueda(boolean tieneRueda) { this.tieneRueda = tieneRueda; }
}