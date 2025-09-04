package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.dto.TipoEmpresaDto;
import com.afk.backend.model.entity.Calificacion;
import com.afk.backend.model.entity.TipoEmpresa;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring")
public interface TipoEmpresaMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    TipoEmpresa toEntity(TipoEmpresaDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    TipoEmpresaDto toDto(TipoEmpresa tipoEmpresa);

    default List<TipoEmpresaDto> toDtoList(Iterable<TipoEmpresa> tiposEmpresa) {
        return StreamSupport.stream(tiposEmpresa.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TipoEmpresaDto dto, @MappingTarget TipoEmpresa entity);
}