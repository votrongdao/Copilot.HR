package bbv.hr.api.config;

import bbv.hr.infrastructure.entities.User;
import bbv.hr.infrastructure.entities.UserStatus;
import bbv.hr.infrastructure.entities.Role;
import bbv.hr.infrastructure.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeed {

    @Bean
    CommandLineRunner createTestUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            if (userRepository.findByEmail("hr@bbv.com").isEmpty()) {

                User user = new User();

                user.setEmail("hr@bbv.com");
                user.setPassword(
                        passwordEncoder.encode("123456")
                );

                user.setRole(Role.HR);
                user.setStatus(UserStatus.ACTIVE);

                userRepository.save(user);
            }
        };
    }
}
