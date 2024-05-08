package com.adil.blog.service;


import com.adil.blog.entity.User;
import com.adil.blog.entity.UserRole;
import com.adil.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void register(User user){
        if(!user.getEmail().contains("@")){
            throw new RuntimeException("Votre est email est invalide");
        }
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new RuntimeException("Votre mail existe déjà");
        }
        String cryptPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptPassword);
        user.setRole(UserRole.ROLE_USER);
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                this.userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Aucun utilisateur ne correspond à cette identifiant"));

    }
}
