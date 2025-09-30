package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.MensajeDto;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface MensajeMapper {

    @Named("mapC")
    default Chat mapC(Long id) {
        if (id == null) return null;
        Chat chat = new Chat();
        chat.setId(id);
        return chat;
    }

    @Named("mapU")
    default Usuario mapU(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Mapping(target = "chat", source = "idChat", qualifiedByName = "mapC")
    @Mapping(target = "sender", source = "idSender", qualifiedByName = "mapU")
    @Mapping(target = "contenido", source = "contenido")
    @Mapping(source = "estado", target = "estado")
    @Mapping(target = "fecha", source = "fecha")
    Mensaje toEntity(MensajeDto mensajeDto);

    @Mapping(source = "chat.id", target = "idChat")
    @Mapping(source = "sender.id", target = "idSender")
    @Mapping(source = "contenido", target = "contenido")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "fecha", target = "fecha")
    MensajeDto toDto(Mensaje mensaje);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(MensajeDto dto, @MappingTarget Mensaje entity);
}
