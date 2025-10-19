package ventanas;

import java.time.LocalDate;

public class Perro extends Mascota {
    private boolean esterilizado;
   
    public Perro(String nombre, String raza, LocalDate fechaNacimiento, 
                String color, double peso, int idUsuario) {
        super(nombre, "Perro", raza, fechaNacimiento, color, peso, idUsuario);
        this.esterilizado = false;
    }

       public boolean isEsterilizado() { return esterilizado; }
    public void setEsterilizado(boolean esterilizado) { this.esterilizado = esterilizado; }
    


    @Override
    public String obtenerInformacion() {
        return super.obtenerInformacion() + 
               "\nVacunado: " + (esterilizado ? "Sí" : "No");
    }
}