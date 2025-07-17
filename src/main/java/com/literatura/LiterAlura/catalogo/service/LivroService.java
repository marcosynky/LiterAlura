package com.literatura.LiterAlura.catalogo.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LivroService {

    private final WebClient webClient;

    public LivroService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://gutendex.com/books/").build();
    }

    public Mono<List> buscarLivros() {
        return webClient.get()
                .uri("?language=pt&limit=10")
                .retrieve()
                .bodyToMono(List.class);
    }
}