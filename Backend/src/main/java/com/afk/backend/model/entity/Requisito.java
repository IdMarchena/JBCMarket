package com.afk.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "requisitos")
@Builder
@Data
public class Requisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_requisito")
    private Long id;

    @Column(name="descripcion_requisito",nullable = false,length = 250)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vacante_id", nullable=false)
    private Vacante vacante;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Tipo_Requisito",nullable = false)
    private TipoRequisito tipo;


}
