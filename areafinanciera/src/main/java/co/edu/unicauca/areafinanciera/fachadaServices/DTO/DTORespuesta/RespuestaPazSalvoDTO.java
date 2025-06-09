package co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DeudaDTO;

@Data
@AllArgsConstructor
public class RespuestaPazSalvoDTO {
    private boolean pazYSalvo;
    private List<DeudaDTO> deudasPendientes;

    public RespuestaPazSalvoDTO() {
    }
}