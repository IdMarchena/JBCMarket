package com.afk.backend.control.controller;
import com.afk.backend.control.dto.PerfilDto;
import com.afk.backend.control.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/perfiles")
@RequiredArgsConstructor
public class PerfilController {
    private final PerfilService perfilService;

    @PostMapping("/addImage")
    public ResponseEntity<PerfilDto> subirImagen(@RequestParam("archivo") MultipartFile archivo,
                                                 @RequestParam("idUsuario") Long idUsuario) {
        return ResponseEntity.ok(perfilService.agregarImagen(idUsuario, archivo));
    }

    @DeleteMapping("/deleteImage/{idUsuario}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Long idUsuario) {
        perfilService.eliminarImagen(idUsuario);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/createPerfil")
    public ResponseEntity<PerfilDto> crearPerfil(@RequestPart("perfil") PerfilDto perfilDto,
                                                 @RequestPart(value = "archivo",required = false) MultipartFile archivo) {
        return ResponseEntity.ok(perfilService.createPerfil(perfilDto, archivo));
    }

    @GetMapping("/getProfileById/{id}")
    public ResponseEntity<PerfilDto> buscarPerfilPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.findPerfilById(id));
    }

    @GetMapping("/getAllProfile")
    public ResponseEntity<List<PerfilDto>> listarPerfil() {
        return ResponseEntity.ok(perfilService.findAllPerfil());
    }

    @PutMapping("/updatePerfiltById/{id}")
    public ResponseEntity<PerfilDto> actualizarProyecto(@RequestPart("proyecto") PerfilDto perfil,
                                                          @PathVariable Long id,
                                                          @RequestPart(value = "archivo",required = false) MultipartFile archivo) {
        return ResponseEntity.ok(perfilService.updatePerfil(id, perfil,archivo));
    }

    @DeleteMapping("/deletePerfil/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/searchPerfil")
    public ResponseEntity<Page<PerfilDto>> searchPerfil(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<PerfilDto> resultados= perfilService.searchPerfil(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }
    @GetMapping("/imagen/{idUsuario}")
    public ResponseEntity<Resource> verImagen(@PathVariable Long idUsuario) {
        Resource imagen = perfilService.obtenerImagen(idUsuario);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // o IMAGE_PNG según el caso
                .body(imagen);
    }

}
