package co.edu.unicauca.areafinanciera.fachadaServices;

import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTO;

public interface SolicitudPazSalvoInt {

    /**
     * Consulta si el estudiante está a paz y salvo en el área financiera.
     */
    RespuestaPazSalvoDTO consultarPazYSalvo(PeticionPazSalvoDTO peticion);

    /**
     * Elimina las deudas no pagadas de un estudiante.
     */
    boolean eliminarPendientes(int idEstudiante);
}