package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    @Query("SELECT p FROM Postulacion p WHERE p.vacante.id = :idVacante")
    List<Postulacion> findByVacanteId(Long idVacante);

    @Query("SELECT p FROM Postulacion p WHERE p.usuario.id = :idUsuario")
    List<Postulacion> findByUsuarioId(Long idUsuario);
}
