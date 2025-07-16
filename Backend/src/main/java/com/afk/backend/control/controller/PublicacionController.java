package com.afk.backend.control.controller;

import com.afk.backend.control.dto.PublicacionDto;
import com.afk.backend.control.service.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    @PostMapping
    public ResponseEntity<PublicacionDto> crearPublicacion(@RequestBody PublicacionDto dto) {
        PublicacionDto creada = publicacionService.createPublicacion(dto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDto> obtenerPublicacionPorId(@PathVariable Long id) {
        PublicacionDto dto = publicacionService.findPublicacionById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PublicacionDto>> obtenerTodasLasPublicaciones() {
        List<PublicacionDto> lista = publicacionService.findAllPublicaciones();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDto> actualizarPublicacion(
            @PathVariable Long id,
            @RequestBody PublicacionDto dto) {
        PublicacionDto actualizada = publicacionService.updatePublicacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        publicacionService.deletePublicacionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vacante/{idVacante}")
    public ResponseEntity<List<PublicacionDto>> obtenerPorVacante(@PathVariable Long idVacante) {
        List<PublicacionDto> lista = publicacionService.findByVacanteId(idVacante);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<PublicacionDto>> obtenerPorEmpresa(@PathVariable Long idEmpresa) {
        List<PublicacionDto> lista = publicacionService.findByEmpresaId(idEmpresa);
        return ResponseEntity.ok(lista);
    }
}
