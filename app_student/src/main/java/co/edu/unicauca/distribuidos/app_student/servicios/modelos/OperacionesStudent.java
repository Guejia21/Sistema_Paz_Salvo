package co.edu.unicauca.distribuidos.app_student.servicios.modelos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.peticion.PeticionPazSalvoDTO;
import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.RespuestaPazSalvoConsultadoDTO;

@FeignClient(name = "reporteStudent", url = "http://localhost:5000/api", configuration = FeignConfig.class)
public interface OperacionesStudent {

        /* Peticion síncrona */
        @PostMapping("orquestadorSincrono")
        public RespuestaPazSalvoConsultadoDTO orquestarServiciosSincronicamente(
                        @RequestBody PeticionPazSalvoDTO objPeticion);

        /* Peticion asíncrona */
        @PostMapping("orquestadorAsincrono")
        public RespuestaPazSalvoConsultadoDTO orquestarServiciosAsincronicamente(
                        @RequestBody PeticionPazSalvoDTO objPeticion);
}
