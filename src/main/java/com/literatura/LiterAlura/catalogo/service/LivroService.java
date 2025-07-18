package com.literatura.LiterAlura.catalogo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literatura.LiterAlura.catalogo.models.Livro;
import com.literatura.LiterAlura.catalogo.models.Autor;
import com.literatura.LiterAlura.catalogo.repository.LivroRepository;
import com.literatura.LiterAlura.catalogo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;

@Service
public class LivroService {

    private WebClient webClient;  // WebClient para realizar requisições
    private final LivroRepository livroRepository;  // Repositório para salvar livros no banco
    private final AutorRepository autorRepository;  // Repositório para salvar autores no banco
    private final ObjectMapper objectMapper;  // ObjectMapper para manipulação de JSON

    @Autowired
    public LivroService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.webClient = webClientBuilder
                .baseUrl("https://gutendex.com")
                .defaultHeader("Accept", "application/json")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
                .filter((request, next) -> {
                    System.out.println("Request URI: " + request.url());
                    return next.exchange(request);
                })
                .build();

        this.objectMapper = objectMapper;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;  // Inicializa o repositório de autores
    }

    // Método para buscar livros pelo título e salvar no banco
    public void buscarLivrosPorTitulo(String titulo) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/books")
                        .queryParam("search", titulo)  // Adiciona o parâmetro de busca para o título
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Erro ao acessar a API: " + clientResponse.statusCode())))
                .bodyToMono(JsonNode.class)  // Converte a resposta para um JsonNode
                .doOnError(error -> System.err.println("Erro durante a requisição: " + error.getMessage())) // Adicionando tratamento de erro
                .subscribe(response -> {
                    processarRespostaEDbInsert(response);  // Processa a resposta e insere os livros no banco
                    exibirMenu();  // Exibe o menu novamente após a busca
                });
    }

    // Método para processar a resposta da API e salvar os livros e autores no banco
    public void processarRespostaEDbInsert(JsonNode response) {
        JsonNode livrosNode = response.path("results");

        if (response.path("count").asInt() > 0 && livrosNode.isArray() && livrosNode.size() > 0) {
            for (JsonNode livroNode : livrosNode) {
                String titulo = livroNode.path("title").asText();
                System.out.println("Processando livro: " + titulo);  // Logando o título do livro

                // Manipulação de múltiplos autores
                StringBuilder autores = new StringBuilder();
                for (JsonNode autorNode : livroNode.path("authors")) {
                    String autorNome = autorNode.path("name").asText();
                    Integer anoNascimento = autorNode.has("birth_year") ? autorNode.path("birth_year").asInt() : null;
                    Integer anoFalecimento = autorNode.has("death_year") ? autorNode.path("death_year").asInt() : null;

                    // Verifica se o autor já está no banco, caso contrário, cria um novo
                    Autor autor = autorRepository.findByNome(autorNome)
                            .orElseGet(() -> {
                                Autor novoAutor = new Autor();
                                novoAutor.setNome(autorNome);  // Atribuindo o nome
                                novoAutor.setAnoNascimento(anoNascimento);  // Atribuindo o ano de nascimento
                                novoAutor.setAnoFalecimento(anoFalecimento);  // Atribuindo o ano de falecimento
                                autorRepository.save(novoAutor);  // Salvando o autor no banco
                                return novoAutor;
                            });

                    // Apende o autor na string de autores
                    autores.append(autor.getNome());
                    if (anoNascimento != null) {
                        autores.append(" (").append(anoNascimento).append(" - ");
                        autores.append(anoFalecimento != null ? anoFalecimento : "presente").append(")");
                    }
                    autores.append(", ");
                }

                // Se não houver autores, insere como "Autor desconhecido"
                String autor = autores.length() > 0 ? autores.substring(0, autores.length() - 2) : "Autor desconhecido";

                // Obtendo idioma e downloads
                String idioma = livroNode.path("languages").get(0).asText();
                double downloads = livroNode.path("download_count").asDouble();

                // Criando objeto Livro
                Livro livro = new Livro(titulo, autor, idioma, downloads);
                livroRepository.save(livro);  // Salvando livro no banco

                // Logar a resposta de forma detalhada para ver o que está sendo retornado
                System.out.println("Livro encontrado: " + titulo + " | Autor: " + autor);
            }

            // Exibe o número de livros encontrados
            System.out.println(livrosNode.size() + " livro(s) encontrado(s).");
        } else {
            System.out.println("Nenhum livro encontrado para o título especificado.");
        }
    }




    // Método para exibir o menu para o usuário
    private void exibirMenu() {
        System.out.println("==== CATALOGO DE LIVROS ====");
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Buscar livro pelo título");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores em determinado ano");
        System.out.println("5 - Listar livros em determinado idioma");
        System.out.println("0 - Sair");
        System.out.print("Por favor, Escolha uma opção: ");
    }

    // Método para listar todos os livros registrados no banco de dados
    public void listarLivros() {
        List<Livro> livros = livroRepository.findAll();  // Usando o repositório JPA para pegar todos os livros
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("Livros registrados:");
            for (Livro livro : livros) {
                System.out.println("Título: " + livro.getTitulo());  // Exibindo o nome do livro
            }
        }
    }

    // Método para salvar o livro e exibir os dados formatados
    public void salvarEListarLivro(Livro livro) {
        // Salva o livro no banco de dados
        livroRepository.save(livro);
    }

    // Método para listar todos os autores registrados no banco de dados
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();  // Usando o repositório JPA para pegar todos os autores
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("Autores registrados:");
            for (Autor autor : autores) {
                System.out.println("Nome: " + autor.getNome());  // Exibindo o nome do autor
                System.out.println("Ano de nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido"));
                System.out.println("Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Presente"));
                System.out.println("---");
            }
        }
    }

    // Método para listar autores registrados de acordo com o ano de nascimento
    public void listarAutoresPorAno(int ano) {
        List<Autor> autores = autorRepository.findByAnoNascimento(ano);  // Usando o repositório para buscar autores por ano de nascimento
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado para o ano especificado.");
        } else {
            System.out.println("Autores registrados no ano " + ano + ":");
            for (Autor autor : autores) {
                System.out.println("Nome: " + autor.getNome());
                System.out.println("Ano de nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido"));
                System.out.println("Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Presente"));
                System.out.println("---");
            }
        }
    }

    // Método para listar livros registrados de acordo com o idioma
    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdioma(idioma);  // Usando o repositório para buscar livros por idioma
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma especificado.");
        } else {
            System.out.println("Livros registrados no idioma " + idioma + ":");
            for (Livro livro : livros) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor(es): " + livro.getAutor());
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Número de downloads: " + livro.getNumeroDownloads());
                System.out.println("---");
            }
        }
    }

}
