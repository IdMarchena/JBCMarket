package com.afk.backend.model.repository;

import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.model.entity.Favorito;
import com.afk.backend.model.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    @Query("SELECT p FROM Publicacion p WHERE p.vacante.id = :idVacante")
    List<Publicacion> findByVacanteId(Long idVacante);

    @Query("SELECT p FROM Publicacion p WHERE p.vacante.empresa.id = :idEmpresa")
    List<Publicacion> findByVacanteEmpresaId(Long idEmpresa);
}

