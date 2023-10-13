package imd.ufrn.familyroutine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableJpaRepositories
@OpenAPIDefinition(info = @Info(title = "FamilyRoutine API REST", version = "1.0", description = "Documentação da API REST no contexto do projeto FamilyRoutine v1.0"))
public class FamilyroutineApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilyroutineApplication.class, args);
	}

}
