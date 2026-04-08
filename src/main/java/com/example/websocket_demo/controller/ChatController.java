package com.example.websocket_demo.controller;

import com.example.websocket_demo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Аннотация @MessageMapping означает, что метод будет обрабатывать сообщения,
    // отправленные клиентом по адресу "/app/chat.sendMessage"
    // Префикс "/app" мы задали в конфигурации.
    @MessageMapping("/chat.sendMessage")
    // @SendTo указывает, куда брокер должен перенаправить полученное сообщение.
    // Все клиенты, подписанные на "/topic/public", получат это сообщение.
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // Здесь можно добавить логику сохранения сообщения в БД
        return chatMessage;
    }

    // Обработка события добавления нового пользователя
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Добавляем имя пользователя в WebSocket сессию, чтобы потом знать, кто отключился
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
