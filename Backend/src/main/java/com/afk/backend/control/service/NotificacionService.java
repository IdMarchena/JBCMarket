package com.afk.backend.control.service;

import com.afk.backend.control.dto.NotificacionDto;

import java.util.List;

public interface NotificacionService {
    NotificacionDto createNotificacion(NotificacionDto notificacion);
    NotificacionDto findNotificacionById(Long id);
    List<NotificacionDto> findAllNotificaciones();
    void deleteNotificacionById(Long id);
}
