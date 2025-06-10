package co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DeudaDTOFinanciera;

@Data
@AllArgsConstructor
public class RespuestaPazSalvoDTOFinanciera {
    private boolean pazYSalvo;
    private List<DeudaDTOFinanciera> deudas;

    public RespuestaPazSalvoDTOFinanciera() {
    }
}