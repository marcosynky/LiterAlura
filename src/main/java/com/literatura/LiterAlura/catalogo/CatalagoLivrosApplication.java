package com.literatura.LiterAlura.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicializa a aplicação Spring Boot.
 * A anotação @SpringBootApplication indica que esta classe é o ponto de entrada para o Spring Boot.
 */
@SpringBootApplication
public class CatalagoLivrosApplication {

    /**
     * O método main é o ponto de entrada para a aplicação.
     * Ele invoca o método run() do SpringApplication para iniciar a aplicação Spring Boot.
     */
    public static void main(String[] args) {
        // Chama o método run() para inicializar a aplicação Spring Boot
        SpringApplication.run(CatalagoLivrosApplication.class, args);
    }
}
