package com.afk.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "historial_postulantes")
@Builder
@Data
public class HistorialPostulante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "fecha_registro_postulante", nullable = false)
    private LocalDateTime fecha_historial_postulante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_postulacion", nullable = false)
    private Postulacion postulacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_postulante", nullable = false)
    private Usuario usuario;


}
