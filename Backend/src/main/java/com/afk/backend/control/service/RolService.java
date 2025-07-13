package com.afk.backend.control.service;

import com.afk.backend.control.dto.RolDto;

import java.util.List;

public interface RolService {
    RolDto createRol(RolDto rol);
    RolDto findRolById(Long id);
    List<RolDto> findAllRoles();
    void deleteRolById(Long id);
}
