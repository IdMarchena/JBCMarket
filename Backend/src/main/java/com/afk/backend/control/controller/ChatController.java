package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.client.external.dto.ChatResponse;
import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/createChat")
    public ResponseEntity<ChatResponse> crearChat(@RequestBody ChatRequest request) {
        ChatResponse chat = chatService.createChat(request);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/obtenerChatPorId/{id}")
    public ResponseEntity<ChatResponse> obtenerChatPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getChatById(id));
    }

    @GetMapping("/listarTodosLosChats")
    public ResponseEntity<List<ChatResponse>> listarTodosLosChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @PutMapping("/actualizarChat/{id}")
    public ResponseEntity<ChatResponse> actualizarChat(@PathVariable Long id, @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.updateChat(id, request));
    }

    @DeleteMapping("/eliminarChat/{id}")
    public ResponseEntity<Void> eliminarChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/obtenerChatsPorUsuario/{userId}")
    public ResponseEntity<List<ChatResponse>> obtenerChatsPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.getChatsByUser(userId));
    }

    @GetMapping("/obtenerChatsEntreUsuarios/{user1Id}/{user2Id}")
    public ResponseEntity<List<ChatResponse>> obtenerChatsEntreUsuarios(
            @PathVariable Long user1Id,
            @PathVariable Long user2Id) {
        return ResponseEntity.ok(chatService.getChatsBetweenUsers(user1Id, user2Id));
    }
    @GetMapping("/obtenerCantidadChats")
    public ResponseEntity<String> obtenerCantidadChats() {
        return ResponseEntity.ok("Cantidad de chats"+chatService.obtenerCantidadDeChatsEncontrados());
    }
    @GetMapping("/buscarChats")
    public ResponseEntity<Page<ChatResponse>> buscarChats(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<ChatResponse> resultados= chatService.buscarChats(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }
}
