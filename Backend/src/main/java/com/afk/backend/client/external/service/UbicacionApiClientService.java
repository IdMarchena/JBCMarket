package com.afk.backend.client.external.service;

import com.afk.backend.client.external.dto.UbicacionDTO;
import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.model.entity.enm.EstadoUbicacion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UbicacionApiClientService {

    private final WebClient googleMapsWebClient;
    private final WebClient ubicacionWebClient;

    public UbicacionApiClientService(
            @Qualifier("googleMapsWebClient") WebClient googleMapsWebClient,
            @Qualifier("ubicacionWebClient") WebClient ubicacionWebClient) {
        this.googleMapsWebClient = googleMapsWebClient;
        this.ubicacionWebClient = ubicacionWebClient;
    }

    public Mono<UbicacionDt> obtenerCoordenadas(String direccion) {
        return googleMapsWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geocode/json")
                        .queryParam("address", direccion)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parsearRespuestaGeocoding);
    }

    public Mono<UbicacionDt> obtenerUbicacionPorId(Long id) {
        return ubicacionWebClient.get()
                .uri("/ubicaciones/{id}", id)
                .retrieve()
                .bodyToMono(UbicacionDt.class);
    }



    private UbicacionDt parsearRespuestaGeocoding(String jsonResponse) {
        return new UbicacionDt(
                null,                         // id_ubicacion
                null,                         // id_padre
                "Ubicación desde API",        // nombre
                40.7128,                      // latitud
                -74.0060,                     // longitud
                EstadoUbicacion.ACTIVA        // estado
        );
    }
}