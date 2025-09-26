package com.afk.backend.control.service;

import com.afk.backend.control.dto.FavoritoDto;
import com.afk.backend.control.dto.UsuarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface FavoritoService {
    FavoritoDto createFavorito(FavoritoDto favorito);
    FavoritoDto findFavoritoById(Long id);
    List<FavoritoDto> findAllFavoritos();
    void deleteFavoritoById(Long id);
    List<FavoritoDto> findFavoritosByUsuario(Long idUsuario);
    List<UsuarioDto> findUsuariosFavoritosDeGerente(Long idGerente);
    List<FavoritoDto> findMutualMatches(Long idUsuario);
    Integer obtenerCantidadMatchsByUsuario(Long idUsuario);
    Page<FavoritoDto> searchFavoritosByFecha(LocalDateTime fecha, Pageable pageable);


}
