package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    @Query("SELECT p FROM Publicacion p WHERE p.vacante.id = :idVacante")
    List<Publicacion> findByVacanteId(Long idVacante);
    @Query("SELECT p FROM Publicacion p WHERE p.vacante.empresa.id = :idEmpresa")
    List<Publicacion> findByVacanteEmpresaId(Long idEmpresa);
    @Query("SELECT p FROM Publicacion p JOIN p.vacante.requisitos r WHERE r IN (SELECT ur FROM UsuarioRegistrado u JOIN u.requisitos ur WHERE u.id = :idUsuario)")
    List<Publicacion> findByRequisitosUsuarioRegistrado(@Param("idUsuario")Long idUsuario);
    @Query("SELECT COUNT(p) FROM Publicacion p WHERE p.vacante.empresa.id = :idEmpresa")
    Integer countByPublicacionEmpresaId(Long idEmpresa);

    Page<Publicacion> findByTituloContaining(String filtro, Pageable pageable);
}

