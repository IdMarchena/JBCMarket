package com.afk.backend.control.mapper;


import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.model.entity.Ubicacion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UbicacionMapper {

    @Mapping(source = "id", target = "id_ubicacion")
    @Mapping(target = "id_padre", expression = "java(ubicacion.getPadre() != null ? ubicacion.getPadre().getId() : null)")
    UbicacionDt toDto(Ubicacion ubicacion);

    @Mapping(source = "id_ubicacion", target = "id")
    @Mapping(target = "padre", expression = "java(dto.id_padre() != null ? new Ubicacion(dto.id_padre(), null, null, null, null, null) : null)")
    Ubicacion toEntity(UbicacionDt dto);

    List<UbicacionDt> toDtos(List<Ubicacion> ubicaciones);

    List<Ubicacion> toEntities(List<UbicacionDt> ubicacionDtos);
}
