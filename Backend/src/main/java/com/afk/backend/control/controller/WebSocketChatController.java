package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.client.external.dto.ChatResponse;
import com.afk.backend.control.service.ChatService;
import com.afk.backend.model.entity.enm.EstadoChat;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebSocketChatController {

    private final ChatService chatService;

    public WebSocketChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatResponse handlePublicMessage(@Payload ChatRequest message) {
        ChatResponse savedMessage = chatService.createChat(message);
        return savedMessage;
    }

    @MessageMapping("/chat.private.{userId}")
    @SendTo("/topic/private.{userId}")
    public ChatResponse handlePrivateMessage(
            @Payload ChatRequest message,
            @DestinationVariable Long userId) {
        ChatRequest privateMessage = new ChatRequest(
                message.senderId(),
                userId,
                message.message(),
                message.status()
        );
        ChatResponse savedMessage = chatService.createChat(privateMessage);
        return savedMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatResponse addUser(@Payload ChatRequest message,
                                SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.senderId());
        return new ChatResponse(
                null,
                message.senderId(),
                null,
                "Usuario conectado",
                EstadoChat.ENTREGADO,
                LocalDateTime.now()
        );
    }
}