package co.unicauca.edu.co.compositor.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.unicauca.edu.co.compositor.fachadaServicios.PazYSalvoServiceImpl;
import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;
@RestController
@RequestMapping("/api")
public class PazYSalvoController {

    @Autowired
    private PazYSalvoServiceImpl pazYSalvoService;

    @GetMapping("/generarPs/{id}")
    public ResponseEntity<StudentStatusDTO> generarPazYSalvo(@PathVariable Integer id) {
        StudentStatusDTO respuesta = pazYSalvoService.verificarEstado(String.valueOf(id));
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<StudentStatusDTO> consultarDeudas(@PathVariable Integer id) {
        StudentStatusDTO respuesta = pazYSalvoService.consultarDeudas(String.valueOf(id));
        return ResponseEntity.ok(respuesta);
    }

}
