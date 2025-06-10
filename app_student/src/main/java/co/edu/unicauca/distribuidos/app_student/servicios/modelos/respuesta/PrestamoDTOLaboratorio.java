package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

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
