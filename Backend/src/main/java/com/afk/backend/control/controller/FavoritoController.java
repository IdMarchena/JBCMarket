package com.afk.backend.control.controller;

import com.afk.backend.control.dto.FavoritoDto;
import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @PostMapping
    public ResponseEntity<FavoritoDto> createFavorito(@RequestBody FavoritoDto favoritoDto) {
        FavoritoDto created = favoritoService.createFavorito(favoritoDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoDto> getFavoritoById(@PathVariable Long id) {
        FavoritoDto favorito = favoritoService.findFavoritoById(id);
        return ResponseEntity.ok(favorito);
    }

    @GetMapping
    public ResponseEntity<List<FavoritoDto>> getAllFavoritos() {
        List<FavoritoDto> favoritos = favoritoService.findAllFavoritos();
        return ResponseEntity.ok(favoritos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        favoritoService.deleteFavoritoById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<FavoritoDto>> getFavoritosByUsuario(@PathVariable Long idUsuario) {
        List<FavoritoDto> favoritos = favoritoService.findFavoritosByUsuario(idUsuario);
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/mutual/{idUsuario}")
    public ResponseEntity<List<FavoritoDto>> getMutualMatches(@PathVariable Long idUsuario) {
        List<FavoritoDto> matches = favoritoService.findMutualMatches(idUsuario);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/gerente/{idGerente}")
    public ResponseEntity<List<UsuarioDto>> getUsuariosFavoritosDeGerente(@PathVariable Long idGerente) {
        List<UsuarioDto> usuarios = favoritoService.findUsuariosFavoritosDeGerente(idGerente);
        return ResponseEntity.ok(usuarios);
    }
}
