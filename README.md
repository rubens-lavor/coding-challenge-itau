# coding-challenge-itau
Repositório dedicado ao desafio itaú devs

---
No projeto, foram criadas 2 API, a primeira, **film-review-api**, é uma aplicação que tem
como finalidade reunir opniões sobre determinado filme, armazenando notas, comentários e que permite interação entre 
as avaliações dos usuários. A seguna é a **security-api**, ela é nossa api de segurança, responsável por autenticar o usuário
e gerar um token JWT.

É importante mencionar que a security-api gera o token, mas não o valida posteriormente.
A responsablidade de validar o token fica para a api que consome nossa api de segurança, no caso a film-review-api.

---
## Meu ambiente de desenvolvimento para este projeto:


- Ubuntu 20.04 LTS
- Java 11
- Docker 20.10.7
- Intellij IDEA Community
- Postman
- DBeaver

## Para rodar o projeto você vai precisar:

- Java **versão 11** ou superior instalado.

- Docker instalado

- IDE para Java


Para verificar a sua versão do JDK e docker, abra o terminal e entre com os comandos:
>java -version

>docker -v 

## Tendo isso você deve:

- Baixar o projeto
- Abra o projeto na IDE de sua escolha, abra um terminal interno da própria IDE
ou externo, desde que esteja na pasta raíz do projeto a coding-challenge-itau,
e entre com o comando:
> docker-compose up

no caso de sistemas linux, é provável que você tenha que rodar como administrador
> sudo docker-compose up 

Assim subirá um container mysql em seu computador na porta 3306, caso você tenha
o mysql instalado localmente e rodando nesta porta, pode trocar no application.yaml para 3307, por exemplo.

Com isso você já pode rodar as aplicações, primeiro, vá até: `film-review-api > src > java > com > filmreview` e abra
o arquivo `Application`  e dê um **run** nesta classe, no intellij o atalho para rodar as classes executáveis é Shift + F10.

Em seguida vá até a security-api, segue caminho: `security-api > src > java > com > security` e abra o 
arquivo `SecurityApplication`, basta rodar essa classe também.

A film-review-api vai rodar na porta 8080 e a security-api na 8081.


**OBS**: É importante que sua IDE enxergue film-review-api e security-api como **módulos**, como de fato são. 
Caso contrário as classes executáveis `Application` e `SecurityApplication` podem não se comportar como tal e o projeto não rodar. 

Estou deixando um vídeo mostrando como rodar e possíveis contornos para esse problema,caso sua IDE seja Intellij IDEA Community isso não deve ser um problema.

---
## Rotas públicas da aplicação:
`/create-account`

`/login-account`

## Crie um usuário

Agora você pode criar um usuário via Postman (Insomnia ou outro de sua preferência) e testar as funcionalidades da aplicação.

Acesse a rota http://localhost:8080/create-account passando o body no formato Json, conforme exemplo abaixo:
```json
{
    "name":"name",
    "username":"user.name",
    "email":"email@mail.com",
    "password":"123"
}
```

<p align="center">
<img src="./utils/img/create-account.png">
</p>

## Faça o login na conta criada:

Em seguida você deve logar na aplicação pela rota http://localhost:8080/login-account 
passando as credencias: username e password.

```json
{
    "username":"user.name",
    "password":"123"
}
```
<p align="center">
<img src="./utils/img/login-account.png">
</p>

Como resposta dessa requisição você recebe um token, que te dará acesso as rotas privadas.

---

## Processo de geração do Bearer token
Ao acessar a rota `http://localhost:8080/login-account`, passando username e senha
no corpo da requisição, a film-review-api manda uma requisição para a security-api, 
`http://localhost:8081/login`, que valida as credenciais(username e senha) e gera um token 
a partir delas, esse token é recebido pela film-review-api e retornado ao usuário.

Com o token é possível  acessar as demais rotas da aplicação, 
passando o mesmo no cabeçalho das requisições.


