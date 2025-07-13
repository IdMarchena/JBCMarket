package com.afk.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "postulaciones")
@Builder
@Data
public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "contenido_postulacion", nullable = false,length = 1000)
    private String contenido_postulacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vacante", nullable = false)
    private Vacante vacante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_postulante", nullable = false)
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_gerente", nullable = false)
    private Usuario usuarioGerente;

    @Column(name = "estado_postulacion", nullable = false,length = 250)
    private String estadoPostulacion;

    @Column(name = "fecha_inicio_postulacion", nullable = false)
    private String fechaInicioPostulacion;

    @Column(name = "fecha_fin_postulacion", nullable = false)
    private String fechafincioPostulacion;
}
