package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoCita;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cita")
    private Long id;

    @Column(name="fecha_cita",nullable = false)
    private LocalDateTime fecha ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_postulante",nullable = false)
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postulacion_id",nullable = false)
    private Postulacion postulacion;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado_cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id",nullable = false)
    private Empresa empresa;

}
