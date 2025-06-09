package co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.PrestamoDTO;

@Data
@AllArgsConstructor
public class RespuestaPazSalvoDTO {
    private boolean pazYSalvo;
    private List<PrestamoDTO> prestamosPendientes;

    public RespuestaPazSalvoDTO() {
    }
}