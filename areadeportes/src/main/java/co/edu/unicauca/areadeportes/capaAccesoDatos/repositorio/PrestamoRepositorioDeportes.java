package co.edu.unicauca.areadeportes.capaAccesoDatos.repositorio;

import co.edu.unicauca.areadeportes.capaAccesoDatos.modelo.Prestamo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PrestamoRepositorioDeportes {

    private final List<Prestamo> prestamos = new ArrayList<>();

    public PrestamoRepositorioDeportes() {
        // Datos precargados de ejemplo
        // Estudiante 1001 - Tiene 2 pendientes (no está a paz y salvo)
        prestamos.add(new Prestamo(1001, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 7), null, "Balón"));
        prestamos.add(new Prestamo(1001, LocalDate.of(2025, 5, 3), LocalDate.of(2025, 5, 10), null, "Uniforme"));

        // Estudiante 1002 - Todo devuelto (está a paz y salvo)
        prestamos.add(new Prestamo(1002, LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 9), LocalDate.of(2025, 5, 8), "Raqueta"));

        // Estudiante 1003 - Tiene 1 pendiente
        prestamos.add(new Prestamo(1003, LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 10), null, "Balón medicinal"));

        // Estudiante 1004 - Todo devuelto (está a paz y salvo)
        prestamos.add(new Prestamo(1004, LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 11), LocalDate.of(2025, 5, 10), "Red de voleibol"));

        // Estudiante 1005 - 1 devuelto, 1 pendiente
        prestamos.add(new Prestamo(1005, LocalDate.of(2025, 5, 6), LocalDate.of(2025, 5, 12), null, "Petos"));
        prestamos.add(new Prestamo(1005, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 4), "Balón"));

        // Estudiante 1006 - Todo devuelto
        prestamos.add(new Prestamo(1006, LocalDate.of(2025, 5, 8), LocalDate.of(2025, 5, 15), LocalDate.of(2025, 5, 15), "Raqueta de tenis"));
    }

    /**
     * Consulta los préstamos no devueltos por un estudiante.
     */
    public List<Prestamo> obtenerPrestamosNoDevueltosPorEstudiante(int idEstudiante) {
        return prestamos.stream()
                .filter(p -> p.getIdEstudiante() == idEstudiante && p.getFechaDevolucionReal() == null)
                .collect(Collectors.toList());
    }

    /**
     * Elimina todos los préstamos no devueltos de un estudiante.
     */
    public void eliminarPrestamosNoDevueltosPorEstudiante(int idEstudiante) {
        prestamos.removeIf(p -> p.getIdEstudiante() == idEstudiante && p.getFechaDevolucionReal() == null);
    }

    public List<Prestamo> obtenerTodos() {
        return prestamos;
    }
}
