package com.afk.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
@Table (name = "tipo_empresas")
public class TipoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_empresa")
    private Long id;

    @Column(name = "descripcion_id_tipo_empresa", nullable = false, length = 100)
    private String descripcion;
}

