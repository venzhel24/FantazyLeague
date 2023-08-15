package ru.venzhel.fantazy.service;

import lombok.NonNull;
import ru.venzhel.fantazy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(@NonNull String username);
    List<User> getAll();

    User getByEmail(String email);

    User getCurrentUser();
}
