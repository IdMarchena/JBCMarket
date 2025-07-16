package com.afk.backend.model.repository;


import com.afk.backend.model.entity.Rol;
import com.afk.backend.model.entity.enm.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRole(Roles name);
}
