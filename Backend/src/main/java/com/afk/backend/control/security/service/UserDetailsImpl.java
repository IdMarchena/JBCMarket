package com.afk.backend.control.security.service;

import com.afk.backend.model.entity.Rol;
import com.afk.backend.model.entity.UsuarioRegistrado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails, OAuth2User {
    private final Long id;
    private final String username;

    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    public static UserDetailsImpl build(UsuarioRegistrado user,
                                        List<Rol> roles) {
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getCorreo(),
                user.getContrasenia(),
                authorities,
                null // Atributos null para autenticación normal
        );
    }

    // Método para autenticación OAuth2
    public static UserDetailsImpl build(UsuarioRegistrado user,
                                        Collection<? extends GrantedAuthority> authorities,
                                        Map<String, Object> attributes) {
        return new UserDetailsImpl(
                user.getId(),
                user.getCorreo(),
                user.getContrasenia(),
                authorities,
                attributes
        );
    }

    // OAuth2User methods
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.username;
    }

    // UserDetails methods
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
