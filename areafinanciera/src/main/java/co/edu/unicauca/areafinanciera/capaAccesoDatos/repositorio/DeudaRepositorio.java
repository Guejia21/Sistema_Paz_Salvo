package co.edu.unicauca.areafinanciera.capaAccesoDatos.repositorio;

import co.edu.unicauca.areafinanciera.capaAccesoDatos.modelo.Deuda;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeudaRepositorio {

    private final List<Deuda> deudas = new ArrayList<>();

    public DeudaRepositorio() {
        // Estudiante 1001 - Deuda pendiente y deuda ya pagada
        deudas.add(new Deuda(1001, 150_000, "Mora en matrícula", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10), "pendiente"));
        deudas.add(new Deuda(1001, 80_000, "Pérdida de material", LocalDate.of(2025, 4, 20), LocalDate.of(2025, 5, 5), "pagada"));

        // Estudiante 1002 - Todo pagado
        deudas.add(new Deuda(1002, 100_000, "Pérdida de material", LocalDate.of(2025, 3, 10), LocalDate.of(2025, 4, 1), "pagada"));
        deudas.add(new Deuda(1002, 50_000, "Retraso en matrícula", LocalDate.of(2025, 2, 15), LocalDate.of(2025, 3, 1), "pagada"));

        // Estudiante 1003 - Tiene deuda en mora y otra pagada
        deudas.add(new Deuda(1003, 70_000, "Mora en matrícula", LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 12), "en mora"));
        deudas.add(new Deuda(1003, 60_000, "Pérdida de material", LocalDate.of(2025, 3, 5), LocalDate.of(2025, 3, 20), "pagada"));

        // Estudiante 1004 - Sin deudas
        // No se agregan registros

        // Estudiante 1005 - Todo pagado
        deudas.add(new Deuda(1005, 90_000, "Pérdida de uniforme", LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 20), "pagada"));

        // Estudiante 1006 - Deuda pendiente
        deudas.add(new Deuda(1006, 110_000, "Mora en matrícula", LocalDate.of(2025, 5, 3), LocalDate.of(2025, 5, 15), "pendiente"));
    }

    public List<Deuda> obtenerDeudasNoPagadasPorEstudiante(int idEstudiante) {
        return deudas.stream()
                .filter(d -> d.getIdEstudiante() == idEstudiante && !d.getEstado().equalsIgnoreCase("pagada"))
                .collect(Collectors.toList());
    }

    public void eliminarDeudasNoPagadasPorEstudiante(int idEstudiante) {
        deudas.removeIf(d -> d.getIdEstudiante() == idEstudiante && !d.getEstado().equalsIgnoreCase("pagada"));
    }

    public List<Deuda> obtenerTodas() {
        return deudas;
    }
}