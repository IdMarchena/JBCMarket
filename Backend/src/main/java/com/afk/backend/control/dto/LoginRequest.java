package com.afk.backend.control.dto;

public record LoginRequest(
        String username,
        String password) {
}
