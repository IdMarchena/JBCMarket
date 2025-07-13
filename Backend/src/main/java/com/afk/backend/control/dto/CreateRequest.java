package com.afk.backend.control.dto;

public record CreateRequest(
        Long usuarioRegistradoId,
        Long rolId,
        Long modificadoPorId
) {}
