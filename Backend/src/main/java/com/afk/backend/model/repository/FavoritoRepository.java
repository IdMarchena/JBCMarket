package com.afk.backend.model.repository;
import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.model.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {


    @Query("SELECT COUNT(f) > 0 FROM Favorito f WHERE " +
            "f.usuario.id = :idUsuarioEmpresa AND " +
            "f.publicacion.id = :idPublicacionUsuario")
    boolean existsMutualMatch(@Param("idUsuarioEmpresa") Long idUsuarioEmpresa,
                              @Param("idPublicacionUsuario") Long idPublicacionUsuario);


    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId")
    List<Favorito> findByUsuarioId(@Param("usuarioId") Long usuarioId);


    @Query("SELECT new com.afk.backend.control.dto.UsuarioDto(u.id, u.nombre, u.correo, null) " +
            "FROM Favorito f JOIN f.usuario u " +
            "WHERE f.publicacion.vacante.empresa.usuario.id = :idGerente")
    List<UsuarioDto> findUsuariosFavoritosByGerenteId(@Param("idGerente") Long idGerente);
}