package com.giullia.agenda.config;

import com.giullia.agenda.auth.Role;
import com.giullia.agenda.auth.User;
import com.giullia.agenda.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@vetclinic.com").isEmpty()) {
            userRepository.save(User.builder()
                    .nome("Administrador")
                    .email("admin@vetclinic.com")
                    .senha(passwordEncoder.encode("Admin@1234"))
                    .role(Role.ROLE_ADMIN)
                    .build());
        }
    }
}
