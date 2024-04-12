package com.example.practiceapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Получаем данные формы входа из запроса
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Создаем объект аутентификации
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            // Пытаемся аутентифицировать пользователя
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException,
            ServletException {
        JwtEntity jwtEntity = (JwtEntity) authResult.getPrincipal();
        Long userId = jwtEntity.getId();


        // Если аутентификация прошла успешно, создаем JWT токен
        String token = jwtTokenProvider.createAccessToken(userId, authResult.getName(), authResult.getAuthorities());

        // Добавляем токен в заголовок ответа
        response.addHeader("Authorization", "Bearer " + token);

        response.sendRedirect("/admin");
    }
}

