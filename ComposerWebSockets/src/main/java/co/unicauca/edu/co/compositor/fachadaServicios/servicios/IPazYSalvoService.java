package co.unicauca.edu.co.compositor.fachadaServicios.servicios;

import co.unicauca.edu.co.compositor.fachadaServicios.DTOPeticion.PeticionPazSalvoDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.RespuestaPazSalvoConsultadoDTO;
import reactor.core.publisher.Mono;

public interface IPazYSalvoService {
    public RespuestaPazSalvoConsultadoDTO consultarPazSalvo(PeticionPazSalvoDTO objPeticion);
    public Mono<RespuestaPazSalvoConsultadoDTO> consultarPazSalvoAsincrono(PeticionPazSalvoDTO objPeticion);
    
}