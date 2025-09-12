package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "usuarios_registrados")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class UsuarioRegistrado extends Usuario{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @Column(name="fecha_registro",nullable = false)
    private LocalDateTime fecha_registro;

    @Enumerated(EnumType.STRING)
    private EstadoUsuarioRegistrado estado_usuario_registrado;

    @Column(name="telefono_usuario",length = 10)
    private String telefono_usuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_requisito",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_requisito")
    )
    private List<Requisito> requisitos;

}
