# Advert API

![technology java](https://img.shields.io/badge/technology-Java-blue.svg)
![technology Gradle](https://img.shields.io/badge/technology-Gradle-blue.svg)
![technology Spring](https://img.shields.io/badge/technology-SpringBoot-green.svg)

> Aplicação que faz parte do Projeto Integrado - Imóvel Simplificado - 
> Microserviço reponsável pelo cadastro de Anúncios.

<p align="right">(<a href="#top">back to top</a>)</p>

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:
<!---Estes são apenas requisitos de exemplo. Adicionar, duplicar ou remover conforme necessário--->

* Você instalou a versão mais recente de `Java`
* Você possui uma instância do `MySQL8` rodando (Container disponível na pasta docker)
* Você ajustou as variáveis de ambiente configuradas no `application.yml`

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- TECHNOLOGIES -->

## :pencil2: Tecnologias utilizadas

* [**Java 17**](https://www.java.com/pt-BR/)
* [**Gradle**](https://docs.gradle.org/current/userguide/userguide.html) | _or use the wrapper ./gradlew_
* [**Spring Boot 3**](https://spring.io/)
* [**Docker**](https://docs.docker.com/docker-for-mac/install/#download-docker-for-mac)
* [**MySQL8**](https://www.mysql.com/)
* [**Lombok**](https://projectlombok.org/)

<p align="right">(<a href="#top">back to top</a>)</p>

## 🚀 Instalando a aplicação

Para instalar a aplicação, siga estas etapas:

- Faça o clone do projeto na sua máquina
- Importe o projeto na IDE que você utilizará (por ex. IntelliJ)
- Ajuste o arquivo de configuração `application.yml` na pasta `src/main/resources`, com as variáveis locais do seu ambiente.

<p align="right">(<a href="#top">back to top</a>)</p>

## ☕ Rodando a aplicação

Para rodar a aplicação, siga estas etapas:

### Instalando dependências

- Execute o comando abaixo na raiz do projeto para instalar as dependências:

```properties
./gradlew clean build
```

### Executando os testes

- Execute o comando abaixo para executar os testes da aplicação

```properties
./gradlew clean test
```

- O relatório dos testes se encontrará na pasta /build/jacoco, sendo possível visualizar em página web acessando /build/jacocoHtml/index.html

### Executando a aplicação

```properties
 ./gradlew bootRun
```

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- RESOURCES -->

## 📝 Documentação da API

A aplicação possui documentação Swagger, acessível pela URL http://host/api/v1/swagger-ui/index.html

<p align="right">(<a href="#top">back to top</a>)</p>