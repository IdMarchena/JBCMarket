package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.*;
import com.afk.backend.control.mapper.NotificacionMapper;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoNotificacion;
import com.afk.backend.model.repository.*;
import com.afk.backend.control.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PublicacionRepository publicacionRepository;
    private final PostulacionRepository postulacionRepository;
    private final CitaRepository citaRepository;
    private final NotificacionMapper notificacionMapper;

    @Override
    public NotificacionDto createNotificacion(NotificacionDto dto) {
        Notificacion noti = notificacionMapper.toEntity(dto);
        noti.setFecha(LocalDateTime.now());
        noti.setEstado_notificacion(EstadoNotificacion.PENDIENTE);
        return notificacionMapper.toDto(notificacionRepository.save(noti));
    }

    @Override
    public NotificacionDto findNotificacionById(Long id) {
        return notificacionRepository.findById(id)
                .map(notificacionMapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<NotificacionDto> findAllNotificaciones() {
        return notificacionRepository.findAll().stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotificacionById(Long id) {
        notificacionRepository.deleteById(id);
    }

    @Override
    public void notificarFavorito(Long idPublicacion, Long idUsuarioFavorito) {
        Publicacion pub = publicacionRepository.findById(idPublicacion).orElseThrow();
        Empresa empresa = pub.getVacante().getEmpresa();
        Usuario usuario = usuarioRepository.findById(idUsuarioFavorito).orElseThrow();

        crearNotificacionEmpresa(
                "A tu publicación '" + pub.getTitulo() + "' la añadió a favoritos: " + usuario.getNombre(),
                empresa
        );
    }

    @Override
    public void notificarSancion(PublicacionDto pubDto, String razon) {
        Publicacion pub = publicacionRepository.findById(pubDto.id()).orElseThrow();
        Empresa empresa = pub.getVacante().getEmpresa();

        crearNotificacionEmpresa(
                "Tu publicación '" + pub.getTitulo() + "' fue sancionada: " + razon,
                empresa
        );
    }

    @Override
    public void notificarCita(CitaDto citaDto) {
        Cita cita = citaRepository.findById(citaDto.id()).orElseThrow();

        Usuario postulante = cita.getPostulacion().getUsuario();
        Empresa empresa = cita.getPostulacion().getVacante().getEmpresa();

        crearNotificacionUsuario("Te han agendado una cita con la empresa " + empresa.getNombre(), postulante);
        crearNotificacionEmpresa("Has agendado una cita con el postulante " + postulante.getNombre(), empresa);
    }

    @Override
    public void notificarMatchEmpresa(PostulacionDto postulacionDto) {
        Postulacion postulacion = postulacionRepository.findById(postulacionDto.id()).orElseThrow();

        Usuario usuario = postulacion.getUsuario();
        Empresa empresa = postulacion.getVacante().getEmpresa();

        crearNotificacionUsuario("La empresa " + empresa.getNombre() + " te ha dado match.", usuario);
    }

    @Override
    public void notificarMatchUsuario(PostulacionDto postulacionDto) {
        Postulacion postulacion = postulacionRepository.findById(postulacionDto.id()).orElseThrow();

        Empresa empresa = postulacion.getVacante().getEmpresa();
        Usuario usuario = postulacion.getUsuario();

        crearNotificacionEmpresa("El usuario " + usuario.getNombre() + " ha hecho match con tu publicación.", empresa);
    }

    @Override
    public void notificarCalificacion(PublicacionDto publicacionDto, UsuarioDto calificadorDto, int puntaje) {
        Publicacion publicacion = publicacionRepository.findById(publicacionDto.id()).orElseThrow();
        Empresa empresa = publicacion.getVacante().getEmpresa();
        Usuario calificador = usuarioRepository.findById(calificadorDto.id()).orElseThrow();

        crearNotificacionEmpresa(
                "Tu publicación '" + publicacion.getTitulo() + "' fue calificada con " + puntaje +
                        " estrellas por " + calificador.getNombre(),
                empresa
        );
    }


    private void crearNotificacionEmpresa(String mensaje, Empresa empresa) {
        Notificacion notif = Notificacion.builder()
                .mensaje(mensaje)
                .fecha(LocalDateTime.now())
                .estado_notificacion(EstadoNotificacion.PENDIENTE)
                .empresa(empresa)
                .build();
        notificacionRepository.save(notif);
    }

    private void crearNotificacionUsuario(String mensaje, Usuario usuario) {
        Notificacion notif = Notificacion.builder()
                .mensaje(mensaje)
                .fecha(LocalDateTime.now())
                .estado_notificacion(EstadoNotificacion.PENDIENTE)
                .usuario(usuario)
                .build();
        notificacionRepository.save(notif);
    }
}
