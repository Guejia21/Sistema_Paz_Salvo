package co.edu.unicauca.distribuidos.app_student.servicios.modelos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.StudentStatusDTO;

@FeignClient(name = "reporteStudent", url = "http://localhost:5000/api", configuration = FeignConfig.class)
public interface OperacionesStudent {
    @GetMapping("generarPs/{id}")
    public StudentStatusDTO generarPazYSalvo(@PathVariable int id);

    @GetMapping("consultar/{id}")
    public StudentStatusDTO consultarDeudas(@PathVariable int id);
}
