package co.edu.unicauca.areafinanciera.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.areafinanciera.fachadaServices.SolicitudPazSalvoInt;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTOFinanciera;

@RestController
@RequestMapping("/api/financiera")
public class PazSalvoRestController {
    @Autowired
    private SolicitudPazSalvoInt servicio;

    @PostMapping("/pazsalvo")
    public ResponseEntity<RespuestaPazSalvoDTOFinanciera> consultarPazYSalvo(@RequestBody PeticionPazSalvoDTO peticion) {
        System.out.println("Consultando paz y salvo para el estudiante con ID: " + peticion.getIdEstudiante());
        RespuestaPazSalvoDTOFinanciera respuesta = servicio.consultarPazYSalvo(peticion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/pazsalvo/{id}")
    public String eliminarPendientes(@PathVariable("id") int idEstudiante) {
        System.out.println("Eliminando deudas pendientes para el estudiante con ID: " + idEstudiante);
        boolean eliminado = servicio.eliminarPendientes(idEstudiante);
        return eliminado ? "Deudas eliminadas correctamente." : "No hay deudas por eliminar.";
    }
}