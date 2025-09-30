package com.afk.backend.control.dto;

public record LoginRequest(
        Long id,
        String username,
        String password) {
}
