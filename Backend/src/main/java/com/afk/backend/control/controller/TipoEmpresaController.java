package com.afk.backend.control.controller;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.dto.TipoEmpresaDto;
import com.afk.backend.control.service.TipoEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipoEmpresa")
@RequiredArgsConstructor
public class TipoEmpresaController {

    private final TipoEmpresaService tipoEmpresaService;

    @PostMapping("/createCompany")
    public ResponseEntity<TipoEmpresaDto> crearTipoEmpresa(@RequestBody TipoEmpresaDto tipoEmpresaDto) {
        TipoEmpresaDto creado = tipoEmpresaService.createTipoEmpresa(tipoEmpresaDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/getTipoEmpresaById/{id}")
    public ResponseEntity<TipoEmpresaDto> obtenerTipoEmpresaPorId(@PathVariable Long id) {
        TipoEmpresaDto tipoEmpresa = tipoEmpresaService.findTipoEmpresaById(id);
        return ResponseEntity.ok(tipoEmpresa);
    }

    @GetMapping("/findAllEmpress")
    public ResponseEntity<List<TipoEmpresaDto>> listarTiposEmpresa() {
        List<TipoEmpresaDto> tipos = tipoEmpresaService.findAllTiposEmpresa();
        return ResponseEntity.ok(tipos);
    }

    @DeleteMapping("/deteleTipoEmpresaById/{id}")
    public ResponseEntity<Void> eliminarTipoEmpresa(@PathVariable Long id) {
        tipoEmpresaService.deleteTipoEmpresaById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getcuantityCompany")
    public ResponseEntity<String> getCuantityCompany() {
        return ResponseEntity.ok("esta es la cantidad de empresa"+ tipoEmpresaService.getCuantityCompany());
    }

    @GetMapping("/buscarTipoEmpresa")
    public ResponseEntity<Page<TipoEmpresaDto>> buscarTipoEmpresa(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<TipoEmpresaDto> resultados= tipoEmpresaService.searchTipoEmpresa(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }
    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<TipoEmpresaDto> actualizarEmpresa(@PathVariable Long id, @RequestBody TipoEmpresaDto dto) {
        TipoEmpresaDto actualizada = tipoEmpresaService.updateTipoEmpresa(id, dto);
        return ResponseEntity.ok(actualizada);
    }
}
