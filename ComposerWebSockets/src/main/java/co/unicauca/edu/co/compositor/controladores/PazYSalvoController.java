package co.unicauca.edu.co.compositor.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import co.unicauca.edu.co.compositor.fachadaServicios.DTOPeticion.PeticionPazSalvoDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.RespuestaPazSalvoConsultadoDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IPazYSalvoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class PazYSalvoController {

    @Autowired
    private IPazYSalvoService pazYSalvoService;

    @Autowired
    private ContadorFallos contadorFallos;

    @PostMapping("/orquestadorSincrono")
    public RespuestaPazSalvoConsultadoDTO orquestarServiciosSincronicamente(@RequestBody PeticionPazSalvoDTO objPeticion) {
        simularFallos();
        return pazYSalvoService.consultarPazSalvo(objPeticion);
    }
    @PostMapping("/orquestadorAsincrono")
    public Mono<RespuestaPazSalvoConsultadoDTO> orquestarServiciosAsincronicamente(@RequestBody PeticionPazSalvoDTO objPeticion) {
        simularFallos();
        return pazYSalvoService.consultarPazSalvoAsincrono(objPeticion);
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
