package com.afk.backend.control.service.impl;

import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.control.mapper.VacanteMapper;
import com.afk.backend.control.service.VacanteService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoUbicacion;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacanteServiceImpl implements VacanteService {

    private final VacanteRepository vacanteRepository;
    private final UbicacionRepository ubicacionRepository;
    private final EmpresaRepository empresaRepository;
    @Qualifier("vacanteMapperImpl")
    private final VacanteMapper mapper;
    private final UbicacionServiceImpl ubicacionService;

    @Override
    @Transactional
    public VacanteDto createVacante(VacanteDto vacanteDto) {
        Empresa empresa = empresaRepository.findById(vacanteDto.idEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Ubicacion ubicacion = ubicacionRepository.findById(vacanteDto.idUbicacion())
                .orElseGet(() -> {
                    UbicacionDt ubicacionDt = ubicacionService.getUbicacion(vacanteDto.idUbicacion()).block();
                    if (ubicacionDt == null) {
                        throw new RuntimeException("Ubicación no encontrada localmente ni en el servicio externo");
                    }
                    Ubicacion nuevaUbicacion = new Ubicacion();
                    Ubicacion padre = ubicacionRepository.findById(ubicacionDt.id_padre())
                            .orElseThrow(() -> new RuntimeException("Ubicación padre no encontrada"));
                    nuevaUbicacion.setPadre(padre);
                    nuevaUbicacion.setId(ubicacionDt.id_ubicacion());
                    nuevaUbicacion.setPadre(padre);
                    nuevaUbicacion.setNombre(ubicacionDt.nombre());
                    nuevaUbicacion.setLongitud(ubicacionDt.longitud());
                    nuevaUbicacion.setLatitud(ubicacionDt.latitud());
                    nuevaUbicacion.setEstado(EstadoUbicacion.ACTIVA);
                    return ubicacionRepository.save(nuevaUbicacion);
                });


        Vacante vacante = mapper.toEntity(vacanteDto);
        vacante.setUbicacion(ubicacion);
        vacante.setEmpresa(empresa);

        Vacante savedVacante = vacanteRepository.save(vacante);
        return mapper.toDto(savedVacante);
    }

    @Override
    @Transactional(readOnly = true)
    public VacanteDto findVacanteById(Long id) {
        Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        if (shouldSyncUbicacion(vacante.getUbicacion())) {
            ubicacionService.sincronizarUbicacion(vacante.getUbicacion().getId());
        }

        return mapper.toDto(vacante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacanteDto> findAllVacantes() {
        List<Vacante> vacantes = vacanteRepository.findAll();

        vacantes.forEach(v -> {
            if (shouldSyncUbicacion(v.getUbicacion())) {
                ubicacionService.sincronizarUbicacion(v.getUbicacion().getId());
            }
        });

        return mapper.toDtoList(vacantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacanteDto> findVacantesByGerenteId(Long idGerente) {
        List<Vacante> vacantes = vacanteRepository.findByEmpresa_UsuarioGerente_Id(idGerente);
        vacantes.forEach(v -> {
            if (shouldSyncUbicacion(v.getUbicacion())) {
                ubicacionService.sincronizarUbicacion(v.getUbicacion().getId());
            }
        });
        return mapper.toDtoList(vacantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacanteDto> findVacantesByEmpresaId(Long idEmpresa) {
        List<Vacante> vacantes = vacanteRepository.findByEmpresa_Id(idEmpresa);
        vacantes.forEach(v -> {
            if (shouldSyncUbicacion(v.getUbicacion())) {
                ubicacionService.sincronizarUbicacion(v.getUbicacion().getId());
            }
        });
        return mapper.toDtoList(vacantes);
    }

    @Override
    @Transactional
    public void deleteVacanteById(Long id) {
        if (!vacanteRepository.existsById(id)) {
            throw new RuntimeException("Vacante no encontrada");
        }
        vacanteRepository.deleteById(id);
    }

    private boolean shouldSyncUbicacion(Ubicacion ubicacion) {
        return ubicacion.getLatitud() == null ||
                ubicacion.getLongitud() == null ||
                ubicacion.getEstado() != EstadoUbicacion.ACTIVA;
    }
}