package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.ProyectoDto;
import com.afk.backend.control.mapper.ProyectoMapper;
import com.afk.backend.control.service.ImageStorageService;
import com.afk.backend.control.service.ProyectoService;
import com.afk.backend.model.entity.Perfil;
import com.afk.backend.model.entity.Proyecto;
import com.afk.backend.model.repository.PerfilRepository;
import com.afk.backend.model.repository.ProyectoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProyectoServiceImpl implements ProyectoService {
    private final ProyectoMapper proyectoMapper;
    private final ProyectoRepository proyectoRepository;
    private final PerfilRepository perfilRepository;
    private final String UPLOAD_DIR = "uploads/proyectos/";
    private final ImageStorageService imageStorageService;

    @Override
    public ProyectoDto createProyecto(ProyectoDto proyectoDto, MultipartFile archivo) {
        Proyecto proyectoE = proyectoMapper.toEntity(proyectoDto);

        if (archivo != null && !archivo.isEmpty()) {
            String url = imageStorageService.guardarImagenRedimensionada(archivo, UPLOAD_DIR, 250, 250);
            proyectoE.setUrlFoto(url);
        }

        Perfil perfil = perfilRepository.findById(proyectoDto.idPerfil())
                .orElseThrow(() -> new NoSuchElementException("Perfil con ID " + proyectoDto.idPerfil() + " no encontrado"));

        proyectoE.setPerfil(perfil);

        return proyectoMapper.toDto(proyectoRepository.save(proyectoE));
    }

    @Override
    public ProyectoDto findProyectoById(Long id) {
        return proyectoMapper.toDto(proyectoRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el proyecto con id: " + id)));
    }

    @Override
    public List<ProyectoDto> findAllProyectos() {
        return proyectoMapper.toListDto(proyectoRepository.findAll());
    }

    @Override
    public ProyectoDto updateProyecto(Long id, ProyectoDto proyectoDto, MultipartFile archivo) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado un proyecto con el id: " + id));

        proyecto.setDescripcion(proyectoDto.descripcion());

        if (archivo != null && !archivo.isEmpty()) {
            // Puedes eliminar la imagen anterior si lo deseas (opcional)
            String url = imageStorageService.guardarImagenRedimensionada(archivo, UPLOAD_DIR, 250, 250);
            proyecto.setUrlFoto(url);
        }

        return proyectoMapper.toDto(proyectoRepository.save(proyecto));
    }


    @Override
    public void deleteProyecto(Long id) {
        if(!proyectoRepository.existsById(id)){
            throw new RuntimeException("No existe el proyecto con el id: " + id);
        }
        proyectoRepository.deleteById(id);
    }

    @Override
    public Integer cuantityProyectoByIdPerfil(Long id){
        Perfil perfil= perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el perfil con el id: " + id));
        return perfil.getProyectos().size();
    }
    @Override
    public List<ProyectoDto> findAllProyectosByUsuario(Long id){
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil con ID " + id + " no encontrado"));

        List<Proyecto> proyectos = perfil.getProyectos();
        return proyectoMapper.toListDto(proyectos);
    }
}
