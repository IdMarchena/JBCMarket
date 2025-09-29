package com.afk.backend.control.controller;

import com.afk.backend.control.dto.FavoritoDto;
import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @PostMapping("/createFavorito")
    public ResponseEntity<FavoritoDto> createFavorito(@RequestBody FavoritoDto favoritoDto) {
        FavoritoDto created = favoritoService.createFavorito(favoritoDto);
        return ResponseEntity.ok(created);
    }
    //x
    @GetMapping("/obtenerFavoritoById/{id}")
    public ResponseEntity<FavoritoDto> getFavoritoById(@PathVariable Long id) {
        FavoritoDto favorito = favoritoService.findFavoritoById(id);
        return ResponseEntity.ok(favorito);
    }
    //x
    @GetMapping("/getAllFavorites")
    public ResponseEntity<List<FavoritoDto>> getAllFavoritesByAspirante() {
        List<FavoritoDto> favoritos = favoritoService.findAllFavoritos();
        return ResponseEntity.ok(favoritos);
    }
    @GetMapping("/getAllFavoritosbyUser/{id1}/{id2}")
    public ResponseEntity<List<FavoritoDto>> getAllFavoritosbyUser(@PathVariable Long id1, @PathVariable Long id2) {
        List<FavoritoDto> favoritos =favoritoService.matchesFavoritosByUsuario(id1, id2);
        return ResponseEntity.ok(favoritos);
    }


    @DeleteMapping("/deleteFavorito/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        favoritoService.deleteFavoritoById(id);
        return ResponseEntity.noContent().build();
    }
    //x
    @GetMapping("/getFavoritoByPostulante/{idUsuario}")
    public ResponseEntity<List<FavoritoDto>> getFavoritoByPostulante(@PathVariable Long idUsuario) {
        List<FavoritoDto> favoritos = favoritoService.findFavoritosByUsuario(idUsuario);
        return ResponseEntity.ok(favoritos);
    }
    //x
    @GetMapping("/getMutualMatches/{idUsuario}")
    public ResponseEntity<List<FavoritoDto>> getMutualMatches(@PathVariable Long idUsuario) {
        List<FavoritoDto> matches = favoritoService.findMutualMatches(idUsuario);
        return ResponseEntity.ok(matches);
    }
    //x
    @GetMapping("/getUsuariosFavoritosByGerente/{idGerente}")
    public ResponseEntity<List<UsuarioDto>> getUsuariosFavoritosDeGerente(@PathVariable Long idGerente) {
        List<UsuarioDto> usuarios = favoritoService.findUsuariosFavoritosDeGerente(idGerente);
        return ResponseEntity.ok(usuarios);
    }
     //x
    @GetMapping("/obtenerCantidadMatchsByUsuario/{idUsuario}")
    public ResponseEntity<String> obtenerCantidadMatchsByUsuario(@PathVariable Long idUsuario){
        return ResponseEntity.ok("esta es la cantidad de favoritos del usuario con id: "+
                idUsuario+
                favoritoService.obtenerCantidadMatchsByUsuario(idUsuario));
    }
    //x
    @GetMapping("/searchFavoritosByFecha")
    public ResponseEntity<Page<FavoritoDto>> searchFavoritosByFecha(
            @RequestParam LocalDateTime fecha,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<FavoritoDto    > resultados= favoritoService.searchFavoritosByFecha(fecha,pageable);
        return ResponseEntity.ok(resultados);
    }
}
