package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoChat;
import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "usuarios-registrados")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Builder
public class UsuarioRegistrado extends Usuario{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = true)
    private Rol rol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_ubicacion",nullable = false)
    private Ubicacion ubicacion;

    @Column(name="fecha_registro",nullable = false)
    private LocalDateTime fecha_registro;

    @Enumerated(EnumType.STRING)
    private EstadoUsuarioRegistrado estado_usuario_registrado;

    @Column(name="telefono_usuario",nullable = false,length = 10)
    private String telefono_usuario;

}
