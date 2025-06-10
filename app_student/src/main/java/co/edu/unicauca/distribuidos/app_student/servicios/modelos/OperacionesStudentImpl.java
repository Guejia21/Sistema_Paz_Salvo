package co.edu.unicauca.distribuidos.app_student.servicios.modelos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.peticion.PeticionPazSalvoDTO;
import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.RespuestaPazSalvoConsultadoDTO;
import feign.FeignException;

@Service
public class OperacionesStudentImpl {
    @Autowired
    private OperacionesStudent request;

    /* Petición síncrona */
    @Retryable(value = { FeignException.class }, maxAttempts = 3, backoff = @Backoff(delay = 4000))
    public RespuestaPazSalvoConsultadoDTO orquestarServiciosSincronicamente(PeticionPazSalvoDTO objPeticion) {
        System.out.println("Intentando generar paz y salvo de forma síncrona...");
        return request.orquestarServiciosSincronicamente(objPeticion);
    }

    /* Petición asíncrona */
    @Retryable(value = { FeignException.class }, maxAttempts = 3, backoff = @Backoff(delay = 4000))
    public RespuestaPazSalvoConsultadoDTO orquestarServiciosAsincronicamente(PeticionPazSalvoDTO objPeticion) {
        System.out.println("Intentando genera paz y salvo de forma asíncrona...");
        return request.orquestarServiciosAsincronicamente(objPeticion);
    }

    @Recover
    public RespuestaPazSalvoConsultadoDTO recuperar(FeignException e, PeticionPazSalvoDTO objPeticion) {
        System.out.println("Todos los reintentos fallaron");
        RespuestaPazSalvoConsultadoDTO objRespuesta = new RespuestaPazSalvoConsultadoDTO();
        objRespuesta.setMensaje("Error al procesar tu petición, por favor intenta más tarde.");
        // Puedes dejar los demás campos en null o con valores por defecto
        return objRespuesta;
    }
}
