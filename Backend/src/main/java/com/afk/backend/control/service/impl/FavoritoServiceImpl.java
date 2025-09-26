    package com.afk.backend.control.service.impl;

    import com.afk.backend.client.external.dto.ChatRequest;
    import com.afk.backend.control.dto.FavoritoDto;
    import com.afk.backend.control.dto.UsuarioDto;
    import com.afk.backend.control.mapper.FavoritoMapper;
    import com.afk.backend.control.mapper.UsuarioMapper;
    import com.afk.backend.control.service.ChatService;
    import com.afk.backend.control.service.FavoritoService;
    import com.afk.backend.model.entity.*;
    import com.afk.backend.model.entity.enm.EstadoChat;
    import com.afk.backend.model.repository.*;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDateTime;
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
        private final UsuarioMapper usuarioMapper;
        private final PerfilRepository perfilRepository;

        @Override
        @Transactional
        public FavoritoDto createFavorito(FavoritoDto dto) {
            Long id;
            Integer bandera;

            if ((dto.idPublicacion() == null && dto.idPerfil() == null) ||
                    (dto.idPublicacion() != null && dto.idPerfil() != null)) {
                throw new IllegalArgumentException("Debes especificar solo una publicación o un perfil como favorito");
            }

            Favorito favorito = favoritoMapper.toEntity(dto);
            Favorito savedFavorito = favoritoRepository.save(favorito);

            if (dto.idPublicacion() != null) {
                id = dto.idPublicacion();
                bandera = 0;
            } else {
                id = dto.idPerfil();
                bandera = 1;
            }
            checkForMutualMatch(dto.idUsuario(), id, bandera);

            return favoritoMapper.toDto(savedFavorito);
        }

        private void isMatch(Long id1, Long id2, Integer bandera) {
            boolean isMatch = true;
            if (bandera != null) {
                if (bandera == 1) { // Favorito es un perfil
                    isMatch = favoritoRepository.existsMutualMatchProfile(id1, null, id2);
                } else { // Favorito es una publicación
                    isMatch = favoritoRepository.existsMutualMatchProfile(id1, id2, null);
                }
            }

            if (isMatch) {
                boolean yaExisteChat = !chatService.getChatsBetweenUsers(id1, id2).isEmpty();

                if (!yaExisteChat) {
                    ChatRequest chatRequest = new ChatRequest(
                            id1, id2,
                            "¡Match realizado! Pueden comenzar a conversar.",
                            EstadoChat.ACTIVO
                    );
                    chatService.createChat(chatRequest);
                    notifyMatch(id1, id2);
                }
            }
        }

        private void checkForMutualMatch(Long idUsuario, Long idPublicacion, Integer bandera) {
            if (bandera == 1) { // El favorito es un perfil
                try {
                    Perfil perfil = perfilRepository.findById(idPublicacion)
                            .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
                    Usuario usuario = usuarioRepository.findById(perfil.getUsuario().getId())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                    isMatch(usuario.getId(), idUsuario, bandera);
                } catch (NoSuchElementException e) {
                    throw new RuntimeException(e);
                }
            } else { // El favorito es una publicación
                Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                        .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

                Vacante vacante = vacanteRepository.findById(publicacion.getVacante().getId())
                        .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

                Usuario usuarioEmpresa = usuarioRepository.findById(vacante.getEmpresa().getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario empresa no encontrado"));
                isMatch(usuarioEmpresa.getId(), idUsuario, bandera);
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
                        return favoritoRepository.existsMutualMatchProfile(
                                favorito.getPublicacion().getVacante().getEmpresa().getUsuario().getId(),
                                idPublicacion, null
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
            List<Usuario> usuarios = favoritoRepository.findUsuariosFavoritosByUsuario(idGerente);
            return usuarioMapper.toListDto(usuarios);
        }

        @Override
        public Integer obtenerCantidadMatchsByUsuario(Long idUsuario) {
            List<FavoritoDto> favoritos = findMutualMatches(idUsuario);
            return favoritos.size();
        }

        @Override
        public Page<FavoritoDto> searchFavoritosByFecha(LocalDateTime fecha, Pageable pageable) {
            Page<Favorito> favorito = favoritoRepository.findByFechaContaining(fecha, pageable);
            return favorito.map(favoritoMapper::toDto);
        }
    }
