package co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.PrestamoDTOLaboratorio;

@Data
@AllArgsConstructor
public class RespuestaPazSalvoDTOLaboratorios {
    private boolean pazYSalvo;
    private List<PrestamoDTOLaboratorio> deudas;

    public RespuestaPazSalvoDTOLaboratorios() {
    }
}