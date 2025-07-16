package com.afk.backend.control.controller;

import com.afk.backend.control.dto.OfertaLaboralDto;
import com.afk.backend.control.service.OfertaLaboralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor
public class OfertaLaboralController {

    private final OfertaLaboralService ofertaLaboralService;

    @PostMapping
    public ResponseEntity<OfertaLaboralDto> crearOferta(@RequestBody OfertaLaboralDto dto) {
        OfertaLaboralDto created = ofertaLaboralService.createOferta(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaLaboralDto> obtenerOfertaPorId(@PathVariable Long id) {
        OfertaLaboralDto oferta = ofertaLaboralService.findOfertaById(id);
        return ResponseEntity.ok(oferta);
    }

    @GetMapping
    public ResponseEntity<List<OfertaLaboralDto>> obtenerTodasLasOfertas() {
        List<OfertaLaboralDto> ofertas = ofertaLaboralService.findAllOfertas();
        return ResponseEntity.ok(ofertas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {
        ofertaLaboralService.deleteOfertaById(id);
        return ResponseEntity.noContent().build();
    }
}
