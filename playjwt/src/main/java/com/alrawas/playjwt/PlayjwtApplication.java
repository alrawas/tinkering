package com.alrawas.playjwt;

import com.alrawas.playjwt.domain.AppUser;
import com.alrawas.playjwt.domain.Role;
import com.alrawas.playjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class PlayjwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayjwtApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(null, "John Connor", "john_c", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "John Travolta", "john_t", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Shrek", "shrek", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Fionna Connor", "fionna", "1234", new ArrayList<>()));

            userService.addRoleToUser("john_c", "ROLE_USER");
            userService.addRoleToUser("john_t", "ROLE_MANAGER");

            userService.addRoleToUser("shrek", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("shrek", "ROLE_ADMIN");
            userService.addRoleToUser("shrek", "ROLE_USER");
            userService.addRoleToUser("shrek", "ROLE_MANAGER");

            userService.addRoleToUser("fionna", "ROLE_USER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