---

### Abaixo segue o vídeo onde eu mostro como baixar, rodar e demonstro o fluxo de autentição do projeto.

https://youtu.be/GYwTBmdqa6A

---

## Explicando o projeto

A seguir vou apresentar as entidades e a ideia por trás delas, mostrando os atributos e omitindo os métodos.


## As classes


`Film`, a classe é composta por um title, imdbID que é o id externo da api que estamos consumindo para buscar os filmes,
rating que é a média das notas de cada review, e uma lista de review, que são as críticas/avaliações feitas pelos usuários para o filme em questão.

Apesar de buscarmos o filme numa api externa, após a busca e avaliação do mesmo, ele é salvo na nossa base de dados pegando o nome e imdbID.

``` java
@Entity
@Table(name = "Films")
public class Film extends  AbstractEntity {

    private String title;

    @Column(name = "imdb_ID")
    private String imdbID;

    private Double rating = 0.0;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Review> reviews = new ArrayList();

}
```
<br>

`Review` é a crítica do usuário, nela temos uma referência para reviewer, que é a pessoa que fez a crítica, uma lista de comentários,
a ideia é que o mesmo usuário possa deixar vários comentários, tipo: 

`assisti mil vezes, muito bom!!!`

`amo o leonardo dicaprio`

...

Completando temos uma nota e uma referência para film, pro mapeamento funcionar certinho.

``` java
@Entity
@Table(name = "Reviews")
public class Review extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Comment> comments = new ArrayList<>();

    private Double grade;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

}
```
<br>

`Comments` são os nossos comentários, ela tem uma flag que diz se o comentário é repetido ou não, uma lista de 
`EvaluationComment` representando os likes e dislikes desse comentário, `ReplyComment` as respostas do comentário e `QuoteComment` as citações. Uma referência para `review`

``` java
@Entity
@Table(name = "Comments")
public class Comment extends AbstractCommentEntity {

    private Boolean isRepeated = false;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<EvaluationComment> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<ReplyComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<QuoteComment> quotes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
```

<br>

`EvaluationComment` é a classe responsável por receber o like/dislike do comentário, a ideia de trazer isso pra uma tabela separada e não simplesmente um inteiro na tabela Comments, é de não permitir que o mesmo reviewer dê likes e dislikes indefinidamente no mesmo cometário, como essa classe contém referência para reviewer, que to chamando de sender, e comment, é possível saber se a pessoa já deixou uma avaliação para o comentário e não permitir a operação repetida.

``` java
@Entity
@Table(name = "Evaluations")
public class EvaluationComment extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "sender_id")
    private Reviewer sender;

    @Enumerated(EnumType.STRING)
    private EvaluationType type;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}

public enum EvaluationType {
    LIKE,
    DISLIKE
}
```
<br>

`ReplyComment` são as respostas deixas num comentário, guardando quem comentou.
``` java
@Entity
@Table(name = "Replies")
public class ReplyComment extends AbstractCommentEntity {

    @OneToOne
    @JoinColumn(name = "sender_id")
    private Reviewer sender;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
```

<br>

`QuoteComment` são as citações
``` java
@Entity
@Table(name = "Quotes")
public class QuoteComment extends AbstractCommentEntity {

    @OneToOne
    @JoinColumn(name = "sender_id")
    private Reviewer sender;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
```
<br>

`Reviewer` é a classe que representa os nossos usuáros, com suas informações de perfil.

``` java
@Entity
@Table(name = "Reviewers")
public class Reviewer extends AbstractEntity{

    private String name;

    private String username;

    private String email;

    private String password;

    private Integer score = 0;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ProfileType profileType = ProfileType.READER;
}

```

<br>

## Listando todos os filmes salvos na minha base local de dados, após alguns testes dos endpoint:


