package com.afk.backend.model.repository;


import com.afk.backend.model.entity.UsuarioRegistrado;
import com.afk.backend.model.entity.UsuarioRol;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    List<UsuarioRol> findByUsuarioRegistradoIdOrderByFechaActivacionRolDesc(Long usuarioId);
    Optional<UsuarioRol> findByUsuarioRegistradoAndEstadoUsuarioRol(UsuarioRegistrado usuario, EstadoUsuarioRol estadoUsuarioRol);
    List<UsuarioRol> findByRolId(Long rolId);
    List<UsuarioRol> findAllByUsuarioRegistradoAndEstadoUsuarioRol(UsuarioRegistrado usuario, EstadoUsuarioRol estadoUsuarioRol);

}
