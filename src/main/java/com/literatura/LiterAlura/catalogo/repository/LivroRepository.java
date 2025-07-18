package com.literatura.LiterAlura.catalogo.repository;

import com.literatura.LiterAlura.catalogo.models.Livro;  // Importa a classe Livro para interagir com a entidade
import org.springframework.data.jpa.repository.JpaRepository;  // Importa JpaRepository, a interface do Spring Data JPA

import java.util.List;  // Importa List para retornar uma lista de objetos

// A interface LivroRepository estende JpaRepository, que fornece métodos prontos para manipulação de dados no banco
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Consulta livros pelo nome do autor
    List<Livro> findByAutor_Nome(String nomeAutor);
}
