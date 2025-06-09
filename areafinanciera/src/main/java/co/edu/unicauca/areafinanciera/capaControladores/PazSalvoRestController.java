package co.edu.unicauca.areafinanciera.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.areafinanciera.fachadaServices.SolicitudPazSalvoInt;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTO;

@RestController
@RequestMapping("/api/financiera")
public class PazSalvoRestController {
    @Autowired
    private SolicitudPazSalvoInt servicio;

    @PostMapping("/pazsalvo")
    public RespuestaPazSalvoDTO consultarPazYSalvo(@RequestBody PeticionPazSalvoDTO peticion) {
        System.out.println("Consultando paz y salvo para el estudiante con ID: " + peticion.getIdEstudiante());
        RespuestaPazSalvoDTO respuesta = servicio.consultarPazYSalvo(peticion);
        return respuesta;
    }

    @DeleteMapping("/pazsalvo/{id}")
    public String eliminarPendientes(@PathVariable("id") int idEstudiante) {
        System.out.println("Eliminando deudas pendientes para el estudiante con ID: " + idEstudiante);
        boolean eliminado = servicio.eliminarPendientes(idEstudiante);
        return eliminado ? "Deudas eliminadas correctamente." : "No hay deudas por eliminar.";
    }
}