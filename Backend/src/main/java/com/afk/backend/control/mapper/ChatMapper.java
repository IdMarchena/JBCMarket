package com.afk.backend.control.mapper;

import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.client.external.dto.ChatResponse;
import com.afk.backend.model.entity.Chat;
import com.afk.backend.model.entity.Usuario;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class},
        uses = {UsuarioMapper.class})
public interface ChatMapper {

    default Usuario map(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioa", source = "senderId")
    @Mapping(target = "usuariob", source = "receiverId")
    @Mapping(target = "mensaje", source = "message")
    @Mapping(target = "estado_chat", source = "status")
    Chat toEntity(ChatRequest chatRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "senderId", source = "usuarioa.id")
    @Mapping(target = "receiverId", source = "usuariob.id")
    @Mapping(target = "message", source = "mensaje")
    @Mapping(target = "status", source = "estado_chat")
    ChatResponse toResponse(Chat chat);

    List<ChatResponse> toResponseList(List<Chat> chats);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioa", ignore = true)
    @Mapping(target = "usuariob", ignore = true)
    void updateEntityFromRequest(@MappingTarget Chat entity, ChatRequest request);
}