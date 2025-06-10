package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeudaDTOFinanciera {
    private double monto;
    private String motivo;
    private LocalDate fechaGeneracion;
    private LocalDate fechaLimitePago;
    private String estado;

    public DeudaDTOFinanciera() {
    }
}
