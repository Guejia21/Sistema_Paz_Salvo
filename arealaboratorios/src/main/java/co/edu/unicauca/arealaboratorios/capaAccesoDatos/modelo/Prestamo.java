package co.edu.unicauca.arealaboratorios.capaAccesoDatos.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Prestamo {
    private int idEstudiante;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private LocalDate fechaDevolucionReal;
    private String estado;
    private String equipoPrestado;

    public Prestamo() {
    }
}