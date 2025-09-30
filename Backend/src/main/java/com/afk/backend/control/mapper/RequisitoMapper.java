package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.RequisitoDto;
import com.afk.backend.model.entity.Requisito;
import com.afk.backend.model.entity.TipoRequisito;
import com.afk.backend.model.entity.Vacante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequisitoMapper {

    @Named("mapV")
    default Vacante mapV(Long id) {
        if (id == null) return null;
        Vacante vacante = new Vacante();
        vacante.setId(id);
        return vacante;
    }

    @Named("mapT")
    default TipoRequisito mapT(Long id) {
        if (id == null) return null;
        TipoRequisito tipo = new TipoRequisito();
        tipo.setId(id);
        return tipo;
    }

    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "vacante", source="idVacante", qualifiedByName = "mapV")
    @Mapping(target = "tipo", source="idTipo", qualifiedByName = "mapT")
    Requisito toEntity(RequisitoDto dto);

    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "vacante.id", target = "idVacante")
    @Mapping(source = "tipo.id", target = "idTipo")
    RequisitoDto toDto(Requisito requisito);

    List<RequisitoDto> toDtoList(List<Requisito> requisitos);
}
