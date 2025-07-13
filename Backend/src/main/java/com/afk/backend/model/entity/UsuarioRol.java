package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoChat;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuarios_roles")
@Builder
@Data
public class UsuarioRol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario-registrado",nullable = false)
    private UsuarioRegistrado usuarioRegistrado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol",nullable = false)
    private Rol rol;

    @Column(name="fecha_activacion_rol",nullable = false)
    private LocalDateTime fechaActivacionRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_usuario_rol")
    private EstadoUsuarioRol estadoUsuarioRol;

    @Column(name = "fecha_fin_rol")
    private LocalDateTime fecha_fin_rol;

}
