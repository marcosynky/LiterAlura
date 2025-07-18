package com.literatura.LiterAlura.catalogo;

import org.springframework.context.annotation.Bean;  // Importa a anotação Bean para registrar métodos como beans do Spring
import org.springframework.context.annotation.Configuration;  // Importa a anotação Configuration para marcar a classe como configuração
import org.springframework.web.reactive.function.client.WebClient;  // Importa o WebClient, utilizado para realizar requisições reativas

@Configuration  // A anotação @Configuration indica que esta classe é uma classe de configuração do Spring
public class WebClientConfig {

    // Método que define e configura um bean do WebClient.Builder
    @Bean  // A anotação @Bean marca este método para que o Spring o registre como um bean gerenciado
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();  // Retorna um WebClient.Builder, que é utilizado para criar instâncias de WebClient
    }
}
