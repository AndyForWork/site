package com.example.nirs.services;

import java.util.HashSet;
import java.util.Set;

import com.example.nirs.dtos.LoginDTO;
import com.example.nirs.models.Role;
import com.example.nirs.models.User;
import com.example.nirs.repository.RoleRepository;
import com.example.nirs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new User(username, encodedPassword, authorities,0));
    }

    public LoginDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );


            return new LoginDTO(userRepository.findByUsername(username).get().getUsername(),userRepository.findByUsername(username).get().getPassword());

        } catch(AuthenticationException e){
            return new LoginDTO(null, null);
        }
    }

}