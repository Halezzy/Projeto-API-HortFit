# Projeto-API-HortFit
Bem-vindo ao HortFit! Este é um projeto que combina um backend em Spring Boot com um frontend em Java para criar uma aplicação completa de gerenciamento de usuários.

## Índice

- [Introdução](#introdução)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Funcionalidades](#funcionalidades)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Introdução

HortFit é uma aplicação desenvolvida para gerenciar usuários, permitindo cadastro, login, atualização e exclusão de usuários. A aplicação é composta por um backend em Spring Boot e um frontend em Java, integrados para fornecer uma experiência completa.

## Tecnologias Utilizadas

- **Backend**: Spring Boot, Spring Data JPA, MySQL
- **Frontend**: Java (JavaFX para interface gráfica)
- **Ferramentas**: GitHub, GitHub Desktop, IntelliJ IDEA, Android Studio

## Instalação

### Backend

1. Clone o repositório do backend:
    ```sh
    git clone https://github.com/Halezzy/HortFit-Backend.git
    cd HortFit-Backend
    ```

2. Configure o banco de dados MySQL no arquivo `application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hortfit_db
    spring.datasource.username=root
    spring.datasource.password=Halz
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    server.address=0.0.0.0
    server.port=8080
    ```

3. Execute a aplicação Spring Boot:
    ```sh
    ./gradlew bootRun
    ```

### Frontend

1. Clone o repositório do frontend:
    ```sh
    git clone https://github.com/Halezzy/HortFit-Frontend.git
    cd HortFit-Frontend
    ```

2. Abra o projeto no Android Studio.

3. Configure a URL do backend nas classes Java (substitua pelo IP da sua máquina ou use `10.0.2.2` para emulador):
    ```java
    URL url = new URL("http://192.168.1.100:8080/api/users/login");
    ```

4. Execute o aplicativo no emulador ou dispositivo Android.

## Configuração

### Configuração de CORS

Adicione a seguinte configuração de CORS no backend para permitir requisições do frontend:
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://10.0.2.2:8080", "http://192.168.1.100:8080") adicione o ip de sua máquina 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
Permissões no AndroidManifest.xml
Certifique-se de adicionar a permissão de internet no arquivo AndroidManifest.xml:

xml
<uses-permission android:name="android.permission.INTERNET" />
<application
    android:usesCleartextTraffic="true"
    ...>
    ...
</application>
Funcionalidades
Cadastro de Usuário: Permite o cadastro de novos usuários.

Login: Autenticação de usuários existentes.

Atualização de Perfil: Atualização dos dados do usuário.

Exclusão de Conta: Remoção de usuários.

Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.
