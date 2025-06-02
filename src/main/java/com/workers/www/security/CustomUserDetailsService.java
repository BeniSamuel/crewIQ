package com.workers.www.security;

import com.workers.www.exception.NotFoundException;
import com.workers.www.service.UserService;
import lombok.AllArgsConstructor;
import com.workers.www.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getUserByEmail(username);
        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new NotFoundException("User not found!!!");
        }
    }
}
