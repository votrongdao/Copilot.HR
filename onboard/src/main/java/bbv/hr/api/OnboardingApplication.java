package bbv.hr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "bbv.hr")
@EntityScan(basePackages = "bbv.hr.infrastructure.entities")
@EnableJpaRepositories(basePackages = "bbv.hr.infrastructure.repositories")
public class OnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardingApplication.class, args);
	}
}