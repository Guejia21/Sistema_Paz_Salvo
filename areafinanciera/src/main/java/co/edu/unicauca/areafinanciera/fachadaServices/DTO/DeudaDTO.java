package co.edu.unicauca.areafinanciera.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DeudaDTO {
    private double monto;
    private String motivo;
    private LocalDate fechaGeneracion;
    private LocalDate fechaLimitePago;
    private String estado;

    public DeudaDTO() {
    }
}