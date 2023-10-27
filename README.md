

# Tech challenge 1
Fast food system development project, to support a neighborhood café that is expanding its operations due to its great success.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/#build-image)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.liquibase)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#features.docker-compose)

### Group
- [Thales Jolo](https://github.com/orgs/tech-challenge-team-fiap/people/thalesjolo)
- [Erikson Bastos](https://github.com/orgs/tech-challenge-team-fiap/people/EriksonB)
- [Samuel Almeida](https://github.com/orgs/tech-challenge-team-fiap/people/samucatezu)
- [Jair Cavalcante](https://github.com/orgs/tech-challenge-team-fiap/people/jaircavalcante)
- [Diego Fontgalland](https://github.com/orgs/tech-challenge-team-fiap/people/fontgalland)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Built With

* Java 17 LTS
* Maven 3.3+ 
* Docker
* Spring boot

### Nomenclature
The code was implemented following the English language standard, this decision was taken to avoid confusion caused by free translations, mainly of structures pertinent to the architecture and application domain, whose literature is mostly found in this language.

## Bounded contexts
https://miro.com/welcomeonboard/MWZjdUkwVXY0Ykx4MUNrSVMzaUxuOUpIUmRyS2dmMUFmbWtRMXp1UmRGdDI0T0hUdTE3VFhycmtNak8xUXBxQ3wzMDc0NDU3MzU4Nzk5NTQ2MTg5fDI=?share_link_id=337444221286

## Architecture 
![image](https://github.com/tech-challenge-team-fiap/tech-challenge/assets/86020448/9f496121-6e63-44f7-93be-9fa8dc26f221)

The Hexagonal Architecture, or Ports and Adapters Architecture, provides a high degree of decoupling between the application's "core" rules and the technologies used to implement the different services. In this application, this becomes clear when it is observed that each service uses its own technology, and that if one day these technologies need to be changed, this should not impact the core business of the application, which is isolated and will remain functioning in the same way.

On the other hand, there is a significant increase in verbosity in the project due to the large number of conversions and mappings between classes, increasing complexity and reducing code readability. This is mainly due to the fact that the application is written in the form of a "monolith", meaning that it has many centralized responsibilities. If the application were divided into microservices, the hexagonal architecture would make more sense, as in atomic services the increase in complexity becomes less relevant, highlighting the main advantage of this architecture, which is the isolation of business rules.


# Docker Compose

### prerequisites
Docker version: 20.10.9+
Docker compose: v2+

1. Build the project using the command `mvn package -Pdocker`.
2. Run the command in the project folder `docker compose up`.
3. After that, you can see the endpoints and model structure at 'http://localhost:8080/swagger-ui/index.html#/'

Note: Every time you run the `docker compose up` command it creates an image, if you make changes you need to delete the image using the `docker rmi tech-challenge-spring-app` command

### 
