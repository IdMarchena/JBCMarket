package com.afk.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "calificaciones")
@Builder
@Data
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "puntaje_calificacion", nullable = false)
    private Integer puntaje;

    @Column(name="comentario_calificacion",nullable = false,length = 100)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_postulante", nullable = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Publicacion", nullable = true)
    private Publicacion publicacion;

}
