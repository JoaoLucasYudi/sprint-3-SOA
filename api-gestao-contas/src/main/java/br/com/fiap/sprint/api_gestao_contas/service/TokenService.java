package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // A anotação @Value injeta um valor do nosso arquivo application.properties.
    // Iremos configurar esta propriedade no final.
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário fornecido.
     * @param usuario O usuário para o qual o token será gerado.
     * @return Uma string representando o token JWT.
     */
    public String generateToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura do token usando nossa chave secreta (HMAC256).
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("api-gestao-contas") // Identifica quem emitiu o token.
                    .withSubject(usuario.getEmail()) // O "dono" do token (o identificador do usuário).
                    .withExpiresAt(genExpirationDate()) // Define a data de expiração do token.
                    .sign(algorithm); // Assina o token, garantindo sua integridade.
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    /**
     * Valida um token JWT. Se o token for válido, retorna o "subject" (email do usuário).
     * @param token O token JWT a ser validado.
     * @return O email do usuário se o token for válido; uma string vazia caso contrário.
     */
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-gestao-contas") // Verifica se o emissor é o mesmo.
                    .build()
                    .verify(token) // Verifica a assinatura e a data de expiração.
                    .getSubject(); // Se tudo estiver OK, retorna o email.
        } catch (JWTVerificationException exception){
            return ""; // Retorna vazio se o token for inválido (expirado, assinatura incorreta, etc).
        }
    }

    /**
     * Método auxiliar para calcular a data de expiração do token.
     * @return Um Instant representando a data/hora de expiração (2 horas a partir de agora).
     */
    private Instant genExpirationDate(){
        // Define que o token expira em 2 horas, no fuso horário de Brasília.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}