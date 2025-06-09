package co.unicauca.edu.co.compositor.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/notificaciones");
        config.setApplicationDestinationPrefixes("/app"); // si vas a enviar desde cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Para clientes Java (WebSocket puro)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
        // Para clientes JS (SockJS)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
