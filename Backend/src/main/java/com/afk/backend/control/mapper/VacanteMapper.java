package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VacanteMapper {

    @Named("mapU")
    default Ubicacion mapU(Long id){
        if(id == null) return null;
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setId(id);
        return ubicacion;
    }
    @Named("mapE")
    default Empresa mapE(Long id){
        if(id == null) return null;
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
    @Named("mapR")
    default List<Requisito> mapR(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Requisito r = new Requisito();
            r.setId(id);
            return r;
        }).collect(Collectors.toList());
    }


    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "ubicacion", source = "idUbicacion", qualifiedByName = "mapU")
    @Mapping(target = "empresa", source = "idEmpresa", qualifiedByName = "mapE")
    @Mapping(target = "fechaVcante", source = "fechaVcante")
    @Mapping(target = "requisitos", source = "requisitos")
    Vacante toEntity(VacanteDto dto);

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "ubicacion.id", target = "idUbicacion")
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "fechaVcante", target = "fechaVcante")
    @Mapping(source = "requisitos",target="requisitos")
    VacanteDto toDto(Vacante vacante);

    default List<VacanteDto> toDtoList(List<Vacante> vacantes) {
        return vacantes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Vacante entity, VacanteDto dto);
}