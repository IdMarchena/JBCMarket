package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoChat;
import com.afk.backend.model.entity.enm.EstadoSancion;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sanciones")
@Builder
@Data
public class Sancion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="descripcion_sancion",nullable = false,length = 250)
    private String descripcion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publicacion",nullable = false)
    private Publicacion publicacion;

    @Column(name="fecha_sancion",nullable = false)
    private LocalDateTime fecha_sancion;

    @Enumerated(EnumType.STRING)
    private EstadoSancion estado_sancion;

}
