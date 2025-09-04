package com.afk.backend.control.controller;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.dto.PublicacionDto;
import com.afk.backend.control.service.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    @GetMapping("/getCuantityPostEmpress/{idEmpresa}")
    public ResponseEntity<Integer> obtenerNumeroPublicacionesEmpresa(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(publicacionService.obtenerCantidadPublicacionesEmpresa(idEmpresa));
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<PublicacionDto> obtenerPublicacionPorId(@PathVariable Long id) {
        PublicacionDto dto = publicacionService.findPublicacionById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/GetPublicacionByIdVacante/{idVacante}")
    public ResponseEntity<List<PublicacionDto>> obtenerPorVacante(@PathVariable Long idVacante) {
        List<PublicacionDto> lista = publicacionService.findByVacanteId(idVacante);
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/getPostByIdEmprss/{idEmpresa}")
    public ResponseEntity<List<PublicacionDto>> obtenerPorEmpresa(@PathVariable Long idEmpresa) {
        List<PublicacionDto> lista = publicacionService.findByEmpresaId(idEmpresa);
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/getAllPost")
    public ResponseEntity<List<PublicacionDto>> obtenerTodasLasPublicaciones() {
        List<PublicacionDto> lista = publicacionService.findAllPublicaciones();
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/searchPostByFilter")
    public ResponseEntity<Page<PublicacionDto>> buscarCalificaciones(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<PublicacionDto> resultados= publicacionService.searchPostByFilter(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping("/createPost")
    public ResponseEntity<PublicacionDto> crearPublicacion(@RequestBody PublicacionDto dto) {
        PublicacionDto creada = publicacionService.createPublicacion(dto);
        return ResponseEntity.ok(creada);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        publicacionService.deletePublicacionById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/updatePost/{id}")
    public ResponseEntity<PublicacionDto> actualizarPublicacion(
            @PathVariable Long id,
            @RequestBody PublicacionDto dto) {
        PublicacionDto actualizada = publicacionService.updatePublicacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/getForYouByUsuario/{idUsuario}")
    public ResponseEntity<List<PublicacionDto>> obtenerPorRequisitosUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(publicacionService.findByRequisitosUsuarioRegistrado(idUsuario));
    }


}
