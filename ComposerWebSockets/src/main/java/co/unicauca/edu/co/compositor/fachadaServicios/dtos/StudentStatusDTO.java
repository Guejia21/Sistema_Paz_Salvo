package co.unicauca.edu.co.compositor.fachadaServicios.dtos;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class StudentStatusDTO {
    private String idEstudiante;
    private boolean estadoLaboratorios;
    private boolean estadoFinanciero;
    private boolean estadoDeportes;
    private boolean pazYSalvo;
    private String mensaje;
}
