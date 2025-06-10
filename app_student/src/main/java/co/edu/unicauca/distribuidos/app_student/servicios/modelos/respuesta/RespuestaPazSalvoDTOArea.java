package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaPazSalvoDTOArea<T> {
    private boolean pazYSalvo;
    private List<T> deudas;

    public RespuestaPazSalvoDTOArea() {
    }
}
