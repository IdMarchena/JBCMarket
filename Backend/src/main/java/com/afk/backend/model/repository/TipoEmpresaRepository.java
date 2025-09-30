package com.afk.backend.model.repository;

import com.afk.backend.model.entity.TipoEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEmpresaRepository extends JpaRepository<TipoEmpresa, Long> {
    Page<TipoEmpresa> findByDescripcionContaining(String filtro, Pageable pageable);
}
