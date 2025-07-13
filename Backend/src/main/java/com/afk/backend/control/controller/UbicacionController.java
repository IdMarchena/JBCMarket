package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.UbicacionDTO;
import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.service.impl.UbicacionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionServiceImpl ubicacionService;

    public UbicacionController(UbicacionServiceImpl ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping("/showMap")
    public String index(){
        return "index";
    }

    @GetMapping("/coordenadas")
    public Mono<UbicacionDt> obtenerCoordenadas(@RequestParam String direccion) {
        return ubicacionService.obtenerCoordenadas(direccion);
    }

    @GetMapping("/{id}")
    public Mono<UbicacionDt> obtenerUbicacion(@PathVariable Long id) {

        return ubicacionService.getUbicacion(id);
    }

    @GetMapping("/sincronizar/{id}")
    public ResponseEntity<String> sincronizar(@PathVariable Long id) {
        ubicacionService.sincronizarUbicacion(id);
        return ResponseEntity.ok("Ubicación sincronizada");
    }
}
