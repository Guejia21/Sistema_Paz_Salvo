package co.unicauca.edu.co.compositor.fachadaServicios.servicios;

import co.unicauca.edu.co.compositor.client.AdminWebSocketClient;
import co.unicauca.edu.co.compositor.fachadaServicios.DTOPeticion.PeticionPazSalvoDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.DeudaDTOFinanciera;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.PrestamoDTODeportes;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.PrestamoDTOLaboratorio;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.RespuestaPazSalvoConsultadoDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.DTORespuesta.RespuestaPazSalvoDTOArea;
import co.unicauca.edu.co.compositor.controladores.ContadorFallos;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;

import org.springframework.stereotype.Service;

@Service
public class PazYSalvoServiceImpl implements IPazYSalvoService {
    private final AdminWebSocketClient adminWebSocketClient;
    private final WebClient webClient;
    private final ContadorFallos contadorFallos; // Inyecta el bean

    public PazYSalvoServiceImpl(
        AdminWebSocketClient adminWebSocketClient, 
        WebClient webClient,
        ContadorFallos contadorFallos // Agrega aquí
    ) {
        this.webClient = webClient;
        this.adminWebSocketClient = adminWebSocketClient;
        this.contadorFallos = contadorFallos; // Asigna aquí
    }

    @Override
    public RespuestaPazSalvoConsultadoDTO consultarPazSalvo(PeticionPazSalvoDTO objPeticion) {
        System.out.println("[Sincrono] Consultado paz y salvo de estudiante con ID: " + objPeticion.getIdEstudiante());
        adminWebSocketClient.notificar(
            "/notificaciones/generales",
            Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " ha realizado una nueva solicitud de paz y salvo.")
        );
        RespuestaPazSalvoConsultadoDTO objRespuesta = new RespuestaPazSalvoConsultadoDTO();
        boolean pazSalvoDeportes = false;
        boolean pazSalvoLaboratorios = false;
        boolean pazSalvoFinanciera = false;

        int intentos = 0;
        final int MAX_INTENTOS = 3;
        boolean exito = false;

        while (intentos < MAX_INTENTOS && !exito) {
            try {
                //Llamada al 1er Servicio: Deportes
                String urlServicioDeportes = "http://localhost:5001/api/deporte/pazsalvo"; 
                RespuestaPazSalvoDTOArea<PrestamoDTODeportes> objRespuestaDeportes = webClient.post()
                    .uri(urlServicioDeportes)
                    .bodyValue(objPeticion)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<RespuestaPazSalvoDTOArea<PrestamoDTODeportes>>() {})
                    .block();
                objRespuesta.setObjAreaDeportes(objRespuestaDeportes);
                if(objRespuestaDeportes.isPazYSalvo()) pazSalvoDeportes = true;

                //Llamada al 2do Servicio: Financiera
                String urlServicioFinanciera = "http://localhost:5002/api/financiera/pazsalvo";
                RespuestaPazSalvoDTOArea<DeudaDTOFinanciera> objRespuestaFinanciera = webClient.post()
                    .uri(urlServicioFinanciera)
                    .bodyValue(objPeticion)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<RespuestaPazSalvoDTOArea<DeudaDTOFinanciera>>() {})
                    .block();
                objRespuesta.setObjAreaFinanciera(objRespuestaFinanciera);
                if(objRespuestaFinanciera.isPazYSalvo()) pazSalvoFinanciera = true;

