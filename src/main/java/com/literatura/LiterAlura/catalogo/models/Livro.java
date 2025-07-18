package com.literatura.LiterAlura.catalogo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Livro { // Classe Livro

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Atributo id
    private Long id; // Atributo id
    private String titulo; // Atributo titulo
    private String autor; // Atributo autor
    private String idioma; // Atributo idioma
    private Double numeroDownloads; // Atributo numeroDownloads

    // Construtores, getters e setters
    public Livro() {}

    public Livro(String titulo, String autor, String idioma, Double numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
}
