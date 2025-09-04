package com.afk.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tipo_requisitos")
@Builder
@Data
public class TipoRequisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_requisito")
    private Long id;

    @Column(name="tipo_requisito",nullable = false,length = 250)
    private String tipo;
}
