package com.afk.backend.control.controller;
import com.afk.backend.control.dto.CitaDto;
import com.afk.backend.control.service.impl.CitaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaServiceImpl citaService;

    public CitaController(CitaServiceImpl citaService) {
        this.citaService = citaService;
    }


    @GetMapping("/obtenerCantidadCitas")
    public ResponseEntity<String> obtenerCantidadCitas() {
        return ResponseEntity.ok("cantidad de citas:"+
                citaService.obtenerCantidadCitas());
    }

    @GetMapping("/obtenerCitaById/{id}")
    public ResponseEntity<String> obtenerCitaById(@PathVariable Long id) {
        return ResponseEntity.ok("cita encontrada"+
                citaService.findCitaById(id));
    }

    @GetMapping("/obtenerCitas")
    public ResponseEntity<List<CitaDto>> obtenerCitas() {
        return ResponseEntity.ok(citaService.findAllCitas());
    }

    @GetMapping("/buscarCitas")
    public ResponseEntity<Page<CitaDto>> buscarUbicaciones(
            @RequestParam LocalDateTime filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<CitaDto> resultados= citaService.buscarCitas(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping("/createCita")
    public ResponseEntity<CitaDto> createCita(@RequestBody CitaDto citaDto) {
        CitaDto creada= citaService.createCita(citaDto);
        return ResponseEntity.ok(creada);
    }
    @PutMapping("/updateCita")
    public ResponseEntity<String> updateCita(@RequestParam Long id,@RequestBody CitaDto ubicacion) {
        citaService.updateCita(id, ubicacion);
        return ResponseEntity.ok("cita actualizada");
    }
    @DeleteMapping("/deleteCita")
    public ResponseEntity<String> deleteCita(@RequestParam Long id) {
        citaService.deleteCitaById(id);
        return ResponseEntity.ok("cita eliminada");
    }

}