                //Llamada al 3er Servicio: Laboratorios
                String urlServicioLaboratorios = "http://localhost:5003/api/laboratorio/pazsalvo";
                RespuestaPazSalvoDTOArea<PrestamoDTOLaboratorio> objRespuestaLaboratorios = webClient.post()
                    .uri(urlServicioLaboratorios)
                    .bodyValue(objPeticion)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<RespuestaPazSalvoDTOArea<PrestamoDTOLaboratorio>>() {})
                    .block();
                objRespuesta.setObjAreaLaboratorios(objRespuestaLaboratorios);
                if(objRespuestaLaboratorios.isPazYSalvo()) pazSalvoLaboratorios = true;

                exito = true; // Si todo salió bien, salimos del ciclo
            }
            catch (Exception e) {
                intentos++;
                contadorFallos.siguienteIntento(); // Lleva el conteo global de fallos
                System.out.println("Intento " + intentos + " fallido: " + e.getMessage());
                if (intentos >= MAX_INTENTOS) {
                    objRespuesta.setMensaje("Error al obtener el estado de paz y salvo tras varios intentos.");
                    return objRespuesta;
                }
                try { Thread.sleep(1000); } catch (InterruptedException ie) { }
            }
        }

        // Verificar si el estudiante está a paz y salvo
        if(pazSalvoDeportes && pazSalvoFinanciera && pazSalvoLaboratorios){
            objRespuesta.setPazSalvo(true);
            objRespuesta.setMensaje("El estudiante está a paz y salvo en todas las áreas.");
            adminWebSocketClient.notificar(
                "/notificaciones/generales",
                Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " se encuentra en paz y salvo en todas las areas.")
            );
            adminWebSocketClient.notificar(
                "/notificaciones/deportes",
                Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " está a paz y salvo en Deportes.")
            );
            adminWebSocketClient.notificar(
                "/notificaciones/financiera",
                Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " está a paz y salvo en Financiera.")
            );
            adminWebSocketClient.notificar(
                "/notificaciones/laboratorios",
                Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " está a paz y salvo en Laboratorios.")
            );
        } else {
            objRespuesta.setPazSalvo(false);
            StringBuilder mensaje = new StringBuilder("El estudiante no está a paz y salvo en las siguientes áreas:");
            if (!pazSalvoDeportes){
                mensaje.append(" Deportes");
                adminWebSocketClient.notificar(
                    "/notificaciones/deportes",
                    Map.of(
                        "contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " no está a paz y salvo en Deportes.",
                        "deudas", objRespuesta.getObjAreaDeportes().getDeudas()
                    )
                );
            }             
            if (!pazSalvoFinanciera) {
                mensaje.append(" Financiera");
                adminWebSocketClient.notificar(
                    "/notificaciones/financiera",
                    Map.of(
                        "contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " no está a paz y salvo en Financiera.",
                        "deudas", objRespuesta.getObjAreaFinanciera().getDeudas()
                    )
                );
            }
            if (!pazSalvoLaboratorios) {
                mensaje.append(" Laboratorios");
                adminWebSocketClient.notificar(
                    "/notificaciones/laboratorios",
                    Map.of(
                        "contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " no está a paz y salvo en Laboratorios.",
                        "deudas", objRespuesta.getObjAreaLaboratorios().getDeudas()
                    )
                );
            }
            objRespuesta.setMensaje(mensaje.toString());
        }
        return objRespuesta;
    }

    @Override
    public Mono<RespuestaPazSalvoConsultadoDTO> consultarPazSalvoAsincrono(PeticionPazSalvoDTO objPeticion) {
        System.out.println("[Asincrono] Consultado paz y salvo de estudiante con ID: " + objPeticion.getIdEstudiante());
        adminWebSocketClient.notificar(
            "/notificaciones/generales",
            Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " ha realizado una nueva solicitud de paz y salvo.")
        );
        
        RespuestaPazSalvoConsultadoDTO objRespuesta = new RespuestaPazSalvoConsultadoDTO();        
        //Llamada al 1er Servicio: Deportes
        String urlServicioDeportes = "http://localhost:5001/api/deporte/pazsalvo"; 
        Mono<RespuestaPazSalvoDTOArea<PrestamoDTODeportes>> objRespuestaDeportes = webClient.post()
            .uri(urlServicioDeportes)
            .bodyValue(objPeticion)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<RespuestaPazSalvoDTOArea<PrestamoDTODeportes>>() {})
            .doOnError(e -> {
                System.out.println("Error al consultar paz y salvo en Deportes: " + e.getMessage());
                contadorFallos.siguienteIntento();
            })
            .retry(2); // 2 reintentos adicionales (total 3 intentos)
        //Llamada al 2do Servicio: Financiera
        String urlServicioFinanciera = "http://localhost:5002/api/financiera/pazsalvo";
        Mono<RespuestaPazSalvoDTOArea<DeudaDTOFinanciera>> objRespuestaFinanciera = webClient.post()
            .uri(urlServicioFinanciera)
            .bodyValue(objPeticion)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<RespuestaPazSalvoDTOArea<DeudaDTOFinanciera>>() {})
            .doOnError(e -> {
                System.out.println("Error al consultar paz y salvo en Financiera: " + e.getMessage());
                contadorFallos.siguienteIntento();
            })
            .retry(2);
        //Llamada al 3er Servicio: Laboratorios
        String urlServicioLaboratorios = "http://localhost:5003/api/laboratorio/pazsalvo";
        Mono<RespuestaPazSalvoDTOArea<PrestamoDTOLaboratorio>> objRespuestaLaboratorios = webClient.post()
            .uri(urlServicioLaboratorios)
            .bodyValue(objPeticion)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<RespuestaPazSalvoDTOArea<PrestamoDTOLaboratorio>>() {})
            .doOnError(e -> {
                System.out.println("Error al consultar paz y salvo en Laboratorios: " + e.getMessage());
                contadorFallos.siguienteIntento();
            })
            .retry(2);
        // Esperar a que todas las respuestas se completen
        Mono.zip(objRespuestaDeportes, objRespuestaFinanciera, objRespuestaLaboratorios)
            .doOnNext(tuple -> {
                boolean pazSalvoDeportes = false;
                boolean pazSalvoLaboratorios = false;
                boolean pazSalvoFinanciera = false; 
                RespuestaPazSalvoDTOArea<PrestamoDTODeportes> respuestaDeportes = tuple.getT1();
                RespuestaPazSalvoDTOArea<DeudaDTOFinanciera> respuestaFinanciera = tuple.getT2();
                RespuestaPazSalvoDTOArea<PrestamoDTOLaboratorio> respuestaLaboratorios = tuple.getT3();
                
                objRespuesta.setObjAreaDeportes(respuestaDeportes);
                objRespuesta.setObjAreaFinanciera(respuestaFinanciera);
                objRespuesta.setObjAreaLaboratorios(respuestaLaboratorios);
                
                if(respuestaDeportes.isPazYSalvo()) pazSalvoDeportes = true;
                if(respuestaFinanciera.isPazYSalvo()) pazSalvoFinanciera = true;
                if(respuestaLaboratorios.isPazYSalvo()) pazSalvoLaboratorios = true;
                
                if(pazSalvoDeportes && pazSalvoFinanciera && pazSalvoLaboratorios){
                    objRespuesta.setPazSalvo(true);
                    objRespuesta.setMensaje("El estudiante está a paz y salvo en todas las áreas.");
                    // Enviar mensaje a WebSocket a todos los admins
                    // Notificar a cada área que el estudiante está a paz y salvo en su área
                    adminWebSocketClient.notificar(
                        "/notificaciones/deportes",
                        Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " está a paz y salvo en Deportes.")
                    );
                    adminWebSocketClient.notificar(
                        "/notificaciones/financiera",
                        Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " está a paz y salvo en Financiera.")
                    );
                    adminWebSocketClient.notificar(
                        "/notificaciones/laboratorios",
                        Map.of("contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " está a paz y salvo en Laboratorios.")
                    );
                    adminWebSocketClient.notificar("/notificaciones/generales", "El estudiante" + objPeticion.getNombreEstudiante() + " se encuentra en paz y salvo en todas las areas.");
                } else {
                    objRespuesta.setPazSalvo(false);
                    StringBuilder mensaje = new StringBuilder("El estudiante no está a paz y salvo en las siguientes áreas:");
                if (!pazSalvoDeportes){
                    mensaje.append(" Deportes");

                    System.out.println("Deudas enviadas a deportes: " + objRespuesta.getObjAreaDeportes().getDeudas());
                    
                    //Enviar mensaje a WebSocket al administrador de deportes
                    adminWebSocketClient.notificar(
                        "/notificaciones/deportes",
                        Map.of(
                            "contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " no está a paz y salvo en Deportes.",
                            "deudas", respuestaDeportes.getDeudas()
                        )
                    );
                } 
                if (!pazSalvoFinanciera) {
                    mensaje.append(" Financiera");
                    
                    System.out.println("Deudas enviadas a financiera: " + objRespuesta.getObjAreaFinanciera().getDeudas());
                    //Enviar mensaje a WebSocket al administrador financiero
                    adminWebSocketClient.notificar(
                        "/notificaciones/financiera",
                        Map.of(
                            "contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " no está a paz y salvo en Financiera.",
                            "deudas", respuestaFinanciera.getDeudas()
                        )
                    );
                }
                if (!pazSalvoLaboratorios) {
                    mensaje.append(" Laboratorios");
                    System.out.println("Deudas enviadas a laboratorios: " + objRespuesta.getObjAreaLaboratorios().getDeudas());
                    //Enviar mensaje a WebSocket al administrador de laboratorios
                    adminWebSocketClient.notificar(
                        "/notificaciones/laboratorios",
                        Map.of(
                            "contenido", "El estudiante " + objPeticion.getNombreEstudiante() + " no está a paz y salvo en Laboratorios.",
                            "deudas", respuestaLaboratorios.getDeudas()
                        )
                    );
                }
                objRespuesta.setMensaje(mensaje.toString());
                }
            })
            .doOnError(e -> System.out.println("Error al combinar respuestas: " + e.getMessage()))
            .block(); // Bloquea hasta que todas las respuestas se completen
        // Verificar si el estudiante está a paz y salvo
        
        return Mono.just(objRespuesta)
            .doOnSuccess(respuesta -> {
                System.out.println("[Asincrono] Paz y salvo consultado exitosamente.");                
            })
            .doOnError(e -> System.out.println("Error al consultar paz y salvo: " + e.getMessage()));
        
    }

    
}