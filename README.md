<h1 align="center">Estação Ribeirinha API</h1>

<p align="center">
  API REST desenvolvida em Java para o monitoramento de estações voltadas principalmente a comunidades ribeirinhas da Amazônia.
</p>

<p align="center">
  <a href="#ficha-da-citizentech">Ficha da CitizenTech</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#projeto">Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#integrantes">Integrantes</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#funcionalidades">Funcionalidades</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#arquitetura">Arquitetura</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#endpoints">Endpoints</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#como-executar">Como Executar</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#documentação">Documentação</a>
</p>

<p align="center">
  <a href="https://estacao-ribeirinha-api.onrender.com/">API publicada</a>
  &nbsp;&nbsp;|&nbsp;&nbsp;
  <a href="https://estacao-ribeirinha-front.onrender.com/">Frontend publicado</a>
  &nbsp;&nbsp;|&nbsp;&nbsp;
  <a href="https://github.com/Augusto-Valerio/estacao-ribeirinha-front">Repositório do frontend</a>
</p>

---

## Ficha da CitizenTech

| Campo | Definição |
|---|---|
| **Nome da CitizenTech** | Estação Ribeirinha |
| **Cidade e bairro / território** | Comunidades ribeirinhas da Amazônia, com estrutura preparada para cadastrar estações de diferentes municípios e estados brasileiros. |
| **Cidadãos beneficiados** | Moradores de comunidades ribeirinhas amazônicas e agentes comunitários responsáveis pelo acompanhamento das condições locais. |
| **Problema** | Dificuldade de concentrar e acompanhar informações locais sobre o nível da água, a energia disponível na bateria da estação e os avisos comunitários. |
| **Como funciona** | Cada estação registra o nível da água e o percentual de carga da bateria que mantém o ponto de monitoramento em funcionamento. A API armazena os dados no Oracle, calcula a tendência do nível e disponibiliza as informações para o frontend. |
| **O que cada parte faz** | O backend Java aplica as regras e disponibiliza a API; o Oracle armazena os dados; o frontend permite consultar e gerenciar estações, leituras e avisos. |
| **Indicadores de impacto** | Quantidade de estações acompanhadas, leituras registradas, avisos comunitários cadastrados e continuidade do histórico de monitoramento. |
| **ODS relacionados** | ODS 7 - Energia Acessível e Limpa; ODS 10 - Redução das Desigualdades; ODS 11 - Cidades e Comunidades Sustentáveis; ODS 13 - Ação Contra a Mudança Global do Clima. |
| **Repositório** | [github.com/Augusto-Valerio/estacao-ribeirinha-api](https://github.com/Augusto-Valerio/estacao-ribeirinha-api) |

> Os dados utilizados são demonstrativos. As tendências e os avisos apresentados pela aplicação não substituem comunicados da Defesa Civil nem representam previsões oficiais de enchentes.

---

## Projeto

A **Estação Ribeirinha** é uma solução CitizenTech concebida principalmente para apoiar comunidades ribeirinhas da Amazônia no acompanhamento de informações locais relacionadas ao nível da água e ao funcionamento dos pontos de monitoramento.

A aplicação permite cadastrar estações de diferentes municípios, registrar medições do nível da água e acompanhar o percentual de carga da bateria responsável por fornecer energia ao ponto de monitoramento. Essa bateria representa a energia disponível para manter sensores e outros componentes essenciais da estação em funcionamento.

A partir das leituras registradas, o sistema calcula automaticamente se o nível da água está `SUBINDO`, `DESCENDO` ou `ESTAVEL`. Também é possível cadastrar avisos locais vinculados a cada comunidade.

Embora tenha como foco principal o contexto amazônico, a estrutura do sistema permite cadastrar estações de diferentes municípios e estados brasileiros, sem limitar a aplicação a uma única cidade ou comunidade.

Este repositório contém a API REST do projeto, desenvolvida em **Java 21 com Spring Boot**, utilizando o banco de dados **Oracle da FIAP**.

Os dados utilizados são demonstrativos e não representam medições ou alertas oficiais.

---

## Integrantes

## Integrantes

| Nome | RM |
|---|---|
| [Augusto Valerio](https://github.com/Augusto-Valerio) | 562185 |
| [Pedro Marchese](https://github.com/PedroMarchese01) | 563339 |
| [Mariana Oliveira](https://github.com/Marirsil) | 564241 |
| [Jonas Esteves](https://github.com/JonasEstevess) | 564143 |
| [Vitor Tigre](https://github.com/VitorTigre) | 561746 |

---

## Funcionalidades

<details>
<summary>Ver funcionalidades implementadas</summary>

- Cadastro, consulta, atualização e exclusão de estações.
- Cadastro, consulta, atualização e exclusão de leituras.
- Cadastro, consulta, atualização e exclusão de avisos.
- Consulta de leituras por estação.
- Consulta da última leitura registrada em uma estação.
- Consulta de avisos vinculados a uma estação.
- Registro automático da data e hora quando não forem informadas.
- Cálculo automático da tendência do nível da água.
- Validação do nível da água e do percentual da bateria.
- Validação da existência da estação antes do cadastro de leituras e avisos.
- Impedimento da exclusão de estações que ainda possuam dados vinculados.
- Padronização das respostas de erro da API.
- Integração com o frontend publicado no Render.

</details>

---

## Arquitetura

A aplicação segue o padrão de camadas utilizado nas aulas:

```text
Frontend
   ↓
Controller
   ↓
BO
   ↓
DAO
   ↓
Oracle Database
```

| Camada | Responsabilidade |
|---|---|
| `entities` | Representa as entidades Estacao, Leitura e Aviso. |
| `controller` | Recebe as requisições HTTP e disponibiliza os endpoints REST. |
| `bo` | Aplica validações, normalizações e regras de negócio. |
| `dao` | Executa os comandos SQL e converte os registros do banco. |
| `conexoes` | Centraliza a conexão JDBC com o Oracle. |
| `excecoes` | Padroniza as respostas de erro da aplicação. |
| `main` | Contém as classes utilizadas nos testes manuais. |

---

## Regras de Negócio

<details>
<summary>Ver regras implementadas</summary>

### Estações

- Nome, comunidade, município, UF e status são obrigatórios.
- A UF deve possuir exatamente duas letras.
- Os status permitidos são `ATIVA`, `MANUTENCAO` e `INATIVA`.
- Uma estação não pode ser excluída enquanto possuir leituras ou avisos vinculados.

### Leituras

- A estação informada deve existir.
- O nível da água deve ser maior ou igual a zero.
- O percentual da bateria deve ficar entre 0 e 100.
- A data da leitura não pode estar no futuro.
- A primeira leitura recebe a tendência `ESTAVEL`.
- Diferença maior que 2 cm recebe a tendência `SUBINDO`.
- Diferença menor que -2 cm recebe a tendência `DESCENDO`.
- Diferenças entre -2 cm e 2 cm recebem a tendência `ESTAVEL`.

### Avisos

- A estação informada deve existir.
- Título, mensagem, nível e status são obrigatórios.
- Os níveis permitidos são `INFORMATIVO`, `ATENCAO` e `CRITICO`.
- Os status permitidos são `ATIVO` e `ENCERRADO`.
- A data do aviso não pode estar no futuro.

</details>

---

## Banco de Dados

O projeto utiliza o **Oracle Database da FIAP** e realiza a persistência por meio de JDBC e do padrão DAO.

| Tabela | Conteúdo |
|---|---|
| `TB_ESTACAO` | Identificação, comunidade, município, UF e status da estação. |
| `TB_LEITURA` | Nível da água, percentual da bateria, data, hora e tendência calculada. |
| `TB_AVISO` | Título, mensagem, nível, data, hora e status do aviso. |

Uma estação pode possuir várias leituras e vários avisos. As leituras e os avisos possuem uma chave estrangeira associada à estação.

---

## Endpoints

A URL base da API publicada é:

```text
https://estacao-ribeirinha-api.onrender.com
```

### Estações

| Método | Endpoint | Finalidade | Resposta |
|---|---|---|---|
| `GET` | `/estacoes` | Listar estações | `200 OK` |
| `GET` | `/estacoes/{idEstacao}` | Buscar uma estação | `200 OK` |
| `POST` | `/estacoes` | Cadastrar uma estação | `201 Created` |
| `PUT` | `/estacoes/{idEstacao}` | Atualizar uma estação | `200 OK` |
| `DELETE` | `/estacoes/{idEstacao}` | Excluir uma estação sem vínculos | `204 No Content` |

### Leituras

| Método | Endpoint | Finalidade | Resposta |
|---|---|---|---|
| `GET` | `/leituras` | Listar leituras | `200 OK` |
| `GET` | `/leituras/{idLeitura}` | Buscar uma leitura | `200 OK` |
| `GET` | `/leituras/estacao/{idEstacao}` | Consultar o histórico da estação | `200 OK` |
| `GET` | `/leituras/estacao/{idEstacao}/ultima` | Consultar a última leitura | `200 OK` |
| `POST` | `/leituras` | Cadastrar uma leitura | `201 Created` |
| `PUT` | `/leituras/{idLeitura}` | Atualizar uma leitura | `200 OK` |
| `DELETE` | `/leituras/{idLeitura}` | Excluir uma leitura | `204 No Content` |

### Avisos

| Método | Endpoint | Finalidade | Resposta |
|---|---|---|---|
| `GET` | `/avisos` | Listar avisos | `200 OK` |
| `GET` | `/avisos/{idAviso}` | Buscar um aviso | `200 OK` |
| `GET` | `/avisos/estacao/{idEstacao}` | Consultar os avisos da estação | `200 OK` |
| `POST` | `/avisos` | Cadastrar um aviso | `201 Created` |
| `PUT` | `/avisos/{idAviso}` | Atualizar um aviso | `200 OK` |
| `DELETE` | `/avisos/{idAviso}` | Excluir um aviso | `204 No Content` |

---

## Exemplos de Requisições

<details>
<summary>Cadastrar uma estação</summary>

**POST `/estacoes`**

```json
{
  "nome": "Estação Arara Azul",
  "comunidade": "Comunidade Boa Esperança",
  "municipio": "Santarém",
  "uf": "PA",
  "status": "ATIVA"
}
```

</details>

<details>
<summary>Cadastrar uma leitura</summary>

**POST `/leituras`**

```json
{
  "idEstacao": 1,
  "nivelAguaCm": 145.5,
  "percentualBateria": 85
}
```

A data e a hora podem ser preenchidas automaticamente pela aplicação. A tendência também é calculada pelo backend a partir da leitura anterior.

</details>

<details>
<summary>Cadastrar um aviso</summary>

**POST `/avisos`**

```json
{
  "idEstacao": 1,
  "titulo": "Nível do rio em elevação",
  "mensagem": "A estação registrou tendência local de subida. Consulte os comunicados oficiais.",
  "nivel": "ATENCAO",
  "status": "ATIVO"
}
```

</details>

---

## Tratamento de Erros

A classe `GlobalExceptions` padroniza as principais respostas de erro:

| Código | Situação |
|---|---|
| `400 Bad Request` | Dados inválidos, regra de negócio não atendida ou JSON incorreto. |
| `404 Not Found` | Estação, leitura ou aviso não encontrado. |
| `500 Internal Server Error` | Falha de conexão, credenciais, driver ou erro inesperado. |

Exemplo de resposta:

```json
{
  "erro": "A estação informada não foi encontrada."
}
```

---

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot 4.1.2 | Construção da aplicação e da API REST |
| Spring Web MVC | Controllers e requisições HTTP |
| Maven Wrapper | Gerenciamento do projeto e das dependências |
| Oracle Database | Banco de dados relacional |
| JDBC | Comunicação entre Java e Oracle |
| OJDBC 17 | Driver de conexão com o Oracle |
| Docker | Empacotamento da aplicação |
| Render | Publicação da API |
| IntelliJ IDEA | Desenvolvimento e execução |
| Postman | Testes dos endpoints |

---

## Como Executar

### Pré-requisitos

- Java JDK 21.
- IntelliJ IDEA ou outra IDE compatível.
- Git.
- Docker, somente para a execução em contêiner.
- Acesso ao Oracle Database da FIAP.
- Tabelas `TB_ESTACAO`, `TB_LEITURA` e `TB_AVISO` criadas no Oracle.

### Clonar o repositório

```bash
git clone https://github.com/Augusto-Valerio/estacao-ribeirinha-api.git
cd estacao-ribeirinha-api
```

### Configurar as credenciais do Oracle

A aplicação utiliza as seguintes variáveis de ambiente:

```text
ORACLE_USUARIO
ORACLE_SENHA
```

As credenciais não devem ser escritas no código nem enviadas para o GitHub.

### Executar pelo IntelliJ IDEA

1. Abra o projeto pelo arquivo `pom.xml`.
2. Configure o SDK do projeto como Java 21.
3. Aguarde o Maven carregar as dependências.
4. Verifique se as tabelas do projeto já foram criadas no Oracle.
5. Acesse **Run → Edit Configurations**.
6. Selecione a classe `EstacaoRibeirinhaApiApplication`.
7. No campo **Environment variables**, informe:

```text
ORACLE_USUARIO=SEU_USUARIO;ORACLE_SENHA=SUA_SENHA
```

8. Execute a classe `EstacaoRibeirinhaApiApplication`.
9. Acesse:

```text
http://localhost:8080
```

### Executar com Docker

Crie a imagem:

```bash
docker build -t estacao-ribeirinha-api .
```

Execute o contêiner:

```bash
docker run --rm -p 8080:8080 -e ORACLE_USUARIO=SEU_USUARIO -e ORACLE_SENHA=SUA_SENHA estacao-ribeirinha-api
```

---

## Testes Manuais

O projeto possui classes com método `main` para executar testes manuais no IntelliJ IDEA.

| Classe | Finalidade |
|---|---|
| `TesteConexao` | Testar a conexão com o Oracle. |
| `TesteSelecionarEstacoes` | Listar as estações cadastradas. |
| `TesteBuscarEstacaoPorId` | Buscar uma estação pelo identificador. |
| `TesteConsultarDadosEstacao` | Consultar estação, leituras e avisos vinculados. |
| `TesteInserirEstacao` | Cadastrar uma estação. |
| `TesteAtualizarEstacao` | Atualizar uma estação existente. |
| `TesteInserirLeitura` | Cadastrar uma leitura e conferir a tendência calculada. |
| `TesteInserirAviso` | Cadastrar um aviso comunitário. |
| `TesteExcluirDadosEstacao` | Excluir avisos e leituras antes da exclusão da estação. |

Os endpoints também podem ser testados pelo Postman utilizando a aplicação local ou a API publicada no Render.

---

## Deploy

A API está publicada no Render utilizando o `Dockerfile` disponível na raiz do projeto.

As credenciais do Oracle são configuradas no Render como variáveis de ambiente e não ficam armazenadas no repositório.

- [Acessar a API publicada](https://estacao-ribeirinha-api.onrender.com/)
- [Acessar o frontend publicado](https://estacao-ribeirinha-front.onrender.com/)
- [Acessar o repositório do frontend](https://github.com/Augusto-Valerio/estacao-ribeirinha-front)

---

## Documentação

A documentação completa contém a especificação da solução, arquitetura, endpoints, telas do frontend, Diagrama Entidade-Relacionamento, diagramas de classes, evidências dos testes e instruções de execução.

[Baixar a documentação da Estação Ribeirinha](./Documentação_Estação_Ribeirinha.pdf)

---

<p align="center">
  Desenvolvido para a Global Solution 2026 - CitizenTech | FIAP
</p>
