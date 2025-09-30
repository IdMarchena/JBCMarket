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
@Table(name = "vacantes")
@Builder
@Data
public class Vacante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vacante")
    private Long id;

    @Column(name = "nombre_vacante", nullable = false, length = 250)
    private String nombre;

    @Column(name = "descripcion_vacante", nullable = false, length = 1000)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id", nullable = false)
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "fecha_vacante", nullable = false)
    private LocalDateTime fechaVcante;

    @OneToMany(mappedBy = "vacante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Requisito> requisitos;



}
