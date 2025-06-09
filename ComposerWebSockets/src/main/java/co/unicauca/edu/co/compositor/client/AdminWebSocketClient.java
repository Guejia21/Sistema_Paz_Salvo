package co.unicauca.edu.co.compositor.client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;import org.springframework.messaging.simp.stomp.StompSession;
import java.util.concurrent.ExecutionException;

@Component
public class AdminWebSocketClient {

    private static final String URL = "ws://localhost:5000/ws";
    private StompSession session;
    public void conectar() {
        System.out.println("[AdminWebSocketClient] Intentando conectar a " + URL);
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(scheduler);

        StompSessionHandler handler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("[AdminWebSocketClient] Conectado al WebSocket (ADMIN)");
                AdminWebSocketClient.this.session = session;
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.err.println("[AdminWebSocketClient] Error en transporte STOMP: " + exception.getMessage());
            }
        };

        try {
            this.session = stompClient.connect(URL, handler).get();
            System.out.println("[AdminWebSocketClient] Sesión STOMP creada: " + (this.session != null && this.session.isConnected()));
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("[AdminWebSocketClient] Error al conectar WebSocket: " + e.getMessage());
        }
    }

    public void notificar(String destino, Object payload) {
        if (session == null || !session.isConnected()) {
            System.out.println("[AdminWebSocketClient] Sesión no conectada, intentando reconectar...");
            conectar();
        }
        System.out.println("[AdminWebSocketClient] Estado de la sesión antes de enviar: " +
            (session != null ? session.isConnected() : "null"));
        System.out.println("Enviando notificación a " + destino + ": " + payload);
        if (session != null && session.isConnected()) {
            session.send(destino, payload);
            System.out.println("Mensaje enviado a " + destino + ": " + payload);
        } else {
            System.err.println("No se pudo enviar el mensaje. STOMP no conectado.");
        }
    }
}
