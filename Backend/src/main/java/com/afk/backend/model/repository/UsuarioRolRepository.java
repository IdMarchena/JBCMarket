package com.afk.backend.model.repository;


import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.UsuarioRegistrado;
import com.afk.backend.model.entity.UsuarioRol;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    List<UsuarioRol> findByUsuarioRegistradoIdOrderByFechaActivacionRolDesc(Long usuarioId);
    Optional<UsuarioRol> findByUsuarioRegistradoAndEstadoUsuarioRol(UsuarioRegistrado usuario, EstadoUsuarioRol estadoUsuarioRol);
    List<UsuarioRol> findAllByUsuarioRegistradoAndEstadoUsuarioRol(UsuarioRegistrado usuario, EstadoUsuarioRol estadoUsuarioRol);

    @Query("SELECT ur FROM UsuarioRol ur " +
            "JOIN ur.rol r " +             // Relacionamos con la entidad Rol
            "JOIN ur.usuarioRegistrado u " + // Relacionamos con la entidad UsuarioRegistrado
            "WHERE ur.id_usuario = :id_usuario " + // Filtramos por id_usuario
            "AND r.role = 'ROLE_GERENTE'")     // Filtramos por el rol GERENTE
    UsuarioRol getUsuarioRolByIdUsuario(Long id_usuario);

}
