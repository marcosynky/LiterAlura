# 📚 LiterAlura - Serviço de Catálogo de Livros

## 📖 Sobre

O **LiterAlura** é um serviço backend para um catálogo de livros, desenvolvido em **Java** com **Spring Boot**. Ele integra uma API externa de livros e fornece funcionalidades como busca, cadastro e exibição de livros e autores no banco de dados, utilizando **Spring WebFlux** e **WebClient**.

Este projeto foi desenvolvido por **Marco Antônio**, com o objetivo de criar uma API eficiente e escalável para gerenciar livros e autores.

## 🚀 Tecnologias

<div>
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Boot-3.0.6-green?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black">
  <img src="https://img.shields.io/badge/H2-2.1.214-blue?style=for-the-badge&logo=h2&logoColor=white">
  <img src="https://img.shields.io/badge/PostgreSQL-42.5.6-blue?style=for-the-badge&logo=postgresql&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit5-5.9.2-green?style=for-the-badge&logo=junit&logoColor=white">
  <img src="https://img.shields.io/badge/Mockito-4.8.1-blue?style=for-the-badge&logo=mockito&logoColor=white">
</div>

<p>Este projeto utiliza as seguintes tecnologias:</p>
<ul>
  <li><strong>Java</strong>: Linguagem de programação utilizada para o backend.</li>
  <li><strong>Spring Boot</strong>: Framework Java utilizado para desenvolver a API.</li>
  <li><strong>Spring WebFlux</strong>: Para chamadas assíncronas e reativas à API externa.</li>
  <li><strong>WebClient</strong>: Para interagir com APIs externas de forma reativa.</li>
  <li><strong>PostgreSQL</strong>: Banco de dados relacional utilizado para persistência de dados em produção.</li>
  <li><strong>H2</strong>: Banco de dados em memória utilizado para testes e desenvolvimento.</li>
  <li><strong>JUnit5</strong>: Framework de testes utilizado para testes unitários e de integração.</li>
  <li><strong>Mockito</strong>: Framework de mock utilizado para simular comportamentos em testes.</li>
</ul>

## 📊 Estado do Projeto

![Progresso](https://img.shields.io/badge/Progresso-80%25-orange?style=for-the-badge&labelColor=000000&color=FF6600&logo=github)

> Atualmente, cerca de 80% das funcionalidades estão implementadas.

## 🛠️ Funcionalidades

- **Buscar livros por título**: Conecte-se a uma API externa para buscar livros.
- **Exibir lista de livros e autores registrados**: Listar livros e autores no banco de dados.
- **Cadastro de livros e autores**: Salvar livros e autores no banco de dados.
- **Listagem de livros por idioma**: Filtrar livros pelo idioma.
- **Listagem de autores por ano**: Filtrar autores pelo ano de nascimento.

## 🔧 Como Rodar o Projeto

### Pré-requisitos

1. **Java 17**: Verifique se o Java 17 está instalado corretamente no seu sistema.
   - Instalação do Java: [Java Docs](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. **Spring Boot**: Este projeto utiliza o Spring Boot para rodar a API.
   - Instalação do Spring Boot: [Spring Boot Docs](https://spring.io/projects/spring-boot)

3. **Banco de Dados**: Configure um banco de dados PostgreSQL ou utilize o H2 em memória para testes.

### Passos para rodar o projeto

1. Clone o repositório para sua máquina local:

```bash
git clone https://github.com/marcosynky/LiterAlura.git
