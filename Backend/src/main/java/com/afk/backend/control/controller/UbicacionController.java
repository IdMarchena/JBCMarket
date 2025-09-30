        package com.afk.backend.control.controller;
        import com.afk.backend.client.external.dto.UbicacionDt;
        import com.afk.backend.control.service.impl.UbicacionServiceImpl;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.domain.Sort;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import reactor.core.publisher.Mono;

        import java.util.List;

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
            @GetMapping("/obtenerCantidadUbicaciones")
            public ResponseEntity<String> obtenerCantidadUbicaciones() {
                return ResponseEntity.ok("cantidad de ubicaciones:"+
                        ubicacionService.obtenerCantidadUbicaciones());
            }
            @GetMapping("/obtenerUbicaciones")
            public ResponseEntity<List<UbicacionDt>> obtenerUbicaciones() {
                return ResponseEntity.ok(ubicacionService.obtenerUbicaciones());
            }

            @GetMapping("/buscarUbicaciones")
            public ResponseEntity<Page<UbicacionDt>> buscarUbicaciones(
                    @RequestParam String filtro,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10")int size,
                    @RequestParam(defaultValue = "id") String sortBy,
                    @RequestParam(defaultValue = "asc") String sortDir) {
                Pageable pageable = PageRequest.of(page,
                        size,
                        sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
                Page<UbicacionDt> resultados= ubicacionService.buscarUbicaciones(filtro,pageable);
                return ResponseEntity.ok(resultados);
            }

            @PostMapping("/createUbicacion")
            public ResponseEntity<UbicacionDt> crearUbicacion(@RequestBody UbicacionDt ubicacion) {
                UbicacionDt creada= ubicacionService.createUbicacion(ubicacion);
                return ResponseEntity.ok(creada);
            }
            @PutMapping("/updateUbicacion")
            public ResponseEntity<UbicacionDt> updateUbicacion(@RequestParam Long id,@RequestBody UbicacionDt ubicacion) {
                UbicacionDt ubicacionDt = ubicacionService.updateUicacion(id, ubicacion);
                return ResponseEntity.ok(ubicacionDt);
            }
            @DeleteMapping("/deleteUbicacion")
            public ResponseEntity<String> deleteUbicacion(@RequestParam Long id) {
                ubicacionService.deleteUbicacion(id);
                return ResponseEntity.ok("ubicacion eliminada");
            }

        }
