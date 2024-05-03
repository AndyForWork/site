package com.example.nirs.services;


import com.example.nirs.models.Role;
import com.example.nirs.models.User;
import com.example.nirs.repository.RoleRepository;
import com.example.nirs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public BCryptPasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getById(Integer userId){
        return userRepository.findById(userId).get();
    }

    public void addBalance(Integer userid, Integer addBalance){
        User user = userRepository.findById(userid).get();
        user.setBalance(user.getBalance()+addBalance);
        userRepository.save(user);
    }



}