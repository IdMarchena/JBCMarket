package com.afk.backend.control.controller;

import com.afk.backend.control.dto.ProyectoDto;
import com.afk.backend.control.service.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoService proyectoService;

    @GetMapping("/getCuantityProyectoByIdPerfil/{id}")
    public ResponseEntity<String> getProyectoByIdPerfil(@PathVariable Long id){
        return ResponseEntity.ok("esta es la cantidad de proyectos del perfil"+proyectoService.cuantityProyectoByIdPerfil(id));
    }

    @PostMapping("/createProject")
    public ResponseEntity<ProyectoDto> crearProject(@RequestPart("proyecto") ProyectoDto proyecto,
                                                 @RequestPart(value = "archivo",required = false) MultipartFile archivo) {
        System.out.println("Proyecto recibido: " + proyecto);
        System.out.println("Archivo recibido: " + (archivo != null ? archivo.getOriginalFilename() : "Ninguno"));
        return ResponseEntity.ok(proyectoService.createProyecto(proyecto, archivo));
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<ProyectoDto> buscarProyectoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.findProyectoById(id));
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<ProyectoDto>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.findAllProyectos());
    }
    @GetMapping("/getAllProjectsByIdPerfil/{id}")
    public ResponseEntity<List<ProyectoDto>> listarProyectosByIdPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.findAllProyectosByUsuario(id));
    }

    @PutMapping("/updateProjectById/{id}")
    public ResponseEntity<ProyectoDto> actualizarProyecto(@RequestPart("proyecto") ProyectoDto proyecto,
                                                          @PathVariable Long id,
                                                          @RequestPart(value = "archivo",required = false) MultipartFile archivo) {
        return ResponseEntity.ok(proyectoService.updateProyecto(id, proyecto,archivo));
    }

    @DeleteMapping("/deleteProjectById/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
