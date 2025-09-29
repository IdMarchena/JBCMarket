package com.afk.backend.model.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "favoritos")
@Builder
@Data
public class Favorito {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="id_favorito")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    @Column(name="fecha_favorito",nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_perfil")
    private Perfil perfil;

}
