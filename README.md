# Tech challenge - FIAP
Fast food system development project, to support a neighborhood café that is expanding its operations due to its great success.

### Problem
The neighborhood fast-food joint, experiencing significant growth due to its success, faces challenges in customer service. The current manual order-taking process leads to chaos and confusion. Complex orders may be misinterpreted or forgotten, causing delays in preparation and customer dissatisfaction.

### Current Challenges
- Lost orders.
- Lack of assurance in correct order preparation.
- Delays in delivery due to coordination issues between staff and kitchen.

### Negative Impact
The expansion of the fast-food joint may be hindered by customer dissatisfaction, resulting in a loss of business.

### Proposed Solution
To overcome these challenges, the fast-food joint plans to invest in a Fast Food Self-Service System. This system will allow customers to place orders without interacting with an attendant, improving service efficiency.

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

### Nomenclature
The code was implemented following the English language standard, this decision was taken to avoid confusion caused by free translations, mainly of structures pertinent to the architecture and application domain, whose literature is mostly found in this language.

## Bounded contexts
https://miro.com/welcomeonboard/MWZjdUkwVXY0Ykx4MUNrSVMzaUxuOUpIUmRyS2dmMUFmbWtRMXp1UmRGdDI0T0hUdTE3VFhycmtNak8xUXBxQ3wzMDc0NDU3MzU4Nzk5NTQ2MTg5fDI=?share_link_id=337444221286

## Architecture - Phase 1
![image](https://github.com/tech-challenge-team-fiap/tech-challenge/assets/86020448/9f496121-6e63-44f7-93be-9fa8dc26f221)

The Hexagonal Architecture, or Ports and Adapters Architecture, provides a high degree of decoupling between the application's "core" rules and the technologies used to implement the different services. In this application, this becomes clear when it is observed that each service uses its own technology, and that if one day these technologies need to be changed, this should not impact the core business of the application, which is isolated and will remain functioning in the same way.

On the other hand, there is a significant increase in verbosity in the project due to the large number of conversions and mappings between classes, increasing complexity and reducing code readability. This is mainly due to the fact that the application is written in the form of a "monolith", meaning that it has many centralized responsibilities. If the application were divided into microservices, the hexagonal architecture would make more sense, as in atomic services the increase in complexity becomes less relevant, highlighting the main advantage of this architecture, which is the isolation of business rules.

## Architecture - Phase 2

![image](https://github.com/tech-challenge-team-fiap/tech-challenge/assets/18012024/18cc8a87-ec52-4186-a9ec-52012346d699)

Clean Architecture is a software design philosophy that prioritizes the creation of modular, scalable, and maintainable applications. Introduced by Robert C. Martin, also known as "Uncle Bob," Clean Architecture provides a structured approach to organizing code by defining clear boundaries between different components. The primary goal is to develop systems that are not only functional but also easy to understand, test, and adapt to changing requirements.

### Built With

* Java 17 LTS
* Maven 3.3+
* Docker
* Spring boot

# Docker Compose

### prerequisites
Docker version: 20.10.9+
Docker compose: v2+

1. Build the project using the command `mvn package`.
2. Run the command in the project folder `docker-compose up`.
3. After that, you can see the endpoints and model structure at 'http://localhost:8080/swagger-ui/index.html#/'

Note: Every time you run the `docker compose up` command it creates an image, if you make changes you need to delete the image using the `docker rmi tech-challenge-spring-app` command

### Releases

| Description                                      | Tag    | Link Github                                                            |
|--------------------------------------------------|--------|------------------------------------------------------------------------|
| Phase 01 - DDD, Docker and Software Architecture | v1.0.0 | https://github.com/tech-challenge-team-fiap/tech-challenge/tree/v1.0.0 |
| Phase 02 - Kubernetes and Clean Architecture     | v2.0.0 | https://github.com/tech-challenge-team-fiap/tech-challenge/tree/v2.0.0 |
| Phase 03 - DevOps, Serveless, Data Engineering   | v3.0.0 | in progress                                                            |
| Phase 04 -                                       | v4.0.0 | waiting                                                                |
| Phase 05 -                                       | v5.0.0 | waiting                                                                |

## Version
2.0.0

## To execute, follow the steps below:

- Clone this repository

```bash
$ git clone https://github.com/tech-challenge-team-fiap/tech-challenge.git
```
- Open the project in your preferred IDE

- Run the build-docker-image, the name fiap-k8s will be your tag to download the local image with docker

```bash
$ ./build-docker-image fiap-k8s
```

## Deploy with Kubernetes

#### To deploy this project, navigate to the ./k8s folder

- You can execute the commands below individually and in sequence, or run the .sh file available in the folder

```bash
kubectl apply -f mysql-secrets.yaml
kubectl apply -f mysql-configMap.yaml
kubectl apply -f db-deploy.yaml
kubectl apply -f app-deploy.yaml
```

To view the available application and access Swagger, execute the command below:

```bash
expose API: kubectl port-forward <nome_pod> 8080:8080
expose DB:  kubectl port-forward <nome_pod> 3306:3306
```

#### Open the browser after releasing the port:
> http://localhost:8080/swagger-ui/index.html#/

If you wish to remove all created resources, you can execute the commands below individually and in sequence or run the .sh file.

```bash
kubectl scale deployment --all --replicas=0
kubectl delete pods --all
kubectl delete services --all
kubectl delete deployments --all
```

