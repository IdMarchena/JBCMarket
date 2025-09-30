package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.MensajeDto;
import com.afk.backend.control.mapper.MensajeMapper;
import com.afk.backend.control.service.MensajeService;
import com.afk.backend.model.entity.Chat;
import com.afk.backend.model.entity.Mensaje;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.enm.EstadoChat;
import com.afk.backend.model.repository.ChatRepository;
import com.afk.backend.model.repository.MensajeRepository;
import com.afk.backend.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensajeServiceImpl implements MensajeService {

    private final ChatRepository chatRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensajeRepository mensajeRepository;
    private final MensajeMapper mensajeMapper;

    @Override
    @Transactional
    public MensajeDto enviarMensaje(Long chatId, Long senderId, String contenido) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        Usuario sender = usuarioRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mensaje mensaje = Mensaje.builder()
                .chat(chat)
                .sender(sender)
                .contenido(contenido)
                .estado(EstadoChat.ENVIADO)
                .fecha(LocalDateTime.now())
                .build();

        Mensaje saved = mensajeRepository.save(mensaje);
        return mensajeMapper.toDto(saved);
    }
    @Override
    public List<MensajeDto> obtenerMensajes(Long chatId) {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        return mensajes.stream().map(mensajeMapper::toDto).collect(Collectors.toList());
    }
}
