package com.literatura.LiterAlura.catalogo.repository;

import com.literatura.LiterAlura.catalogo.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> { // Interface de repositório JPA
    List<Livro> findByIdioma(String idioma);  // Consulta livros por idioma
}
