package com.anapaulasantossf.projetos.login_jwt.config;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthentificationFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Se for POST para /auth/login ou /users, só passa para frente sem fazer nada
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (HttpMethod.POST.matches(method) && (path.equals("/auth/login") || path.equals("/users"))) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);
        if (token != null){
            System.out.println("CONTEM TOKEN");
            Map<String, Claim> payload = jwtService.validateToken2(token);
            String email = payload.get("email").toString().replace("\"","");
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            System.out.println("NÃO CONTEM TOKEN");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inexistente ou inválido");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null)
            return null;

        return authHeader.replace("Bearer ", "");
    }

}
