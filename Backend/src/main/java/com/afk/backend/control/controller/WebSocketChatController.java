package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.client.external.dto.ChatResponse;
import com.afk.backend.control.dto.MensajeDto;
import com.afk.backend.control.service.ChatService;
import com.afk.backend.control.service.MensajeService;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.enm.EstadoChat;
import com.afk.backend.model.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class WebSocketChatController {

    private final UsuarioRepository usuarioRepository;
    private final ChatService chatService;
    private final MensajeService mensajeService;



    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatResponse handlePublicMessage(@Payload ChatRequest message) {
        return chatService.createChat(message);
    }

    @MessageMapping("/chat.private.{userId}")
    @SendTo("/topic/private.{userId}")
    public ChatResponse handlePrivateMessage(
            @Payload ChatRequest message,
            @DestinationVariable Long userId) {
        Optional<Usuario> usuario= usuarioRepository.findById(userId);
        //quede por aca
        ChatRequest privateMessage = new ChatRequest(
                message.senderId(),
                userId,
                message.message(),
                message.status(),
                message.mensajes()
        );
        return chatService.createChat(privateMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatResponse addUser(@Payload ChatRequest message,
                                SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.senderId());
        return new ChatResponse(
                null,
                message.senderId(),
                null,
                "Usuario conectado",
                EstadoChat.ENTREGADO,
                LocalDateTime.now(),
                message.mensajes()
        );
    }

    @MessageMapping("/chat.send/{chatId}")
    @SendTo("/topic/chat.{chatId}")
    public MensajeDto handleMessage(
            @Payload ChatRequest message,
            @DestinationVariable Long chatId) {
        return mensajeService.enviarMensaje(chatId, message.senderId(), message.message());
    }

    @MessageMapping("/chat.markAsRead/{chatId}")
    public void markAsRead(@DestinationVariable Long chatId) {
        chatService.updateChatStatus(chatId, EstadoChat.LEIDO);
    }
}