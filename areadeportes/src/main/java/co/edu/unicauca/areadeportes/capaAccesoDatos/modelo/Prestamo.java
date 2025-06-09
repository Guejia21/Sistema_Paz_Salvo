package co.edu.unicauca.areadeportes.capaAccesoDatos.modelo;

import lombok.Data;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Prestamo {
    private int idEstudiante;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private LocalDate fechaDevolucionReal;
    private String elementoPrestado;

    public Prestamo() {
    }
}
