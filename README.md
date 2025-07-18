

<h1>LiterAlura - Serviço de Catálogo de Livros</h1>

<h2>📖 Sobre</h2>
<p>O <strong>LiterAlura</strong> é um serviço backend para um catálogo de livros, desenvolvido em <strong>Java</strong> com <strong>Spring Boot</strong>. Ele integra uma API externa de livros e fornece funcionalidades como busca, cadastro e exibição de livros e autores no banco de dados, utilizando <strong>Spring WebFlux</strong> e <strong>WebClient</strong>.</p>

<p>Este projeto foi desenvolvido por <strong>Marco Antônio</strong>, com o objetivo de criar uma API eficiente e escalável para gerenciar livros e autores.</p>

<h2>🚀 Tecnologias</h2>

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

<h2>📊 Estado do Projeto</h2>

![Progresso](https://img.shields.io/badge/Progresso-80%25-orange?style=for-the-badge&labelColor=000000&color=FF6600&logo=github)

<p>Atualmente, cerca de 80% das funcionalidades estão implementadas.</p>

<h2>Autor</h2>
<h3>Marco Antônio</h3>
<p>Desenvolvedor Full-Stack</p>

<p>
  <a href="https://github.com/marcosynky" target="_blank">
    <img src="https://img.shields.io/badge/GitHub-000000?style=for-the-badge&logo=github&logoColor=white" />
  </a>
  <a href="https://www.linkedin.com/in/marco-antônio-developer-fullstack" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" />
  </a>
</p>

<h2>📱 Funcionalidades</h2>

<ul>
  <li>Busca de livros por título com integração de API externa.</li>
  <li>Exibição de lista de livros e autores registrados no banco de dados.</li>
  <li>Cadastro de livros e autores no banco de dados.</li>
  <li>Listagem de livros por idioma.</li>
  <li>Listagem de autores por ano de nascimento.</li>
</ul>

<h2>🛠️ Como Rodar o Projeto</h2>

<h3>Pré-requisitos</h3>
<ol>
  <li><strong>Java 17</strong>: Verifique se o Java 17 está instalado corretamente no seu sistema.</li>
  <li><strong>Spring Boot</strong>: Este projeto utiliza o Spring Boot para rodar a API.</li>
  <li><strong>Banco de Dados</strong>: Configure um banco de dados PostgreSQL ou utilize o H2 em memória para testes.</li>
</ol>

<h3>Passos para rodar o projeto</h3>

<pre>
1. Clone o repositório para sua máquina local:
   git clone https://github.com/marcosynky/LiterAlura.git

2. Entre no diretório do projeto:
   cd LiterAlura

3. Execute o projeto com o comando:
   ./mvnw spring-boot:run

4. Acesse a API através do endereço: 
   http://localhost:8080
</pre>

<h2>📱 Funcionalidades da API</h2>
<ul>
  <li><strong>GET</strong> <code>/livros</code>: Lista todos os livros registrados no banco de dados.</li>
  <li><strong>GET</strong> <code>/livros/{titulo}</code>: Busca livros pelo título.</li>
  <li><strong>GET</strong> <code>/autores</code>: Lista todos os autores registrados no banco de dados.</li>
  <li><strong>GET</strong> <code>/autores/ano/{ano}</code>: Lista autores registrados em determinado ano de nascimento.</li>
  <li><strong>GET</strong> <code>/livros/idioma/{idioma}</code>: Lista livros por idioma.</li>
</ul>

<h2>📝 Testes</h2>

<p>Este projeto usa <strong>JUnit5</strong> e <strong>Mockito</strong> para realizar testes unitários. Para rodar os testes, execute:</p>

<pre>
./mvnw test
</pre>

