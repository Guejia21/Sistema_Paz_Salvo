package co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrestamoDTODeportes {
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private String elementoPrestado;

    public PrestamoDTODeportes() {
    }
}
