package co.unicauca.edu.co.compositor.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.unicauca.edu.co.compositor.fachadaServicios.PazYSalvoServiceImpl;
import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;
import main.java.co.unicauca.edu.co.compositor.controladores.ContadorFallos;

@RestController
@RequestMapping("/api")
public class PazYSalvoController {

    @Autowired
    private PazYSalvoServiceImpl pazYSalvoService;

    @Autowired
    private ContadorFallos contadorFallos;

    @GetMapping("/generarPs/{id}")
    public ResponseEntity<StudentStatusDTO> generarPazYSalvo(@PathVariable Integer id) {
        simularFallos();
        StudentStatusDTO respuesta = pazYSalvoService.verificarEstado(String.valueOf(id));
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<StudentStatusDTO> consultarDeudas(@PathVariable Integer id) {
        simularFallos();
        StudentStatusDTO respuesta = pazYSalvoService.consultarDeudas(String.valueOf(id));
        return ResponseEntity.ok(respuesta);
    }

    /* Método para simular fallos */
    private void simularFallos() {
        int intentos = contadorFallos.siguienteIntento();
        if (intentos < 2) {
            try {
                System.out.println("Simulando fallo...");
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            throw new RuntimeException("Fallo simulado");
        }
    }
}
