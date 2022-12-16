package com.anderscore.authorization;

import com.anderscore.authorization.domain.user.User;
import com.anderscore.authorization.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// CHECK JWT TOKEN HERE : https://jwt.io/

//	@Bean
//	CommandLineRunner commandLineRunner(UserService userService) {
//		return args -> {
//
//
//			userService.addRoleToUser(8L, 1L);
//			userService.addRoleToUser(9L, 1L);
//			userService.addRoleToUser(10L, 1L);
//			userService.addRoleToUser(11L, 2L);
//		};
//
//	}
}
