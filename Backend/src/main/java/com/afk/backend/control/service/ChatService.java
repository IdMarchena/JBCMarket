package com.afk.backend.control.service;
import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.client.external.dto.ChatResponse;
import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.model.entity.enm.EstadoChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    ChatResponse createChat(ChatRequest chatRequest);
    ChatResponse getChatById(Long id);
    List<ChatResponse> getAllChats();
    ChatResponse updateChat(Long id, ChatRequest chatRequest);
    void deleteChat(Long id);
    List<ChatResponse> getChatsByUser(Long userId);
    List<ChatResponse> getChatsBetweenUsers(Long user1Id, Long user2Id);
    void updateChatStatus(Long chatId, EstadoChat status);
    Integer obtenerCantidadDeChatsEncontrados();
    Page<ChatResponse> buscarChats(String filtro, Pageable pageable);
}
