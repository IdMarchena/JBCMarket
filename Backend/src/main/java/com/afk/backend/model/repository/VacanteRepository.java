package com.afk.backend.model.repository;
import com.afk.backend.model.entity.Vacante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacanteRepository extends JpaRepository<Vacante, Long> {

    @Query("SELECT v FROM Vacante v WHERE v.empresa.usuario.id = :idGerente")
    List<Vacante> findByEmpresa_UsuarioGerente_Id(Long idGerente);
    List<Vacante> findByEmpresa_Id(Long idEmpresa);
    List<Vacante> findByEmpresaUsuarioId(Long idUsuario);
    List<Vacante> findByNombre(String nombre);
    Page<Vacante> findByNombreContaining(String nombre, Pageable pageable);

}
