# Tech-Challenge

Desafio desenvolvido para o curso de Software Architecture da FIAP Pós Tech.

## Versão
2.0.0

## Requisitos minimos
- JDK 17
- maven 3.6.3 ou superior
- docker e docker-compose

## Para executar, siga os passos abaixo:

- Clone este repositório
```bash
$ git clone https://github.com/tech-challenge-team-fiap/tech-challenge.git
```
- Abra o projeto na IDE de sua preferência

- Rode o arquivo build-docker-image, o nome fiap-k8s será sua tag para baixar a imagem local via docker
```bash
$ ./build-docker-image fiap-k8s
```

## Deploy via Kubernetes

Para fazer o deploy desse projeto acesse a pasta ./k8s

- Pode executar os comandos abaixos de forma individual e na sequência ou executar o .sh disponivel na pasta.

```bash
kubectl apply -f mysql-secrets.yaml
kubectl apply -f mysql-configMap.yaml
kubectl apply -f db-deploy.yaml
kubectl apply -f app-deploy.yaml
```

Para visualizar a aplicação disponivel e acessar o Swagger, executar o comando abaixo:

```bash
Para API: kubectl port-forward <nome_pod> 8080:8080
Para DB:  kubectl port-forward <nome_pod> 3306:3306
```

Abra o Navegador após a liberação da porta:
> http://localhost:8080/swagger-ui/index.html#/

Se desejar remover todos os recursos criados, Pode executar os comandos abaixos de forma individual e na sequência ou executar o .sh

```bash
kubectl scale deployment --all --replicas=0
kubectl delete pods --all
kubectl delete services --all
kubectl delete deployments --all
```

