package co.edu.unicauca.areadeportes.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PrestamoDTO {
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private String elementoPrestado;

    public PrestamoDTO() {
    }
}