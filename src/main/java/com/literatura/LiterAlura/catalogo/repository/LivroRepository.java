package com.literatura.LiterAlura.catalogo.repository;

import com.literatura.LiterAlura.catalogo.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
