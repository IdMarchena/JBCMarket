package com.afk.backend.control.service;

import com.afk.backend.control.dto.*;

import java.util.List;

public interface NotificacionService {
    NotificacionDto createNotificacion(NotificacionDto notificacion);
    NotificacionDto findNotificacionById(Long id);
    List<NotificacionDto> findAllNotificaciones();
    void deleteNotificacionById(Long id);

    void notificarFavorito(Long idPublicacion, Long idUsuarioFavorito);

    void notificarSancion(PublicacionDto pub, String razon);

    void notificarCita(CitaDto cita);

    void notificarMatchEmpresa(PostulacionDto postulacion);

    void notificarMatchUsuario(PostulacionDto postulacion);

    void notificarCalificacion(PublicacionDto publicacionDto, UsuarioDto calificador, int puntaje);
}
