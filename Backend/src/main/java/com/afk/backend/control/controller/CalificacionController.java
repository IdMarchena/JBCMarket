package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.service.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService calificacionService;

    @PostMapping("/crearCalificacion")
    public ResponseEntity<CalificacionDto> crearCalificacion(@RequestBody CalificacionDto calificacionDto) {
        CalificacionDto creada = calificacionService.createCalificacion(calificacionDto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/ObtenerCalificacionPorId/{id}")
    public ResponseEntity<CalificacionDto> obtenerPorId(@PathVariable Long id) {
        CalificacionDto calificacion = calificacionService.findCalificacionById(id);
        return ResponseEntity.ok(calificacion);
    }

    @GetMapping("/listarCalificaciones")
    public ResponseEntity<List<CalificacionDto>> listarTodas() {
        List<CalificacionDto> calificaciones = calificacionService.findAllCalificaciones();
        return ResponseEntity.ok(calificaciones);
    }

    @PutMapping("/actualizarCalificacion/{id}")
    public ResponseEntity<CalificacionDto> actualizarCalificacion(@PathVariable Long id, @RequestBody CalificacionDto dto) {
        CalificacionDto actualizada = calificacionService.updateCalificacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/deleteCalificacion/{id}")
    public ResponseEntity<Void> eliminarCalificacion(@PathVariable Long id) {
        calificacionService.deleteCalificacionById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/obtenerCantidadCalificaciones")
    public ResponseEntity<String> obtenerCantidadCalificaciones() {
        return ResponseEntity.ok("cantidad de ubicaciones:"+
                calificacionService.obtenerCantidadCalificaciones());
    }
    @GetMapping("/buscarCalificaciones")
    public ResponseEntity<Page<CalificacionDto>> buscarCalificaciones(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<CalificacionDto> resultados= calificacionService.buscarCalificaciones(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }
}
