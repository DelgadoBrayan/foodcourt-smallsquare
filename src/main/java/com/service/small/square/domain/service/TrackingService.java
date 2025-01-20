package com.service.small.square.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.domain.model.Tracking;
import com.service.small.square.infrastucture.exception.InvalidOrderException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final WebClient trackingWebClient;


    public void postTrackingInfo(Long clientId, Long orderId, String initialState) {
        trackingWebClient.post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("clientId", clientId)
                .queryParam("orderId", orderId)
                .queryParam("initialState", initialState).build())
            .retrieve()
            .onStatus(status -> status == HttpStatus.BAD_REQUEST, 
                clientResponse -> Mono.error(new InvalidOrderException("Error guardar la trazabilidad de la orden")))
            .bodyToMono(Tracking.class).block();
    }

    public void patchTrackingInfo(Long orderId, String newState, String description) {
        trackingWebClient.patch()
            .uri(uriBuilder -> uriBuilder.path("/{orderId}")
                .queryParam("newState", newState)
                .queryParam("description", description)
                .build(orderId))
            .retrieve()
            .onStatus(status -> status == HttpStatus.BAD_REQUEST, 
                clientResponse -> Mono.error(new InvalidOrderException("Error al obtener la consulta")))
            .bodyToMono(Void.class)
            .block();
    }
}
