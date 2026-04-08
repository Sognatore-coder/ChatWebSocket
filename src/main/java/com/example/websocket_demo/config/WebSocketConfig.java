package com.example.websocket_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Включает поддержку WebSocket и STOMP
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Настройка брокера сообщений и префиксов
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Включаем простой брокер в памяти для дестинаций, начинающихся с /topic и /queue
        // /topic используется для широковещательных рассылок
        // /queue используется для отправки конкретному пользователю
        config.enableSimpleBroker("/topic", "/queue");
        // Указываем префикс для сообщений, которые будут обрабатываться аннотацией @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
        // Префикс для отправки сообщений конкретному пользователю
        config.setUserDestinationPrefix("/user");
    }

    // Регистрация эндпоинта, к которому будут подключаться клиенты
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/chat-websocket") // Эндпоинт для WebSocket соединения
                .setAllowedOrigins("http://localhost:8080") // Разрешаем запросы с любых источников (для простоты)
                .withSockJS(); // Включаем поддержку SockJS (fallback)
    }
}
