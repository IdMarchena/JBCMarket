package com.afk.backend.control.service.impl;
import com.afk.backend.client.external.dto.ChatRequest;
import com.afk.backend.control.dto.FavoritoDto;
import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.mapper.FavoritoMapper;
import com.afk.backend.control.service.ChatService;
import com.afk.backend.control.service.FavoritoService;
import com.afk.backend.model.entity.Favorito;
import com.afk.backend.model.entity.Publicacion;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Vacante;
import com.afk.backend.model.entity.enm.EstadoChat;
import com.afk.backend.model.repository.FavoritoRepository;
import com.afk.backend.model.repository.PublicacionRepository;
import com.afk.backend.model.repository.UsuarioRepository;
import com.afk.backend.model.repository.VacanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class FavoritoServiceImpl implements FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final PublicacionRepository publicacionRepository;
    private final VacanteRepository vacanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ChatService chatService;
    private final FavoritoMapper favoritoMapper;

    @Override
    @Transactional
    public FavoritoDto createFavorito(FavoritoDto dto) {
        Favorito favorito = favoritoMapper.toEntity(dto);
        Favorito savedFavorito = favoritoRepository.save(favorito);


        checkForMutualMatch(dto.idUsuario(), dto.idPublicacion());

        return favoritoMapper.toDto(savedFavorito);
    }

    private void checkForMutualMatch(Long idUsuario, Long idPublicacion) {
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Vacante vacante = vacanteRepository.findById(publicacion.getVacante().getId())
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        Usuario usuarioEmpresa = usuarioRepository.findById(vacante.getEmpresa().getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario empresa no encontrado"));

        boolean isMatch = favoritoRepository.existsMutualMatch(
                usuarioEmpresa.getId(),
                idPublicacion
        );

        if (isMatch) {
            boolean yaExisteChat = !chatService
                    .getChatsBetweenUsers(idUsuario, usuarioEmpresa.getId())
                    .isEmpty();

            if (!yaExisteChat) {
                ChatRequest chatRequest = new ChatRequest(
                        idUsuario,
                        usuarioEmpresa.getId(),
                        "¡Match realizado! Pueden comenzar a conversar.",
                        EstadoChat.ACTIVO
                );

                chatService.createChat(chatRequest);
                notifyMatch(idUsuario, usuarioEmpresa.getId());
            }
        }
    }

    private void notifyMatch(Long idUsuario1, Long idUsuario2) {
        System.out.println("Notificando a usuarios " + idUsuario1 + " y " + idUsuario2 + " sobre su match");
    }

    @Override
    public List<FavoritoDto> findMutualMatches(Long idUsuario) {

        List<Favorito> favoritosUsuario = favoritoRepository.findByUsuarioId(idUsuario);

        return favoritosUsuario.stream()
                .filter(favorito -> {

                    Long idPublicacion = favorito.getPublicacion().getId();
                    return favoritoRepository.existsMutualMatch(
                            favorito.getPublicacion().getVacante().getEmpresa().getUsuario().getId(),
                            idPublicacion
                    );
                })
                .map(favoritoMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public FavoritoDto findFavoritoById(Long id) {
        Favorito favorito = favoritoRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Favorito con ID " + id + " no encontrado"));
        return favoritoMapper.toDto(favorito);
    }

    @Override
    public List<FavoritoDto> findAllFavoritos() {
        List<Favorito> favoritos = favoritoRepository.findAll();
        return favoritos.stream().map(favoritoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteFavoritoById(Long id) {
        if (!favoritoRepository.existsById(id)) {
            throw new NoSuchElementException("Favorito con ID " + id + " no encontrado");
        }
        favoritoRepository.deleteById(id);
    }

    @Override
    public List<FavoritoDto> findFavoritosByUsuario(Long idUsuario) {
        List<Favorito> favoritos = favoritoRepository.findByUsuarioId(idUsuario);
        return favoritos.stream().map(favoritoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> findUsuariosFavoritosDeGerente(Long idGerente) {
        return favoritoRepository.findUsuariosFavoritosByGerenteId(idGerente);
    }
}
