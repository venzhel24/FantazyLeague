package ru.venzhel.fantazy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.venzhel.fantazy.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername_UserFound() {
        String username = "user1";

        Optional<User> user = userRepository.findByUsername(username);

        assertTrue(user.isPresent());
        assertEquals(username, user.get().getUsername());
    }

    @Test
    void findByUsername_UserNotFound() {
        String username = "non-existing-user";

        Optional<User> user = userRepository.findByUsername(username);

        assertFalse(user.isPresent());
    }

    @Test
    void findByEmail_UserFound() {
        String email = "user1@example.com";

        Optional<User> user = userRepository.findByEmail(email);

        assertTrue(user.isPresent());
        assertEquals(email, user.get().getEmail());
    }

    @Test
    void findByEmail_UserNotFound() {
        String email = "non-existing-email";

        Optional<User> user = userRepository.findByEmail(email);

        assertFalse(user.isPresent());
    }
}