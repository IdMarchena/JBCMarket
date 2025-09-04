package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Chat;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.entity.Vacante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findChatByNombre(String nombre);

    Page<Empresa> findByNombreContaining(String nombre, Pageable pageable);

    @Query("SELECT c FROM Empresa c WHERE c.usuario.id = :userId OR c.usuario.id = :userId")
    List<Empresa> findByUsuarioId(@Param("userId") Long userId);

    List<Vacante> findVacantesByUsuarioId(Long id);

    @Query("SELECT DISTINCT e FROM Empresa e " +
            "LEFT JOIN e.vacantes v " +
            "LEFT JOIN v.requisitos r " +
            "WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(r.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Empresa> searchByEmpresaOrRequisito(@Param("keyword") String keyword, Pageable pageable);

}
