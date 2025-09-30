package com.afk.backend.model.entity;

import com.afk.backend.model.entity.enm.EstadoChat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "chats")
@Builder
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_a", nullable = false)
    private Usuario usuarioa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_b", nullable = false)
    private Usuario usuariob;


    @Column(name="mensaje_chat",nullable = false,length = 500)
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private EstadoChat estado_chat;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensaje> mensajes = new ArrayList<>();

}
