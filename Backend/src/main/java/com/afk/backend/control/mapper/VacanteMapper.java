package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.entity.Ubicacion;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Vacante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VacanteMapper {

    default Ubicacion mapU(Long id){
        if(id == null) return null;
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setId(id);
        return ubicacion;
    }

    default Empresa mapE(Long id){
        if(id == null) return null;
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }

    @Mapping(target = "ubicacion", source = "idUbicacion", qualifiedByName = "mapU")
    @Mapping(target = "empresa", source = "idEmpresa", qualifiedByName = "mapE")
    @Mapping(target = "fecha_vacante", source = "fechaVcante")
    @Mapping(target = "desscripcion", source = "descripcion")
    Vacante toEntity(VacanteDto dto);

    @Mapping(target = "idUbicacion", source = "ubicacion.id")
    @Mapping(target = "idEmpresa", source = "empresa.id")
    @Mapping(target = "fechaVcante", source = "fecha_vacante")
    @Mapping(target = "descripcion", source = "desscripcion")
    VacanteDto toDto(Vacante vacante);

    default List<VacanteDto> toDtoList(List<Vacante> vacantes) {
        return vacantes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Named("mapU")
    default Ubicacion mapUNamed(Long id){
        return mapU(id);
    }

    @Named("mapE")
    default Empresa mapENamed(Long id){
        return mapE(id);
    }
}