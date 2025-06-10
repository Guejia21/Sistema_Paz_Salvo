package co.edu.unicauca.distribuidos.app_student.servicios.modelos.peticion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PeticionPazSalvoDTO {
    private int idEstudiante;
    private String nombreEstudiante;

    public PeticionPazSalvoDTO() {
    }
}
