package com.afk.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vacantes")
@Builder
@Data
public class Requisito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="descripcion_requisito",nullable = false,length = 250)
    private String descripcion_requisito;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_vacante",nullable = false)
    private Vacante vacante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Tipo_Requisito",nullable = false)
    private TipoRequisito tipo_requisito;
}
