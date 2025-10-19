package ventanas;

import java.time.LocalDate;

public class Gato extends Mascota {
    private boolean esterilizado;
  

    public Gato(String nombre, String raza, LocalDate fechaNacimiento, 
                String color, double peso, int idUsuario) {
        super(nombre, "Gato", raza, fechaNacimiento, color, peso, idUsuario);
        this.esterilizado = false;
    }

    public boolean isEsterilizado() { return esterilizado; }
    public void setEsterilizado(boolean esterilizado) { this.esterilizado = esterilizado; }
    

}