``` json

[
	{
		"id": "217a8c62-7132-4f09-97d7-faa01035ef10",
		"imdbID": "tt0133093",
		"title": "The Matrix",
		"rating": 9.666666666666666,
		"reviews": [
			{
				"id": "07eb9c62-c651-41f5-99ea-2947271ace6f",
				"filmId": "217a8c62-7132-4f09-97d7-faa01035ef10",
				"grade": 10.0,
				"reviewer": {
					"id": "9bc03bd6-9ca6-420c-939b-328a09bbd617",
					"name": "rubens",
					"username": "rubens.lavor",
					"profile": "MODERATOR"
				},
				"comments": []
			},
			{
				"id": "6d5168e3-2d59-46d7-833a-d08e265ac6fd",
				"filmId": "217a8c62-7132-4f09-97d7-faa01035ef10",
				"grade": 10.0,
				"reviewer": {
					"id": "e8b77566-a9f0-4089-99ae-3572d7b2d4f5",
					"name": "rubens 3",
					"username": "rubens3.lavor",
					"profile": "BASIC"
				},
				"comments": []
			},
			{
				"id": "7d8eeb3e-18b4-43af-a48c-a063f3271b4f",
				"filmId": "217a8c62-7132-4f09-97d7-faa01035ef10",
				"grade": 9.0,
				"reviewer": {
					"id": "5276b37d-c8ad-4ae4-8462-0a0eee0692b0",
					"name": "rubens2",
					"username": "rubens2.lavor",
					"profile": "MODERATOR"
				},
				"comments": []
			}
		]
	},
	{
		"id": "6675a647-0206-4db5-9db9-cd4c322d7525",
		"imdbID": "tt1375666",
		"title": "Inception",
		"rating": 9.25,
		"reviews": [
			{
				"id": "1f39e496-7e62-4d9f-8c02-4de808fe4164",
				"filmId": "6675a647-0206-4db5-9db9-cd4c322d7525",
				"grade": 10.0,
				"reviewer": {
					"id": "e8b77566-a9f0-4089-99ae-3572d7b2d4f5",
					"name": "rubens 3",
					"username": "rubens3.lavor",
					"profile": "BASIC"
				},
				"comments": []
			},
			{
				"id": "7924ff36-a4e9-43f7-bb7a-200bb22cc221",
				"filmId": "6675a647-0206-4db5-9db9-cd4c322d7525",
				"grade": 8.5,
				"reviewer": {
					"id": "9bc03bd6-9ca6-420c-939b-328a09bbd617",
					"name": "rubens",
					"username": "rubens.lavor",
					"profile": "MODERATOR"
				},
				"comments": [
					{
						"id": "58a8deaf-dbf9-49c8-95fa-09c9e47c3fff",
						"description": "comentário para 'A Origem'",
						"like": 1,
						"dislike": 0,
						"createdAt": "2022-06-27T22:30:41.159976",
						"replies": [],
						"quotes": [],
						"repeated": false
					}
				]
			}
		]
	}
]

```
<br>
<br>

Do desafio completo ficou faltando a implementação do cache.

<details><summary><b>Desafio detalhado:</b></summary>


CHALLENGE - Sistema de críticas de filmes


Descrição:


Antes de começar o desafio, é importante informar que esse modelo de
desafio é um modelo exaustivo, ou seja, não esperamos que você
entregue tudo que está aqui, mas que desenvolva o máximo que
conseguir com qualidade. Não se preocupe em entregar tudo, o
importante é ir parte por parte e dar o seu melhor!


