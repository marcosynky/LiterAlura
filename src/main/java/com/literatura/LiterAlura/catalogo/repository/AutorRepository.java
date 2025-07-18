package com.literatura.LiterAlura.catalogo.repository;

import com.literatura.LiterAlura.catalogo.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Método para buscar autor por nome
    Optional<Autor> findByNome(String nome);

    // Método para buscar autores por ano de nascimento
    List<Autor> findByAnoNascimento(int ano);

    // Podemos adicionar outros métodos de consulta personalizados aqui, se necessário
}
