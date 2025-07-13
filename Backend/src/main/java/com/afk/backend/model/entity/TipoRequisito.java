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
public class TipoRequisito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="tipo_requisito",nullable = false,length = 250)
    private String tipo;
}
