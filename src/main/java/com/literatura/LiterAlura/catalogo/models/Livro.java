package com.literatura.LiterAlura.catalogo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Classe que representa um livro no sistema.
 * Esta classe mapeia a tabela 'livro' no banco de dados.
 */
@Entity
public class Livro {

    // A anotação @Id define que o campo 'id' é a chave primária da tabela.
    // A anotação @GeneratedValue indica que o valor do 'id' será gerado automaticamente pelo banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributo que armazena o título do livro.
    private String titulo;

    // Atributo que armazena o nome do autor do livro.
    private String autor;

    // Atributo que armazena o nome da editora do livro.
    private String editora;

    // Atributo que armazena a URL da imagem do livro.
    private String urlImagem;

    // Atributo que armazena o ano de publicação do livro.
    private Integer anoPublicacao;

    // Getters e Setters são utilizados para acessar e modificar os valores dos atributos privados.

    public Long getId() {
        return id;  // Retorna o 'id' do livro.
    }

    public void setId(Long id) {
        this.id = id;  // Define o 'id' do livro.
    }

    public String getTitulo() {
        return titulo;  // Retorna o título do livro.
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;  // Define o título do livro.
    }

    public String getAutor() {
        return autor;  // Retorna o nome do autor do livro.
    }

    public void setAutor(String autor) {
        this.autor = autor;  // Define o autor do livro.
    }

    public String getEditora() {
        return editora;  // Retorna o nome da editora do livro.
    }

    public void setEditora(String editora) {
        this.editora = editora;  // Define a editora do livro.
    }

    public String getUrlImagem() {
        return urlImagem;  // Retorna a URL da imagem do livro.
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;  // Define a URL da imagem do livro.
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;  // Retorna o ano de publicação do livro.
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;  // Define o ano de publicação do livro.
    }


}
