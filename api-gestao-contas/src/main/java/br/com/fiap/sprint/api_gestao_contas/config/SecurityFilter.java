package br.com.fiap.sprint.api_gestao_contas.config;

import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import br.com.fiap.sprint.api_gestao_contas.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança que intercepta todas as requisições para validar o token JWT.
 * A anotação @Component é ESSENCIAL para que o Spring reconheça esta classe
 * como um Bean e possa injetá-la na SecurityConfig.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Tenta recuperar o token do cabeçalho da requisição.
        var token = this.recoverToken(request);

        if (token != null) {
            // 2. Se um token foi enviado, valida-o para extrair o email do usuário.
            var email = tokenService.validateToken(token);
            UserDetails user = usuarioRepository.findByEmail(email).orElse(null);

            if (user != null) {
                // 3. Se o token for válido e o usuário existir, nós o "autenticamos".
                // Isso informa ao Spring Security que o usuário está logado para esta requisição.
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 4. Continua a cadeia de filtros para que a requisição chegue ao seu destino (o controller).
        filterChain.doFilter(request, response);
    }

    /**
     * Método auxiliar para extrair o token do cabeçalho "Authorization".
     * O padrão é o token vir no formato "Bearer <token>", então removemos o prefixo.
     */
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}