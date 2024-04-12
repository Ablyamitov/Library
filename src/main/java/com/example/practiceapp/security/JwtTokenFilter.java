package com.example.practiceapp.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.GenericFilterBean;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      String bearerToken = ((HttpServletRequest)servletRequest).getHeader("Authorization");
        if (bearerToken!=null && bearerToken.startsWith("Bearer ")){
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken!=null && !bearerToken.equals("null") && jwtTokenProvider.validateToken(bearerToken)){
            try{    //если токен валидный, получаем аутентификацию
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if(authentication!=null){   //пользователь аутентифицирован
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception ignored){}

        }
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
