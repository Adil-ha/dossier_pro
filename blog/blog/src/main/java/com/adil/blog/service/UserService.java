package com.adil.blog.service;


import com.adil.blog.dto.UserDTO;
import com.adil.blog.exception.DuplicateEmailException;
import com.adil.blog.exception.InvalidEmailException;
import com.adil.blog.entity.User;
import com.adil.blog.entity.UserRole;
import com.adil.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void register(User user){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if(!pattern.matcher(user.getEmail()).matches()){
            throw new InvalidEmailException("Votre est email est invalide");
        }
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new DuplicateEmailException("Votre mail existe déjà");
        }
        String cryptPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptPassword);
        user.setRole(UserRole.ROLE_USER);
        this.userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                this.userRepository
                        .findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("Aucun utilisateur ne correspond à cette identifiant"));

    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

}
