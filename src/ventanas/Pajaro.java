package ventanas;

import java.time.LocalDate;

public class Pajaro extends Mascota {
    private boolean puedeVolar;
   

    public Pajaro(String nombre, String raza, LocalDate fechaNacimiento, 
                 String color, double peso, int idUsuario) {
        super(nombre, "Pájaro", raza, fechaNacimiento, color, peso, idUsuario);
        this.puedeVolar = true;
    }

    public boolean isPuedeVolar() { return puedeVolar; }
    public void setPuedeVolar(boolean puedeVolar) { this.puedeVolar = puedeVolar; }
    
  
}