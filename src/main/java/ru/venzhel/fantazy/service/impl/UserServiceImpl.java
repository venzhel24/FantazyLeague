package ru.venzhel.fantazy.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.model.User;
import ru.venzhel.fantazy.repository.UserRepository;
import ru.venzhel.fantazy.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Optional<User> getByUsername(@NonNull String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return null;
        }
        return users;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
    }
}
