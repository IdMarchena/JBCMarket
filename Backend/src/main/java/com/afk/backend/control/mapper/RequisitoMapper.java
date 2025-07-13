package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.RequisitoDto;
import com.afk.backend.model.entity.Requisito;
import com.afk.backend.model.entity.TipoRequisito;
import com.afk.backend.model.entity.Vacante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequisitoMapper {

    default Vacante mapVacante(Long id) {
        if (id == null) return null;
        Vacante vacante = new Vacante();
        vacante.setId(id);
        return vacante;
    }

    default TipoRequisito mapTipoRequisito(Long id) {
        if (id == null) return null;
        TipoRequisito tipo = new TipoRequisito();
        tipo.setId(id);
        return tipo;
    }

    @Mapping(target = "vacante", expression = "java(mapVacante(dto.idVacante()))")
    @Mapping(target = "tipo_requisito", expression = "java(mapTipoRequisito(dto.idTipoRequisito()))")
    @Mapping(target = "descripcion_requisito", source = "descripcion")
    Requisito toEntity(RequisitoDto dto);


    @Mapping(target = "idVacante", source = "vacante.id")
    @Mapping(target = "idTipoRequisito", source = "tipo_requisito.id")
    @Mapping(target = "descripcion", source = "descripcion_requisito")
    RequisitoDto toDto(Requisito requisito);

    List<RequisitoDto> toDtoList(List<Requisito> requisitos);
}
