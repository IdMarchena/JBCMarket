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
@Table(name = "ofertas_laborales")
@Builder
@Data
public class OfertaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="fecha_inicio_sistema",nullable = false)
    private LocalDateTime fecha_inicio_sistema;

    @Column(name="fecha_fin_sistema",nullable = true)
    private LocalDateTime fecha_fin_sistema;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial_postulante",nullable = false)
    private List<HistorialPostulante> historial_postulantes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vacante",nullable = false)
    private List<Vacante> vacantes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_calificacion",nullable = false)
    private List<Calificacion> calificaciones;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita",nullable = false)
    private List<Cita> citas;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_favorito",nullable = false)
    private List<Favorito> favoritos;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publicacion",nullable = false)
    private List<Publicacion> publicacions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = false)
    private List<Empresa> empresas;
}
