package com.afk.backend.model.repository;
import com.afk.backend.model.entity.Favorito;
import com.afk.backend.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    Page<Favorito> findByFechaContaining(LocalDateTime fecha, Pageable pageable);

    @Query("SELECT COUNT(f) > 0 FROM Favorito f WHERE " +
            "f.usuario.id = :idUsuarioEmpresa AND " +
            "(f.publicacion.id = :idPublicacionUsuario OR f.perfil.id = :idPerfil)")
    boolean existsMutualMatchProfile(@Param("idUsuarioEmpresa") Long idUsuarioEmpresa,
                                     @Param("idPublicacionUsuario") Long idPublicacionUsuario,
                                     @Param("idPerfil") Long idPerfil);

    @Query("""
SELECT f
FROM Favorito f
WHERE 
    (
        f.publicacion IS NOT NULL
        AND (f.usuario.id = :idUsuario OR f.usuario.id = :idUsuarioGerente)
        AND EXISTS (
            SELECT 1
            FROM Favorito f2
            WHERE 
                f2.perfil IS NOT NULL
                AND f2.perfil.id = f.publicacion.id
                AND f2.usuario.id IN (:idUsuario, :idUsuarioGerente)
                AND f.usuario.id <> f2.usuario.id
        )
    )
    OR
    (
        f.perfil IS NOT NULL
        AND (f.usuario.id = :idUsuario OR f.usuario.id = :idUsuarioGerente)
        AND EXISTS (
            SELECT 1
            FROM Favorito f3
            WHERE 
                f3.publicacion IS NOT NULL
                AND f3.publicacion.id = f.perfil.id
                AND f3.usuario.id IN (:idUsuario, :idUsuarioGerente)
                AND f.usuario.id <> f3.usuario.id
        )
    )
""")
    List<Favorito> existMatch(@Param("idUsuario") Long idUsuario,
                              @Param("idUsuarioGerente") Long idUsuarioGerente);
















    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId")
    List<Favorito> findByUsuarioId(@Param("usuarioId") Long usuarioId);


    @Query("SELECT new Usuario (u.id, u.nombre, u.correo, null, u.perfil) " +
            "FROM Favorito f JOIN f.usuario u " +
            "WHERE f.publicacion.vacante.empresa.usuario.id = :idGerente")
    List<Usuario> findUsuariosFavoritosByUsuario(@Param("idGerente") Long idGerente);
}