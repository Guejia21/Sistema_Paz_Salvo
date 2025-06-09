package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RespuestaSolicitudDTO {
    private String mensaje;
    // Necesito saber como se envia la respuesta por parte del orquestador
}
