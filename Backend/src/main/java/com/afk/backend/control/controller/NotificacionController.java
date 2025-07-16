package com.afk.backend.control.controller;

import com.afk.backend.control.dto.*;
import com.afk.backend.control.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDto> createNotificacion(@RequestBody NotificacionDto dto) {
        NotificacionDto created = notificacionService.createNotificacion(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDto> getNotificacionById(@PathVariable Long id) {
        NotificacionDto notificacion = notificacionService.findNotificacionById(id);
        return ResponseEntity.ok(notificacion);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDto>> getAllNotificaciones() {
        List<NotificacionDto> notificaciones = notificacionService.findAllNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        notificacionService.deleteNotificacionById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorito")
    public ResponseEntity<Void> notificarFavorito(
            @RequestParam Long idPublicacion,
            @RequestParam Long idUsuarioFavorito) {
        notificacionService.notificarFavorito(idPublicacion, idUsuarioFavorito);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sancion")
    public ResponseEntity<Void> notificarSancion(
            @RequestBody PublicacionDto publicacionDto,
            @RequestParam String razon) {
        notificacionService.notificarSancion(publicacionDto, razon);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cita")
    public ResponseEntity<Void> notificarCita(@RequestBody CitaDto citaDto) {
        notificacionService.notificarCita(citaDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/match/empresa")
    public ResponseEntity<Void> notificarMatchEmpresa(@RequestBody PostulacionDto postulacionDto) {
        notificacionService.notificarMatchEmpresa(postulacionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/match/usuario")
    public ResponseEntity<Void> notificarMatchUsuario(@RequestBody PostulacionDto postulacionDto) {
        notificacionService.notificarMatchUsuario(postulacionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calificacion")
    public ResponseEntity<Void> notificarCalificacion(
            @RequestParam Long idPublicacion,
            @RequestParam Long idCalificador,
            @RequestParam int puntaje) {

        PublicacionDto publicacionDto = new PublicacionDto(idPublicacion, null, null, null, null, null, null);
        UsuarioDto usuarioDto = new UsuarioDto(idCalificador, null, null, null);

        notificacionService.notificarCalificacion(publicacionDto, usuarioDto, puntaje);

        return ResponseEntity.ok().build();
    }
}
