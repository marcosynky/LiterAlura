package com.literatura.LiterAlura;  // Pacote onde a classe está localizada

import org.springframework.boot.SpringApplication;  // Importa a classe SpringApplication para inicializar a aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Importa a anotação SpringBootApplication

@SpringBootApplication  // Anotação que marca esta classe como a classe principal da aplicação Spring Boot
public class LiterAluraApplication {

	public static void main(String[] args) {
		// Este método é o ponto de entrada da aplicação, onde o Spring Boot é iniciado
		SpringApplication.run(LiterAluraApplication.class, args);  // Inicializa a aplicação Spring Boot e executa o contexto
	}

}
