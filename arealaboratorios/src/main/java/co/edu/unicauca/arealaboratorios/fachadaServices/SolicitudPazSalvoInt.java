package co.edu.unicauca.arealaboratorios.fachadaServices;

import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTOLaboratorios;

public interface SolicitudPazSalvoInt {

    /**
     * Consulta si el estudiante está a paz y salvo en el área de laboratorios.
     */
    RespuestaPazSalvoDTOLaboratorios consultarPazYSalvo(PeticionPazSalvoDTO peticion);

    /**
     * Elimina los préstamos activos o vencidos (no devueltos).
     */
    boolean eliminarPendientes(int idEstudiante);
}