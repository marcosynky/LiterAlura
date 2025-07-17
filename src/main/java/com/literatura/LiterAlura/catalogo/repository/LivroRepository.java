package com.literatura.LiterAlura.catalogo.repository;

import com.literatura.LiterAlura.catalogo.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    // Aqui você pode adicionar consultas personalizadas, se necessário
    List<Livro> findByAutor(String autor); // Exemplo de consulta personalizada
}
