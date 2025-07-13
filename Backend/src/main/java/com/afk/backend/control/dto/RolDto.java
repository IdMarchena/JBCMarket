package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.Roles;

public record RolDto(
        Integer id,
        Roles role
) {}