package co.edu.unicauca.distribuidos.app_student.servicios.modelos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.RespuestaSolicitudDTO;

@FeignClient(name = "reporteStudent", url = "http://localhost:5000/api", configuration = FeignConfig.class)
public interface OperacionesStudent {
    @GetMapping("generarPs/{id}")
    public RespuestaSolicitudDTO generarPazYSalvo(@PathVariable Integer id);

    @GetMapping("consultar/{id}")
    public RespuestaSolicitudDTO consultarDeudas(@PathVariable Integer id);
}
