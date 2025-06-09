package co.edu.unicauca.areadeportes.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.areadeportes.fachadaServices.SolicitudPazSalvoInt;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTO;

@RestController
@RequestMapping("/api/deporte")
public class PazSalvoRestController {
    @Autowired
    private SolicitudPazSalvoInt solicitudPazSalvo;

    @PostMapping("/pazsalvo")
    public RespuestaPazSalvoDTO consultarPazYSalvo(@RequestBody PeticionPazSalvoDTO peticion) {
        System.out.println("Consultando paz y salvo para el estudiante con ID: " + peticion.getIdEstudiante());
        RespuestaPazSalvoDTO respuesta = solicitudPazSalvo.consultarPazYSalvo(peticion);
        return respuesta;
    }

    @DeleteMapping("/pazsalvo/{id}")
    public String eliminarPendientes(@PathVariable("id") int idEstudiante) {
        System.out.println("Eliminando pendientes para el estudiante con ID: " + idEstudiante);
        boolean eliminado = solicitudPazSalvo.eliminarPendientes(idEstudiante);
        return eliminado ? "Pendientes eliminados correctamente." : "No hay pendientes por eliminar.";
    }
}
