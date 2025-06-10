package co.edu.unicauca.areafinanciera.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

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