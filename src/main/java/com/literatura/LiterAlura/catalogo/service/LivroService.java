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
        this.webClient = webClientBuilder // Configura o WebClient
                .baseUrl("https://gutendex.com") // Define a URL base
                .defaultHeader("Accept", "application/json") // Define o cabeçalho Accept
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))// Configura o WebClient para seguir redirecionamentos
                .filter((request, next) -> { // Adiciona um filtro ao WebClient
                    System.out.println("Request URI: " + request.url());// Imprime a URI da requisição
                    return next.exchange(request);// Executa a requisição
                })
                .build();

        this.objectMapper = objectMapper; // Inicializa o ObjectMapper
        this.livroRepository = livroRepository; // Inicializa o repositório de livros
        this.autorRepository = autorRepository;  // Inicializa o repositório de autores
    }

    // Método para buscar livros pelo título e salvar no banco
    public void buscarLivrosPorTitulo(String titulo) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/books") // Define a rota para buscar livros
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
    public void processarRespostaEDbInsert(JsonNode response) { // Processa a resposta da API
        JsonNode livrosNode = response.path("results"); // Obtem o array de livros

        if (response.path("count").asInt() > 0 && livrosNode.isArray() && livrosNode.size() > 0) { // Verifica se houver livros
            System.out.println("==== LIVROS ENCONTRADOS ===="); // Imprime uma mensagem

            // Loop para processar os livros encontrados
            for (JsonNode livroNode : livrosNode) { // Loop para percorrer os livros
                String titulo = livroNode.path("title").asText(); // Obtem o título
                StringBuilder autores = new StringBuilder();//

                // Manipulação de múltiplos autores
                for (JsonNode autorNode : livroNode.path("authors")) { // Loop para percorrer os autores
                    String autorNome = autorNode.path("name").asText(); // Obtem o nome
                    Integer anoNascimento = autorNode.has("birth_year") ? autorNode.path("birth_year").asInt() : null; // Obtem o ano de nascimento
                    Integer anoFalecimento = autorNode.has("death_year") ? autorNode.path("death_year").asInt() : null; // Obtem o ano de falecimento

                   // Busca ou cria o autor
                    Autor autor = autorRepository.findByNome(autorNome) // Busca o autor pelo nome
                            .orElseGet(() -> {
                                Autor novoAutor = new Autor(); // Cria um novo autor
                                novoAutor.setNome(autorNome);  // Atribuindo o nome
                                novoAutor.setAnoNascimento(anoNascimento);  // Atribuindo o ano de nascimento
                                novoAutor.setAnoFalecimento(anoFalecimento);  // Atribuindo o ano de falecimento
                                autorRepository.save(novoAutor);  // Salvando o autor no banco
                                return novoAutor;
                            });

                    // Apende o autor na string de autores
                    autores.append(autor.getNome()); // Atribuindo o nome
                    if (anoNascimento != null) {
                        autores.append(" (").append(anoNascimento).append(" - ");// Atribuindo o ano de nascimento
                        autores.append(anoFalecimento != null ? anoFalecimento : "presente").append(")"); // Atribuindo o ano de falecimento
                    }
                    autores.append(", ");// Atribuindo a virgula
                }

                // Se não houver autores, insere como "Autor desconhecido"
                String autor = autores.length() > 0 ? autores.substring(0, autores.length() - 2) : "Autor desconhecido";

                // Obtendo idioma e downloads
                String idioma = livroNode.path("languages").get(0).asText();
                double downloads = livroNode.path("download_count").asDouble();

                // Exibindo os detalhes do livro de forma mais limpa
                System.out.println("----- LIVRO -----"); // Imprime uma mensagem
                System.out.println("Título: " + titulo); // Imprime o título
                System.out.println("Autor: " + autor); // Imprime o autor
                System.out.println("Idioma: " + idioma); // Imprime o idioma
                System.out.println("Número de downloads: " + downloads); // Imprime o número de downloads
                System.out.println("------------------");
            }

            // Exibe o número de livros encontrados de forma mais clara
            System.out.println(livrosNode.size() + " livro(s) encontrado(s)."); // Imprime o número de livros
        } else {
            System.out.println("Nenhum livro encontrado para o título especificado."); // Imprime uma mensagem
        }
    }



    // Método para exibir o menu para o usuário
    private void exibirMenu() {
        System.out.println("==== CATALOGO DE LIVROS ===="); // Imprime uma mensagem
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Buscar livro pelo título"); // Imprime uma mensagem
        System.out.println("2 - Listar livros registrados"); // Imprime uma mensagem
        System.out.println("3 - Listar autores registrados"); // Imprime uma mensagem
        System.out.println("4 - Listar autores em determinado ano"); // Imprime uma mensagem
        System.out.println("5 - Listar livros em determinado idioma"); //
        System.out.println("0 - Sair"); // Imprime uma mensagem
        System.out.print("Por favor, Escolha uma opção: "); // Imprime uma mensagem
    }

    // Método para listar todos os livros registrados no banco de dados
    public void listarLivros() {
        List<Livro> livros = livroRepository.findAll();  // Usando o repositório JPA para pegar todos os livros
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado."); // Imprime uma mensagem
        } else {
            for (Livro livro : livros) {
                System.out.println("----- LIVRO -----"); // Exibindo uma mensagem
                System.out.println("Título: " + livro.getTitulo());  // Exibindo o nome do livro
                System.out.println("Autor: " + livro.getAutor());  // Exibindo o autor do livro
                System.out.println("Idioma: " + livro.getIdioma());  // Exibindo o idioma do livro
                System.out.println("Número de downloads: " + livro.getNumeroDownloads());  // Exibindo o número de downloads
                System.out.println("------------------");
            }
        }
    }

    // Método para salvar o livro e exibir os dados formatados
    public void salvarEListarLivro(Livro livro) {
        // Salva o livro no banco de dados
        livroRepository.save(livro); // Usando o repositório JPA para salvar o livro
    }

    // Método para listar todos os autores registrados no banco de dados
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();  // Usando o repositório JPA para pegar todos os autores
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado."); // Imprime uma mensagem
        } else {
            System.out.println("Autores registrados:"); // Exibindo uma mensagem
            for (Autor autor : autores) {
                System.out.println("Nome: " + autor.getNome());  // Exibindo o nome do autor
                System.out.println("Ano de nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido")); // Exibindo o ano de nascimento
                System.out.println("Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Presente")); // Exibindo o ano de falecimento
                System.out.println("---");
            }
        }
    }

    // Método para listar autores registrados de acordo com o ano de nascimento
    public void listarAutoresPorAno(int ano) {
        List<Autor> autores = autorRepository.findByAnoNascimento(ano);  // Usando o repositório para buscar autores por ano de nascimento
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado para o ano especificado."); // Imprime uma mensagem
        } else {
            System.out.println("Autores registrados no ano " + ano + ":"); // Exibindo uma mensagem
            for (Autor autor : autores) {
                System.out.println("Nome: " + autor.getNome()); // Exibindo o nome do autor
                System.out.println("Ano de nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido")); // Exibindo o ano de nascimento
                System.out.println("Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Presente")); // Exibindo o ano de falecimento
                System.out.println("---");
            }
        }
    }

    // Método para listar livros registrados de acordo com o idioma
    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdioma(idioma);  // Usando o repositório para buscar livros por idioma
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma especificado."); // Imprime uma mensagem
        } else {
            System.out.println("Livros registrados no idioma " + idioma + ":"); // Exibindo uma mensagem
            for (Livro livro : livros) {
                System.out.println("Título: " + livro.getTitulo()); // Exibindo o título do livro
                System.out.println("Autor(es): " + livro.getAutor()); // Exibindo os autores
                System.out.println("Idioma: " + livro.getIdioma());// Exibindo o idioma
                System.out.println("Número de downloads: " + livro.getNumeroDownloads()); // Exibindo o número de downloads
                System.out.println("---");// Exibindo uma linha
            }
        }
    }

}
