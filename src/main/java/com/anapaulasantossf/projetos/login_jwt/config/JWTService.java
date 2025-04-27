package com.anapaulasantossf.projetos.login_jwt.config;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.valueToTree(user);
            objectNode.remove("password");
            String payload = mapper.writeValueAsString(objectNode);

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("login-jwt-api")
                    .withSubject("Token de autenticação da API")
                    .withPayload(payload)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException | JsonProcessingException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }


    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
