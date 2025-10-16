package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Este serviço é a ponte entre o nosso modelo de dados (Usuario) e o Spring Security.
 * Sua única função é carregar os detalhes de um usuário pelo seu nome de usuário (no nosso caso, o email).
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * O Spring Security chama este método automaticamente durante o processo de autenticação.
     * Ele recebe o email do DTO de login e precisa retornar um UserDetails.
     * Como nossa classe Usuario implementa UserDetails, podemos retorná-la diretamente.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }
}