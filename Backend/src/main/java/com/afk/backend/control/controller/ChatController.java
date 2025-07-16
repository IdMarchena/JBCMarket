package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.client.external.dto.ChatResponse;
import com.afk.backend.control.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> crearChat(@RequestBody ChatRequest request) {
        ChatResponse chat = chatService.createChat(request);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> obtenerChatPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getChatById(id));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> listarTodosLosChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatResponse> actualizarChat(@PathVariable Long id, @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.updateChat(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<ChatResponse>> obtenerChatsPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.getChatsByUser(userId));
    }

    @GetMapping("/entre/{user1Id}/{user2Id}")
    public ResponseEntity<List<ChatResponse>> obtenerChatsEntreUsuarios(
            @PathVariable Long user1Id,
            @PathVariable Long user2Id) {
        return ResponseEntity.ok(chatService.getChatsBetweenUsers(user1Id, user2Id));
    }
}
