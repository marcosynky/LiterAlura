package com.literatura.LiterAlura.catalogo.controller;

import com.literatura.LiterAlura.catalogo.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.Scanner;

@Controller  // Define esta classe como um controlador do Spring
public class LivroController implements CommandLineRunner {

    @Autowired
    private LivroService livroService;  // Injeção de dependência para o serviço de livros

    // Método que será executado logo após a inicialização do aplicativo (pois implementa CommandLineRunner)
    @Override
    public void run(String... args) throws Exception {
        // Criação do Scanner para capturar as entradas do usuário no console
        Scanner scanner = new Scanner(System.in);

        // Loop infinito que mantém o menu ativo até que o usuário escolha sair
        while (true) {
            // Exibe as opções de menu para o usuário
            System.out.println("==== CATALOGO DE LIVROS ====");
            System.out.println("1 - Buscar livro pelo título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Lista nossos autores");
            System.out.println("4 - Listar autores em determinado ano");
            System.out.println("5 - Listar livros em determinado idioma");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            // Adiciona o tratamento de exceção para garantir que o usuário insira um número inteiro
            int opcao = -1;
            try {
                opcao = scanner.nextInt();  // Lê a opção escolhida pelo usuário
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, insira um número válido.");
                scanner.nextLine(); // Limpar o buffer de entrada
                continue;  // Retorna ao início do loop
            }

            // Processa a opção do usuário
            switch (opcao) {
                case 1:
                    // Leitura correta do título com espaços (nextLine() para títulos com múltiplas palavras)
                    scanner.nextLine(); // Limpar o buffer do scanner
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();  // Lê o título completo do livro
                    livroService.buscarLivrosPorTitulo(titulo);  // Chama o método para buscar livros pelo título
                    break;
                case 2:
                    livroService.listarLivros();  // Chama o método para listar livros registrados
                    break;
                case 3:
                    livroService.listarAutores();  // Chama o método para listar autores
                    break;
                case 4:
                    // Leitura correta do ano
                    System.out.print("Digite o ano: ");
                    int ano = scanner.nextInt();  // Lê o ano
                    livroService.listarAutoresPorAno(ano);  // Chama o método para listar autores por ano
                    break;
                case 5:
                    // Leitura correta do idioma
                    scanner.nextLine(); // Limpar o buffer do scanner
                    System.out.print("Digite o idioma: ");
                    String idioma = scanner.nextLine();  // Lê o idioma completo
                    livroService.listarLivrosPorIdioma(idioma);  // Chama o método para listar livros por idioma
                    break;
                case 6:
                    System.out.println("Saindo do programa...");  // Exibe mensagem e sai do menu
                    return;  // Encerra o programa
                default:
                    System.out.println("Opção inválida. Tente novamente.");  // Exibe uma mensagem para opções inválidas
            }
        }
    }
}

