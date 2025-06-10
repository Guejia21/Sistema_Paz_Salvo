package co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;



@Data
@AllArgsConstructor
//Existen diferentes tipos de prestamos o deudas que un estudiante puede tener, por lo que se utiliza un tipo genérico T para poder adaptarse a diferentes contextos.
public class RespuestaPazSalvoDTOArea<T> {
    private boolean pazYSalvo;
    private List<T> deudas;

    public RespuestaPazSalvoDTOArea() {
    }
}