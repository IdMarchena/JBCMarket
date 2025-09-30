package com.afk.backend.control.controller;

import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/getCantidadUsuarios")
    public ResponseEntity<String> getCantidadUsuarios() {
        return ResponseEntity.ok("la cantidad de usuarios es: " + usuarioService.getCantidadUsuarios());
    }
    @GetMapping("/SearchUserByFilter")
    public ResponseEntity<Page<UsuarioDto>> SearchUserByFilter(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<UsuarioDto> resultados= usuarioService.SearchUserByFilter(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }
    @PostMapping("/createUser")
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto creado = usuarioService.createUsuario(usuarioDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDto usuario = usuarioService.findUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        List<UsuarioDto> usuarios = usuarioService.findAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UsuarioDto> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        UsuarioDto actualizado = usuarioService.updateUsuario(id, usuarioDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getUserByCorreo/{correo}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorCorreo(@PathVariable String correo) {
        UsuarioDto usuario = usuarioService.findByCorreo(correo);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/getUserByNombre/{nombre}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(usuarioService.findByNombre(nombre));
    }
}
