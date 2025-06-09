package co.unicauca.edu.co.compositor.fachadaServicios;

import co.unicauca.edu.co.compositor.client.AdminWebSocketClient;
import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IServicioDeportes;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IServicioFinanciero;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IServicioLaboratorios;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class PazYSalvoServiceImpl implements IPazYSalvoService {
    private final AdminWebSocketClient adminWebSocketClient;
    private final IServicioLaboratorios labService;
    private final IServicioFinanciero finService;
    private final IServicioDeportes depService;

    public PazYSalvoServiceImpl(
        AdminWebSocketClient adminWebSocketClient,
        IServicioLaboratorios labService,
        IServicioFinanciero finService,
        IServicioDeportes depService
    ) {
        this.adminWebSocketClient = adminWebSocketClient;
        this.labService = labService;
        this.finService = finService;
        this.depService = depService;
    }

    @PostConstruct
    public void init() {
        System.out.println("[PazYSalvoServiceImpl] Inicializando y conectando WebSocket...");
        adminWebSocketClient.conectar();
    }

    @Override
    public StudentStatusDTO verificarEstado(String idEstudiante) {
        System.out.println("[PazYSalvoServiceImpl] Verificando estado de paz y salvo para el estudiante: " + idEstudiante);
        boolean lab = labService.estaAPazYSalvo(idEstudiante);
        boolean fin = finService.estaAPazYSalvo(idEstudiante);
        boolean dep = depService.estaAPazYSalvo(idEstudiante);

        StudentStatusDTO dto = new StudentStatusDTO();
        dto.setIdEstudiante(idEstudiante);
        dto.setEstadoLaboratorios(lab);
        dto.setEstadoFinanciero(fin);
        dto.setEstadoDeportes(dep);
        dto.setPazYSalvo(lab && fin && dep);
        dto.setMensaje("El estudiante " + idEstudiante + " está " +
            (dto.isPazYSalvo() ? "a paz y salvo - Paz y salvo generado correctamente" : "con deudas pendientes - Paz y salvo no generado"));

        if (!dto.isPazYSalvo()) {
            System.out.println("[PazYSalvoServiceImpl] El estudiante NO está a paz y salvo. Intentando notificar...");
            adminWebSocketClient.notificar("/notificaciones/generales", dto);
        } else {
            System.out.println("[PazYSalvoServiceImpl] El estudiante está a paz y salvo. No se enviará notificación.");
        }

        return dto;
    }

    @Override
    public StudentStatusDTO consultarDeudas(String idEstudiante) {
        System.out.println("[PazYSalvoServiceImpl] Consultando deudas para el estudiante: " + idEstudiante);
        boolean lab = labService.estaAPazYSalvo(idEstudiante);
        boolean fin = finService.estaAPazYSalvo(idEstudiante);
        boolean dep = depService.estaAPazYSalvo(idEstudiante);
        StudentStatusDTO dto = new StudentStatusDTO();
        dto.setIdEstudiante(idEstudiante);
        dto.setEstadoLaboratorios(lab);
        dto.setEstadoFinanciero(fin);
        dto.setEstadoDeportes(dep);
        dto.setPazYSalvo(lab && fin && dep);

        if(!lab){
            dto.setMensaje("El estudiante " + idEstudiante + " tiene deudas pendientes en laboratorios.");
        }
        if (!fin) {
            dto.setMensaje("El estudiante " + idEstudiante + " tiene deudas pendientes financieras.");
        }
        if (!dep) {
            dto.setMensaje("El estudiante " + idEstudiante + " tiene deudas pendientes en deportes.");
        }
        if(dto.isPazYSalvo()) {
            dto.setMensaje("El estudiante " + idEstudiante + " está a paz y salvo.");
        }

        if (!dto.isPazYSalvo()) {
            System.out.println("[PazYSalvoServiceImpl] El estudiante tiene deudas. Intentando notificar...");
            adminWebSocketClient.notificar("/notificaciones/generales", dto);
        } else {
            System.out.println("[PazYSalvoServiceImpl] El estudiante está a paz y salvo. No se enviará notificación.");
        }
        return dto;
    }
}