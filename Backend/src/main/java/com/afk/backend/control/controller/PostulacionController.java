package com.afk.backend.control.controller;

import com.afk.backend.control.dto.PostulacionDto;
import com.afk.backend.control.service.PostulacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulaciones")
@RequiredArgsConstructor
public class PostulacionController {

    private final PostulacionService postulacionService;

    @PostMapping
    public ResponseEntity<PostulacionDto> crearPostulacion(@RequestBody PostulacionDto dto) {
        PostulacionDto creada = postulacionService.createPostulacion(dto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostulacionDto> obtenerPorId(@PathVariable Long id) {
        PostulacionDto postulacion = postulacionService.findPostulacionById(id);
        return ResponseEntity.ok(postulacion);
    }

    @GetMapping
    public ResponseEntity<List<PostulacionDto>> obtenerTodas() {
        List<PostulacionDto> lista = postulacionService.findAllPostulaciones();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostulacionDto> actualizarPostulacion(
            @PathVariable Long id,
            @RequestBody PostulacionDto dto) {
        PostulacionDto actualizada = postulacionService.updatePostulacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPostulacion(@PathVariable Long id) {
        postulacionService.deletePostulacionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vacante/{idVacante}")
    public ResponseEntity<List<PostulacionDto>> obtenerPorVacante(@PathVariable Long idVacante) {
        List<PostulacionDto> lista = postulacionService.findPostulacionesByVacante(idVacante);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PostulacionDto>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        List<PostulacionDto> lista = postulacionService.findPostulacionesByUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }
}
