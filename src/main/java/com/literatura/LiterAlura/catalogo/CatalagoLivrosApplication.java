package com.literatura.LiterAlura.catalogo;

import io.github.cdimascio.dotenv.Dotenv;  // Importa a classe Dotenv para carregar as variáveis de ambiente de um arquivo .env
import org.springframework.boot.SpringApplication;  // Importa a classe SpringApplication para inicializar a aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Importa a anotação SpringBootApplication para marcar a classe como ponto de entrada

/**
 * Classe principal que inicializa a aplicação Spring Boot.
 * A anotação @SpringBootApplication indica que esta classe é o ponto de entrada para o Spring Boot.
 */
@SpringBootApplication  // Anotação que marca a classe como uma configuração do Spring Boot
public class CatalagoLivrosApplication {

    /**
     * O método main é o ponto de entrada para a aplicação.
     * Ele invoca o método run() do SpringApplication para iniciar a aplicação Spring Boot.
     */
    public static void main(String[] args) {
        // Chama o método run() para inicializar a aplicação Spring Boot
        SpringApplication.run(CatalagoLivrosApplication.class, args);
        // Inicia a aplicação Spring Boot com a classe CatalagoLivrosApplication como a configuração principal


    }
}
