package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.PerfilDto;
import com.afk.backend.control.mapper.PerfilMapper;
import com.afk.backend.control.service.ImageStorageService;
import com.afk.backend.control.service.PerfilService;
import com.afk.backend.model.entity.Perfil;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.repository.PerfilRepository;
import com.afk.backend.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PerfilServiceImpl implements PerfilService {
    private final PerfilMapper perfilMapper;
    private final PerfilRepository perfilRepository;
    private final String UPLOAD_DIR = "uploads/perfiles/";
    private final UsuarioRepository usuarioRepository;
    private final ImageStorageService imageStorageService;

    @Override
    public PerfilDto createPerfil(PerfilDto dto, MultipartFile archivo) {
        Perfil perfil = perfilMapper.toEntity(dto);

        if (archivo != null && !archivo.isEmpty()) {
            String url = imageStorageService.guardarImagenRedimensionada(archivo, UPLOAD_DIR, 250, 250);
            perfil.setUrlForto(url);
        }

        Perfil savedPerfil = perfilRepository.save(perfil);
        return perfilMapper.toDto(savedPerfil);
    }

    @Override
    public PerfilDto findPerfilById(Long id) {
        return perfilMapper.toDto(perfilRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el perfil con Id: "+id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PerfilDto> findAllPerfil() {
        return perfilMapper.toListDto(perfilRepository.findAll());
    }

    @Override
    public PerfilDto updatePerfil(Long id, PerfilDto perfil,MultipartFile archivo) {
        Perfil perfilE = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado un proyecto con el id: " + id));

        perfilE.setDescripcion(perfil.descripcion());

        if (archivo != null && !archivo.isEmpty()) {
            // Puedes eliminar la imagen anterior si lo deseas (opcional)
            String url = imageStorageService.guardarImagenRedimensionada(archivo, UPLOAD_DIR, 250, 250);
            perfilE.setUrlForto(url);
        }

        return perfilMapper.toDto(perfilRepository.save(perfilE));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PerfilDto> searchPerfil(String filtro, Pageable pageable){
        Page<Perfil> perfils= perfilRepository.findByPerfilName(filtro, pageable);
        return perfils.map(perfilMapper::toDto);
    }

    @Override
    public void deletePerfil(Long id) {
        if(!perfilRepository.existsById(id)){
            throw new RuntimeException("No se encontro el perfil con Id: "+id);
        }
        perfilRepository.deleteById(id);
    }
    @Override
    @Transactional
    public PerfilDto agregarImagen(Long idUsuario, MultipartFile archivo) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            throw new RuntimeException("El usuario no tiene un perfil asignado");
        }

        try {
            // Guardar archivo en carpeta local
            Path path = Paths.get(UPLOAD_DIR + archivo.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, archivo.getBytes());

            // Actualizar URL en perfil
            perfil.setUrlForto(path.toString());
            perfilRepository.save(perfil);

            return perfilMapper.toDto(perfil);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    @Override
    @Transactional
    public void eliminarImagen(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Perfil perfil = usuario.getPerfil();
        if (perfil == null || perfil.getUrlForto() == null) {
            throw new RuntimeException("El usuario no tiene imagen de perfil");
        }

        try {
            Path path = Paths.get(perfil.getUrlForto());
            Files.deleteIfExists(path); // borrar físicamente el archivo
            perfil.setUrlForto(null);   // borrar referencia en BD
            perfilRepository.save(perfil);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen", e);
        }
    }
    @Override
    public Resource obtenerImagen(Long idUsuario) {
        Perfil perfil = perfilRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        try {
            Path ruta = Paths.get(perfil.getUrlForto()); // esa ruta que ya guardas
            return new UrlResource(ruta.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("No se pudo cargar la imagen", e);
        }
    }

}
