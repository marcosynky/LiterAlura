package com.literatura.LiterAlura.catalogo.controller;


import com.literatura.LiterAlura.catalogo.models.Livro;
import com.literatura.LiterAlura.catalogo.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class LivroController implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    @Override
    public void run(String... args) throws Exception {
      Scanner scanner = new Scanner(System.in);
      while (true) {
          System.out.println("==== CATALOGO DE LIVROS ====");
          System.out.println("1 - Listar livros");
          System.out.println("2 - Buscar Livro por autor");
          System.out.println("3 - Sair");
          System.out.print("Escolha uma opção: ");
          int opcao = scanner.nextInt();

          switch (opcao) {
              case 1:
                  listarLivros();
                  break;
              case 2:
                  System.out.print("Digite o autor: ");
                  String autor = scanner.next();
                  buscarLivroPorAutor(autor);
                  break;
              case 3:
                  System.out.println("Saindo do programa...");
                  return;
                  default:
                  System.out.println("Opção inválida. Tente novamente.");

          }
      }

    }
    private void listarLivros() {
        livroService.buscarLivros().subscribe(livros ->{
            for (Object livro : livros) {
                System.out.println(livro.getClass() + " - " + livro.getClass());
            }
        });
    }

    private void buscarLivroPorAutor(String autor) {

    }
}