PARTE 1 - Sistema e regras de negócio
Quando vamos assistir um filme é comum pedirmos indicações.
Queremos saber o que as pessoas acharam do filme, se é algo que vale
a pena ser assistido. Portanto, crie um sistema que tenha a finalidade
de receber e armazenar comentários e notas de um filme.
Crie um api onde um usuário poderá se cadastrar. Cada usuário terá um
perfil na plataforma, sendo eles: LEITOR, BÁSICO, AVANÇADO, e
MODERADOR. Todo usuário deve começar como LEITOR e poderá ir
avançando de perfil conforme a interação com a plataforma.
LEITOR: Após o cadastro, esse usuário poderá logar e buscar por um
filme. Ele poderá ver as informações de um filme, comentários e dar
uma nota para o filme. A cada filme que o usuário avaliar, ele ganha 1
ponto em seu perfil.
BÁSICO: O usuário leitor poderá se tornar BÁSICO quando adquirir 20
pontos. Nesse perfil será possível postar comentários, notas e
responder comentários. Cada resposta que o usuário enviar, ele ganha
1 ponto.
AVANÇADO: O usuário básico poderá se tornar AVANÇADO quando
adquirir 100 pontos. Esse perfil tem as capacidades do BÁSICO, e mais
citar outros comentários (comentários feitos por outros usuários) e
marcar comentários como “gostei” ou "não gostei”.

MODERADOR: Um usuário poderá se tornar MODERADOR de 2
formas: um moderador torna outro usuário moderador ou por pontuação,
para se tornar MODERADOR o usuário deverá ter 1000 pontos. O
moderador tem as capacidades do AVANÇADO, e mais excluir um
comentário ou marcar como repetida.
A busca pelo filme na sua api deve ser feita consultando uma api
pública chamada OMDb API (https://www.omdbapi.com/) e os
comentários e notas devem ser salvos no seu sistema.


PARTE 2 - Segurança
A segurança de um sistema é um dos pontos mais importantes que
precisamos ter para garantir uma maior confiabilidade para os nossos
clientes e para o nosso próprio projeto. Quando falamos de segurança
em um projeto, podemos ir além de funcionalidades básicas que
linguagens e frameworks nos entregam. Podemos pensar, arquitetar e
desenvolver soluções totalmente voltadas para promover uma maior
segurança.
Com base nisso, neste desafio você deve criar uma nova API de
autorização de login.
Para que um usuário cadastrado possa realizar as operações no seu
sistema, ele deve se autenticar. Com isso, crie uma nova API que ficará
somente responsável de autenticar esse usuário.
Quando sua API de críticas receber uma requisição de login com os
dados do usuário de login e senha, ela deverá fazer uma requisição
para a API de autenticação passando as informações de login e senha.
Sua API de autenticação deverá fazer a validação daquele login e senha
estão corretos. Caso esteja, deverá ser gerado um token e devolvido
para a API de críticas, que devolverá para o usuário.

Caso o login e senha estiverem errado, sua API de autenticação deverá
salvar um cache com uma tentativa de login e a cada nova tentativa de
login errada esse cache deve ser atualizado.

Requisitos
- Um usuário não poderá logar sem ter feito um cadastro
- Um usuário não poderá ver filmes e comentários e notas sem
estar logado
- Um usuário não poderá criar, editar ou excluir comentários e notas
sem estar logado
- Um usuário de um determinado perfil não poderá realizar ações
que não fazem parte de seu perfil
- Todas as funcionalidade de seu sistema devem receber um token
de autenticação, gerados pela sua API de autorização
- Um usuário não autenticado(que não possui o token) não poderá
realizar ações no sistema.
- Um usuário com token invalido não poderá realizar ações no
sistema.
- Todas as tentativas falhas de login devem ser salvas em um
cache.
- Caso um usuário tente 3 vezes logar e erre, na 4 vez deverá ser
retornado uma mensagem de “limite de tentativas excedido “
Requisitos não funcionais
- Armazene os dados de cadastro de usuário e produtos em uma
base de dados da sua escolha, inclusive H2.
- Crie um cache para tentativas de login, o cache pode ser
utilizando um provedor de cache ou o próprio hashMap do java
- Desenvolva suas API’s utilizando a linguagem JAVA.
- Você deve utilizar os filmes fornecidos pela API
https://www.omdbapi.com/, ou seja deverá consumir essa API na
sua aplicação.
</details>

