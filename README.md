# coding-challenge-itau
Repositório dedicado ao desafio itaú devs

---
No projeto, foram criadas 2 API, a primeira, **film-review-api**, é uma aplicação que tem
como finalidade reunir opnioes sobre determinado filme, armazenando notas, comentários e que permite interação entre 
as avaliações dos usuários. A seguna é a **security-api**, ela é nossa api de segurança, responsável por autenticar o usuário
e gerar um token JWT.

É importante mencionar que a security-api gera o token, mas não o valida posteriormente.
A responsablidade de validar o token fica para a api que consome nossa api de segurança, no caso a film-review-api.

---
## Meu abiente de desenvolvimento para este projeto:

- Ubuntu 20.04 LTS
- Java 11
- Docker 20.10.7
- Intellij IDEA Community
- Postman
- DBeaver

Para verificar a sua versão do JDK e docker, abra o terminal de entre com os comandos:
>java -version

>docker -v 


## Para rodar o projeto você vai precisar:

- Java **versão 11** ou superior instalado.

- Docker instalado

- IDE para Java

## Tendo isso você deve:

- Baixar o projeto
- Abra o projeto na IDE de sua escolha, abra um terminal interno da própria IDE
ou externo, desde que esteja na pasta raíz do projeto a coding-challenge-itau,
e entre com o comando:
> docker-compose up

no caso de sistemas linux, é provável que você tenha que rodar como administrador
> sudo docker-compose up 

assim subirá um container mysql em seu computador na porta 3306, caso você tenha
o mysql instalado localmente e rodando nesta porta, pode trocar no application.yaml para 3307, por exemplo.

Com isso você já pode rodar as aplicações, primeiro, vá até: film-review-api > src > java > com > filmreview e abra
o arquivo Application  e dê um **run** nesta classe, no intellij o atalho para rodar as classes executáveis é Shift + F10.
Em seguida vá até a security-api, segue caminho: security-api > src > java > com > security e abra o 
arquivo SecurityApplication, basta rodar essa classe também.
A film-review-api vai rodar na porta 8080 e a security-api na 8081.

## Crei um usuário

Agora você pode criar um usuário e testar as funcionalidades da aplicação.
Acesse a rota localhost:8080/create-account passando o body:
```json
{
    "name":"name",
    "username":"user.name",
    "email":"email@mail.com",
    "password":"123"
}
```

Em seguida você deve logar na aplicação pela rota localhost:8080/login-account 
passando as credencias: username e password.

## Processo de geração do Bearer token
Ao acessar a rota *localhost:8080/login-account*, passando username e senha
no corpo da requisição, a film-review-api manda uma requisição para a security-api, 
*localhost:8081/login*, que valida as credenciais(username e senha) e gera um token 
a partir delas, esse token é recebido pela film-review-api e tornado ao usuário.

Com o token é possível  acessar as demais rotas da aplicação, 
passando o mesmo no cabeçalho das requisições.

Abaixo segue um vídeo demonstrando
https://youtu.be/GYwTBmdqa6A

## As classes

## Modelagem no banco de dados

## Os Endpoints


