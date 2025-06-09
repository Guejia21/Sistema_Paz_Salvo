package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentStatusDTO {
    private String idEstudiante;
    private boolean estadoLaboratorios;
    private boolean estadoFinanciero;
    private boolean estadoDeportes;
    private boolean pazYSalvo;
    private String mensaje;
}
