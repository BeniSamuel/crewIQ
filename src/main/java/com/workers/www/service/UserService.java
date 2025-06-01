package com.workers.www.service;

import com.workers.www.dto.RegisterUserDto;
import com.workers.www.model.User;
import com.workers.www.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers () {
        return this.userRepository.findAll();
    }

    public Page<User> getAllUserPaginated (int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return this.userRepository.findAll(pageable);
    }

    public User getUserByEmail (String email) {
        return this.userRepository.getUserByEmail(email).orElse(null);
    }

    public User getUserById (Long id) {
        return this.userRepository.getUserById(id).orElse(null);
    }

    public User createUser (RegisterUserDto registerUserDto) {
        User newUser = new User(registerUserDto.getName(), registerUserDto.getEmail(), passwordEncoder.encode(registerUserDto.getPassword()), registerUserDto.getRole());
        return this.userRepository.save(newUser);
    }

    public User updateUserById (Long id, RegisterUserDto registerUserDto) {
        User user = this.getUserById(id);
        if (user != null) {
            user.setName(registerUserDto.getName());
            user.setEmail(registerUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            user.setRole(registerUserDto.getRole());

            return this.userRepository.save(user);
        }
        return null;
    }

    public Boolean deleteUserById (Long id) {
        User user = this.getUserById(id);
        if (user != null) {
            this.userRepository.delete(user);
            return true;
        }
        return false;
    }
}
