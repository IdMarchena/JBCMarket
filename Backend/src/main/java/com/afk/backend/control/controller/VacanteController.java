package com.afk.backend.control.controller;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.control.service.VacanteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vacantes")
@RequiredArgsConstructor
public class VacanteController {

    private final VacanteService vacanteService;

    @PostMapping("/createVacante")
    public ResponseEntity<VacanteDto> crearVacante(@RequestBody VacanteDto vacanteDto) {
        VacanteDto creado = vacanteService.createVacante(vacanteDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/getVacanteId/{id}")
    public ResponseEntity<VacanteDto> obtenerVacantePorId(@PathVariable Long id) {
        VacanteDto vacante = vacanteService.findVacanteById(id);
        return ResponseEntity.ok(vacante);
    }

    @GetMapping("/findAllVacantes")
    public ResponseEntity<List<VacanteDto>> listarVacantes() {
        List<VacanteDto> vacantes = vacanteService.findAllVacantes();
        return ResponseEntity.ok(vacantes);
    }

    @GetMapping("/findAllVacandesByIdGerente/{idGerente}")
    public ResponseEntity<List<VacanteDto>> listarVacantesPorGerente(@PathVariable Long idGerente) {
        List<VacanteDto> vacantes = vacanteService.findVacantesByGerenteId(idGerente);
        return ResponseEntity.ok(vacantes);
    }

    @GetMapping("/findAllVacantesByIdEmpresa/{idEmpresa}")
    public ResponseEntity<List<VacanteDto>> listarVacantesPorEmpresa(@PathVariable Long idEmpresa) {
        List<VacanteDto> vacantes = vacanteService.findVacantesByEmpresaId(idEmpresa);
        return ResponseEntity.ok(vacantes);
    }

    @GetMapping("/findAllVacantesByName/{nombre}")
    public ResponseEntity<List<VacanteDto>> listarVacantesPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(vacanteService.findVacantesByNombre(nombre));
    }

    @DeleteMapping("/deleteVacante/{id}")
    public ResponseEntity<Void> eliminarVacante(@PathVariable Long id) {
        vacanteService.deleteVacanteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getVacanciesCuantityByEmpresaId/{empresaId}")
    public ResponseEntity<String> countVacantesByEmpresaId(@PathVariable Long id){
        return ResponseEntity.ok("esta es la cantidad de vacantes de la empresa con id: "+id+vacanteService.countVacantesByEmpresaId(id));
    }

    @PutMapping("/updateVacancies/{id}")
    public ResponseEntity<VacanteDto> actualizarCalificacion(@PathVariable Long id, @RequestBody VacanteDto dto) {
        VacanteDto actualizada = vacanteService.updateVacante(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/searchVacanciesByFiltro")
    public ResponseEntity<Page<VacanteDto>> buscarCalificaciones(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<VacanteDto> resultados= vacanteService.findByNombreContaining(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }
}
