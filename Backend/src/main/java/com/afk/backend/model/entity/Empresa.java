package com.afk.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nombre_emprpesa", nullable = false,length = 250)
    private String nombre;

    @Column(name="descripcion_empresa",nullable = false,length = 10000)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_gerente", nullable = false)
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_empresa", nullable = false)
    private TipoEmpresa tipo_Empresa;

    @Column(name = "numero_empleados", nullable = false)
    private int numero_empleados;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacante> vacantes = new ArrayList<>();

}
