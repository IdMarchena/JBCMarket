package com.afk.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "Perfiles")

public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_perfil")
    private Long id;

    @Column(name="Perfil Name")
    private String perfilName;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proyectos_id")
    private List<Proyecto> proyectos;

    @Column(name="fotoPerfil")
    private String urlForto;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
