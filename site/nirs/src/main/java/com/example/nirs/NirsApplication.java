package com.example.nirs;

import com.example.nirs.models.Role;
import com.example.nirs.models.User;
import com.example.nirs.repository.RoleRepository;
import com.example.nirs.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class NirsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NirsApplication.class, args);
	}

}
