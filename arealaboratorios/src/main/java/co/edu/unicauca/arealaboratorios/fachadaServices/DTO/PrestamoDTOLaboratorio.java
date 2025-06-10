package co.edu.unicauca.arealaboratorios.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PrestamoDTOLaboratorio {
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private String equipoPrestado;
    private String estado;

    public PrestamoDTOLaboratorio() {
    }
}