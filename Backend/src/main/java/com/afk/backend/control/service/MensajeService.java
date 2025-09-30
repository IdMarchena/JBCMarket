package com.afk.backend.control.service;
import com.afk.backend.control.dto.MensajeDto;
import java.util.List;
public interface MensajeService {
    MensajeDto enviarMensaje(Long chatId, Long senderId, String contenido);
    List<MensajeDto> obtenerMensajes(Long chatId);
}
