package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.TipoRequisitoDto;
import com.afk.backend.model.entity.TipoRequisito;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring")
public interface TipoRequisitoMapper {

    TipoRequisito toEntity(TipoRequisitoDto dto);

    TipoRequisitoDto toDto(TipoRequisito tipoRequisito);

    default List<TipoRequisitoDto> toDtoList(List<TipoRequisito> tiposRequisito) {
        return tiposRequisito.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<TipoRequisitoDto> toDtoList(Iterable<TipoRequisito> tiposRequisito) {
        return StreamSupport.stream(tiposRequisito.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}