# FAMILYROUTINE
### TECNOLOGIAS UTILIZADAS

Java JDK 17

Spring Boot 3.0.5

Spring Batch

Spring Data JDBC

Spring Security

### INSTALAÇÕES

#### Instalar o Java JDK 17 (Recomendo o do RedHat x64 MSI)

https://developers.redhat.com/products/openjdk/download

Atentem para ativar TUDO na instalação, o PATH e etc.

#### Instalar o MAVEN (OPCIONAL)

Escolher a versão Binary zip archive

https://maven.apache.org/download.cgi

Tutorial de como instalar o Maven e colocar as variáveis de ambiente

https://dicasdejava.com.br/como-instalar-o-maven-no-windows/

#### IDE

Recomendo o VSCode

https://code.visualstudio.com/download

Recomendo instalar algumas extensões que irão ajudar:

Extension Pack for Java, Language Support for Java(TM) by Red Hat.

### PARA RODAR A APLICAÇÃO

#### Docker
Para essa forma de rodar o projeto, faz-se necessário ter o Docker instalado. Para mais informações consulte a página do docker disponível [aqui](https://www.docker.com/products/docker-desktop/).
No diretório raiz da aplicação em um terminal, digite: 

```bash
docker build . -t familyroutine
```

Isso vai construir uma imagem no docker com o nome `familyroutine`. Para executar essa imagem, ou seja, rodar um container, fazemos assim:

```bash
docker run -d -p 8080:8080 --name familyroutinecontainer familyroutine
```

#### JAR
Para essa forma de rodar o projeto, faz-se necessário ter obrigatoriamente o MAVEN instalado. No diretório raiz da aplicação em um terminal, digite:

```bash
mvn build -DskipTests
```

Após terminar de buildar o projeto, note que vai ser gerado um JAR na pasta `target`. Para rodá-lo, basta digitar:

```bash
java -jar target/familyroutine-0.0.1-SNAPSHOT.jar
```

### IDE
Clonar o projeto escolhendo a branch develop, rodar aplicação via VSCode clicando em "Run" no arquivo `FamilyRoutineApplication.java` e ou apertando F5.

