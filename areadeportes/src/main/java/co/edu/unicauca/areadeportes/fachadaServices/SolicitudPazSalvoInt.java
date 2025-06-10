package co.edu.unicauca.areadeportes.fachadaServices;

import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTODeportes;

public interface SolicitudPazSalvoInt {
    /**
     * Consulta si un estudiante está a paz y salvo en el área de deportes.
     * 
     * @param peticion Información de la solicitud con el ID del estudiante
     * @return Respuesta con el estado y los préstamos pendientes (si existen)
     */
    public RespuestaPazSalvoDTODeportes consultarPazYSalvo(PeticionPazSalvoDTO peticion);

    /**
     * Elimina los préstamos no devueltos por el estudiante (simula entrega).
     * 
     * @param idEstudiante Código del estudiante
     * @return true si se eliminaron préstamos pendientes, false si no tenía
     */
    public boolean eliminarPendientes(int idEstudiante);
}
