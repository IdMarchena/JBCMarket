package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.OfertaLaboralDto;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring",
        uses = {
                HistorialPostulanteMapper.class,
                VacanteMapper.class,
                CalificacionMapper.class,
                CitaMapper.class,
                FavoritoMapper.class,
                PublicacionMapper.class,
                EmpresaMapper.class
        })
public interface OfertaLaboralMapper {

    default List<HistorialPostulante> mapHistorialPostulantes(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            HistorialPostulante h = new HistorialPostulante();
            h.setId(id);
            return h;
        }).collect(Collectors.toList());
    }

    default List<Vacante> mapVacantes(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Vacante v = new Vacante();
            v.setId(id);
            return v;
        }).collect(Collectors.toList());
    }

    default List<Calificacion> mapC(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Calificacion c = new Calificacion();
            c.setId(id);
            return c;
        }).collect(Collectors.toList());
    }

    default List<Favorito> mapF(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Favorito f = new Favorito();
            f.setId(id);
            return f;
        }).collect(Collectors.toList());
    }
    default List<Publicacion> mapP(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Publicacion p = new Publicacion();
            p.setId(id);
            return p;
        }).collect(Collectors.toList());
    }
    default List<Empresa> mapE(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Empresa e = new Empresa();
            e.setId(id);
            return e;
        }).collect(Collectors.toList());
    }
    default List<Cita> mapCi(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Cita c = new Cita();
            c.setId(id);
            return c;
        }).collect(Collectors.toList());
    }


    @Mapping(target = "historial_postulantes", expression = "java(mapHistorialPostulantes(dto.idsHistorialPostulantes()))")
    @Mapping(target = "vacantes", expression = "java(mapVacantes(dto.idsVacantes()))")
    @Mapping(target = "calificaciones", expression = "java(mapC(dto.idsCalificaciones()))")
    @Mapping(target = "citas", expression = "java(mapCi(dto.idsCalificaciones()))")
    @Mapping(target = "favoritos", expression = "java(mapF(dto.idsFavoritos()))")
    @Mapping(target = "publicacions", expression = "java(mapP(dto.idsPublicaciones()))")
    @Mapping(target = "empresas", expression = "java(mapE(dto.idsEmpresas()))")
    OfertaLaboral toEntity(OfertaLaboralDto dto);

    @Mapping(target = "idsHistorialPostulantes", expression = "java(oferta.getHistorial_postulantes() != null ? oferta.getHistorial_postulantes().stream().map(h -> h.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "idsVacantes", expression = "java(oferta.getVacantes() != null ? oferta.getVacantes().stream().map(v -> v.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "idsCalificaciones", expression = "java(oferta.getCalificaciones() != null ? oferta.getCalificaciones().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "idsCitas", expression = "java(oferta.getCitas() != null ? oferta.getCitas().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "idsFavoritos", expression = "java(oferta.getFavoritos() != null ? oferta.getFavoritos().stream().map(f -> f.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "idsPublicaciones", expression = "java(oferta.getPublicacions() != null ? oferta.getPublicacions().stream().map(p -> p.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "idsEmpresas", expression = "java(oferta.getEmpresas() != null ? oferta.getEmpresas().stream().map(e -> e.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    OfertaLaboralDto toDto(OfertaLaboral oferta);

    default List<OfertaLaboralDto> toDtoList(Iterable<OfertaLaboral> ofertas) {
        return StreamSupport.stream(ofertas.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historial_postulantes", ignore = true)
    @Mapping(target = "vacantes", ignore = true)
    @Mapping(target = "calificaciones", ignore = true)
    @Mapping(target = "citas", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "publicacions", ignore = true)
    @Mapping(target = "empresas", ignore = true)
    void updateEntityFromDto(@MappingTarget OfertaLaboral entity, OfertaLaboralDto dto);
}