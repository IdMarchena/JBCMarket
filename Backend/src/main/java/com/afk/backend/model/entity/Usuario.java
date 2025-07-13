package com.afk.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre_usuario",nullable = false, length = 250)
    private String nombre;

    @Column(name = "correo_usuario",nullable = false, length = 250)
    private String correo;

    @Column(name="contraseña",nullable = false)
    private String contrasenia;


}
