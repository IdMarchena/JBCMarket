package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record FavoritoDto(
        Long id,
        Long idUsuario,
        Long idPublicacion,
        LocalDateTime fechaFavorito
) {}