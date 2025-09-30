package com.afk.backend.model.entity;
import com.afk.backend.model.entity.enm.EstadoChat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "mensajes")
@Builder
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Usuario sender;

    @Column(nullable = false, length = 500)
    private String contenido;

    @Enumerated(EnumType.STRING)
    private EstadoChat estado;

    private LocalDateTime fecha;
}

