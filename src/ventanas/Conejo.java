package ventanas;

import java.time.LocalDate;

public class Conejo extends Mascota {
    private boolean vacunadoMixomatosis;


    public Conejo(String nombre, String raza, LocalDate fechaNacimiento, 
                 String color, double peso, int idUsuario) {
        super(nombre, "Conejo", raza, fechaNacimiento, color, peso, idUsuario);
        this.vacunadoMixomatosis = false;
    }

    public boolean isVacunadoMixomatosis() { return vacunadoMixomatosis; }
    public void setVacunadoMixomatosis(boolean vacunado) { this.vacunadoMixomatosis = vacunado; }
    
}