package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoCita;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "citas")
@Builder
@Data
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="fecha_cita",nullable = false)
    private LocalDate fecha ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_postulante",nullable = false)
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_postulacion",nullable = false)
    private Postulacion postulacion;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado_cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = false)
    private Empresa empresa;

}
