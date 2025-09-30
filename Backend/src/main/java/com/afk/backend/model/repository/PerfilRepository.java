package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Page<Perfil> findByPerfilName(String perfilName, Pageable pageable);
}
