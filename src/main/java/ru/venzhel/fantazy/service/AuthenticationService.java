package ru.venzhel.fantazy.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.venzhel.fantazy.dto.AuthRequest;
import ru.venzhel.fantazy.dto.AuthResponse;
import ru.venzhel.fantazy.dto.RegisterRequest;
import ru.venzhel.fantazy.model.User;

import java.io.IOException;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}
