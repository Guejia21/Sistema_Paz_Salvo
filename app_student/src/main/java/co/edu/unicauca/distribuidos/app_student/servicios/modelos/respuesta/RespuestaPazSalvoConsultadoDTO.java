package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import lombok.Data;

@Data
public class RespuestaPazSalvoConsultadoDTO {
    private RespuestaPazSalvoDTOArea<PrestamoDTODeportes> objAreaDeportes;
    private RespuestaPazSalvoDTOArea<PrestamoDTOLaboratorio> objAreaLaboratorios;
    private RespuestaPazSalvoDTOArea<DeudaDTOFinanciera> objAreaFinanciera;
    private boolean pazSalvo;
    private String mensaje;
}
