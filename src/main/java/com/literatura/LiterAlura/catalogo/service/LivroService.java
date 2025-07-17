package com.literatura.LiterAlura.catalogo.service;

import com.literatura.LiterAlura.catalogo.models.Livro;
import com.literatura.LiterAlura.catalogo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository; // Injeção de dependência

    public Flux<Livro> buscarLivros() {
        // Aqui estamos retornando o Flux diretamente
        return Flux.fromIterable(livroRepository.findAll()); // Converte List para Flux
    }

    public Flux<Livro> buscarLivrosPorAutor(String autor) {
        // Buscar livros por autor e converter para Flux
        return Flux.fromIterable(livroRepository.findByAutor(autor)); // Converte List para Flux
    }
}
