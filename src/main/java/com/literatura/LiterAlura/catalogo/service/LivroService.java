package com.literatura.LiterAlura.catalogo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literatura.LiterAlura.catalogo.models.Livro;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class LivroService {

    private WebClient webClient;
    private final ObjectMapper objectMapper; // Utilizado para formatar o JSON

    // Construtor que inicializa o WebClient e o ObjectMapper
    public LivroService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder  // Alterado para 'this.webClient' para referenciar o campo da classe
                .baseUrl("https://gutendex.com")
                .defaultHeader("Accept", "application/json")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))  // Ativa o redirecionamento
                .filter((request, next) -> {
                    System.out.println("Request URI: " + request.url());
                    return next.exchange(request);
                })
                .build();

        this.objectMapper = objectMapper; // Inicializando o ObjectMapper
    }

    // Método para buscar livros pelo título
    public void buscarLivrosPorTitulo(String titulo) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/books")
                        .queryParam("search", titulo)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return Mono.error(new RuntimeException("Erro ao acessar a API: " + clientResponse.statusCode()));
                })
                .bodyToMono(JsonNode.class)  // Pegando a resposta como JsonNode para processar o JSON
                .doOnTerminate(() -> {
                    System.out.println("Busca terminada para o título: " + titulo);  // Mensagem ao finalizar a busca
                })
                .subscribe(response -> {
                    // Processa a resposta da API
                    try {
                        // Exibe a resposta formatada no console
                        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
                        System.out.println("Resposta da API: " + prettyJson);  // Exibe o JSON formatado no console
                    } catch (Exception e) {
                        System.err.println("Erro ao formatar a resposta JSON: " + e.getMessage());
                    }
                    processarResposta(response);  // Chama o método para processar e exibir os dados
                });
    }

    // Método para processar a resposta da API e exibir os livros
    private void processarResposta(JsonNode response) {
        JsonNode livrosNode = response.path("results");  // Supondo que o JSON retornado tenha uma chave "results"
        if (livrosNode.isArray() && livrosNode.size() > 0) {
            System.out.println("Livros encontrados: ");
            for (JsonNode livroNode : livrosNode) {
                String titulo = livroNode.path("title").asText();
                String autor = livroNode.path("author").asText();
                System.out.println("Título: " + titulo + " | Autor: " + autor);
            }
        } else {
            System.out.println("Nenhum livro encontrado para o título especificado.");
        }
    }

    // Método para listar todos os livros registrados
    public Flux<Livro> listarLivros() {
        return webClient.get()
                .uri("/books")  // Endpoint para listar livros
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Erro ao acessar a API: " + clientResponse.statusCode())))
                .bodyToFlux(Livro.class);  // Retorna Flux<Livro>
    }

    // Método para listar todos os autores
    public Flux<String> listarAutores() {
        return webClient.get()
                .uri("/authors")  // Endpoint para listar autores
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Erro ao acessar a API: " + clientResponse.statusCode())))
                .bodyToFlux(String.class);  // Retorna Flux de autores como Strings
    }

    // Método para listar autores de um determinado ano
    public Flux<String> listarAutoresPorAno(int ano) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/authors")
                        .queryParam("year", ano)
                        .build())  // Busca autores por ano
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Erro ao acessar a API: " + clientResponse.statusCode())))
                .bodyToFlux(String.class);  // Retorna Flux de autores como Strings
    }

    // Método para listar livros em um determinado idioma
    public Flux<Livro> listarLivrosPorIdioma(String idioma) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/books")
                        .queryParam("language", idioma)
                        .build())  // Busca livros por idioma
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Erro ao acessar a API: " + clientResponse.statusCode())))
                .bodyToFlux(Livro.class);  // Retorna Flux<Livro>
    }
}
