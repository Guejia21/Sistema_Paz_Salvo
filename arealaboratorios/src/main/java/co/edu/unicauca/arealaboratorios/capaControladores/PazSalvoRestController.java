package co.edu.unicauca.arealaboratorios.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.arealaboratorios.fachadaServices.SolicitudPazSalvoInt;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTOLaboratorios;

@RestController
@RequestMapping("/api/laboratorio")
public class PazSalvoRestController {
    @Autowired
    private SolicitudPazSalvoInt servicio;

    /**
     * Consulta si el estudiante está a paz y salvo en laboratorios.
     */
    @PostMapping("/pazsalvo")
    public ResponseEntity<RespuestaPazSalvoDTOLaboratorios> consultarPazYSalvo(@RequestBody PeticionPazSalvoDTO peticion) {
        System.out.println("Consultando paz y salvo para el estudiante con ID: " + peticion.getIdEstudiante());
        RespuestaPazSalvoDTOLaboratorios respuesta = servicio.consultarPazYSalvo(peticion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /**
     * Elimina préstamos no devueltos (activo o vencido) del estudiante.
     */
    @DeleteMapping("/pazsalvo/{id}")
    public String eliminarPendientes(@PathVariable("id") int idEstudiante) {
        System.out.println("Eliminando préstamos pendientes para el estudiante con ID: " + idEstudiante);
        boolean eliminado = servicio.eliminarPendientes(idEstudiante);
        return eliminado ? "Préstamos eliminados correctamente." : "No hay préstamos pendientes por eliminar.";
    }
}