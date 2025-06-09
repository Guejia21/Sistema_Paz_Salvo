package co.edu.unicauca.areafinanciera.capaAccesoDatos.modelo;

import lombok.Data;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Deuda {
    private int idEstudiante;
    private double monto;
    private String motivo;
    private LocalDate fechaGeneracion;
    private LocalDate fechaLimitePago;
    private String estado;

    public Deuda() {
    }
}
