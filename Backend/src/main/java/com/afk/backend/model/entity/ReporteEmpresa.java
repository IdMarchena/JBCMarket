package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EsatadoReporteEmpresa;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "reportes_empresa")
@Builder
@Data
public class ReporteEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="desripncion_reporte_empresa", nullable = false,length = 1000)
    private String desripncion;

    @Column(name="fecha_reporte", nullable = false)
    private LocalDateTime fechaReporte;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = false)
    private Empresa empresa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;

    @Column(name="tipo_reporte_empresa", nullable = false,length = 1000)
    private String tipo_reporte;

    @Enumerated(EnumType.STRING)
    private EsatadoReporteEmpresa estado_reporte;

    @Column(name="severidad_reporte_empresa", nullable = false,length = 1000)
    private String severidad;
}
