package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoChat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto{
        private Long id;
        private Long idUsuarioA;
        private Long idUsuarioB;
        private String mensaje;
        @Enumerated(EnumType.STRING)
        private EstadoChat estadoChat;
}