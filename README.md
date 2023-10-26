# Read Me First
The following was discovered as part of building this project:

* The original package name 'br.com.fiap.tech-challenge' is invalid and this project uses 'br.com.fiap.techchallenge' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/#build-image)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.liquibase)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#features.docker-compose)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)

Please review the tags of the used images and set them to the same as you're running in production.

# Docker Compose

### Pré requisitos
Docker version: 20.10.9+
Docker compose: v2+

1. Buildar o projeto utlizando o comando `mvn package -Pdocker`.
2. Executar o comando na pasta do projeto `docker compose up`.

Obs: Toda vez que roda do commando `docker compose up` ele cria uma imagem, se realizar alterações precisa apagar a imagem utilizando o commando `docker rmi tech-challenge-spring-app`