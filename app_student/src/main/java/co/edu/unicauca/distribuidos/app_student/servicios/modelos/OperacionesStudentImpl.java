package co.edu.unicauca.distribuidos.app_student.servicios.modelos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.RespuestaSolicitudDTO;
import feign.FeignException;

@Service
public class OperacionesStudentImpl {
    @Autowired
    private OperacionesStudent request;

    @Retryable(value = { FeignException.class }, maxAttempts = 3, backoff = @Backoff(delay = 3000))
    public RespuestaSolicitudDTO generarPazYSalvo(int id) {
        // se ejecuta cuando no hay fallos
        System.out.println("Intentando generar paz y salvo...");
        return request.generarPazYSalvo(id);
    }

    @Retryable(value = { FeignException.class }, maxAttempts = 3, backoff = @Backoff(delay = 3000))
    public RespuestaSolicitudDTO consultarDeudas(int id) {
        // se ejecuta cuando no hay fallos
        System.out.println("Intentando consultar tus deudas...");
        return request.consultarDeudas(id);
    }

    @Recover
    public RespuestaSolicitudDTO recuperar(FeignException e, int id) {
        System.out.println("Todos los reintentos fallaron");
        RespuestaSolicitudDTO objRespuesta = new RespuestaSolicitudDTO();
        objRespuesta.setMensaje("Error al procesar tu peticion, por favor intenta mas tarde.");
        return objRespuesta;
    }
}
