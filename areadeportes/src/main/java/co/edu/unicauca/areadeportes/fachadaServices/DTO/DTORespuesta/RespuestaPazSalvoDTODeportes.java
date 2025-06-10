package co.edu.unicauca.areadeportes.fachadaServices.DTO.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

import co.edu.unicauca.areadeportes.fachadaServices.DTO.PrestamoDTODeportes;

@Data
@AllArgsConstructor
public class RespuestaPazSalvoDTODeportes {
    private boolean pazYSalvo;
    private List<PrestamoDTODeportes> deudas;

    public RespuestaPazSalvoDTODeportes() {
    }
}