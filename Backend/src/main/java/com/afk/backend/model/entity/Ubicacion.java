package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoUbicacion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
@Table(name = "ubicaciones")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_padre")
    private Ubicacion padre;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_ubicacion")
    private EstadoUbicacion estado;
}