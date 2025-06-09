package co.edu.unicauca.arealaboratorios.capaAccesoDatos.repositorio;

import co.edu.unicauca.arealaboratorios.capaAccesoDatos.modelo.Prestamo;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PrestamoRepositorioLaboratorio {

    private final List<Prestamo> prestamos = new ArrayList<>();

    public PrestamoRepositorioLaboratorio() {
        // Estudiante 1001 - Tiene préstamo activo y otro devuelto
        prestamos.add(new Prestamo(1001, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 7), null, "activo", "Microscopio"));
        prestamos.add(new Prestamo(1001, LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 17), LocalDate.of(2025, 4, 16), "devuelto", "Osciloscopio"));

        // Estudiante 1002 - Todo devuelto
        prestamos.add(new Prestamo(1002, LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 9), "devuelto", "Computador de laboratorio"));

        // Estudiante 1003 - Tiene préstamo vencido
        prestamos.add(new Prestamo(1003, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 5), null, "vencido", "Multímetro"));

        // Estudiante 1004 - Sin préstamos (paz y salvo aquí)
        // (no se agrega ningún registro)

        // Estudiante 1005 - Tiene un préstamo activo
        prestamos.add(new Prestamo(1005, LocalDate.of(2025, 5, 6), LocalDate.of(2025, 5, 12), null, "activo", "Fuente de poder"));

        // Estudiante 1006 - Todo devuelto
        prestamos.add(new Prestamo(1006, LocalDate.of(2025, 5, 8), LocalDate.of(2025, 5, 15), LocalDate.of(2025, 5, 14), "devuelto", "Osciloscopio"));
    }

    /**
     * Obtiene los préstamos con estado "activo" o "vencido" (no devueltos) de un estudiante.
     */
    public List<Prestamo> obtenerPrestamosNoDevueltosPorEstudiante(int idEstudiante) {
        return prestamos.stream()
                .filter(p -> p.getIdEstudiante() == idEstudiante &&
                            (p.getEstado().equalsIgnoreCase("activo") || p.getEstado().equalsIgnoreCase("vencido")))
                .collect(Collectors.toList());
    }

    /**
     * Elimina los préstamos no devueltos de un estudiante (activo o vencido).
     */
    public void eliminarPrestamosNoDevueltosPorEstudiante(int idEstudiante) {
        prestamos.removeIf(p -> p.getIdEstudiante() == idEstudiante &&
                                (p.getEstado().equalsIgnoreCase("activo") || p.getEstado().equalsIgnoreCase("vencido")));
    }

    public List<Prestamo> obtenerTodos() {
        return prestamos;
    }
}