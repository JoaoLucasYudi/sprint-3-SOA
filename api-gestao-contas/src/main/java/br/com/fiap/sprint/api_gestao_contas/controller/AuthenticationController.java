package br.com.fiap.sprint.api_gestao_contas.controller;

import br.com.fiap.sprint.api_gestao_contas.dto.LoginRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TokenResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import br.com.fiap.sprint.api_gestao_contas.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    // O AuthenticationManager é o objeto principal do Spring Security que processa a autenticação.
    // Vamos configurá-lo para ser injetável no próximo passo.
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginDTO) {
        // 1. Cria um objeto de "credenciais" com o email e senha. Este objeto ainda não está autenticado.
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());

        // 2. Passa as credenciais para o Spring Security. Ele irá:
        //    a) Chamar nosso AuthenticationService para buscar o usuário pelo email.
        //    b) Usar nosso PasswordEncoder para comparar a senha enviada com a senha criptografada no banco.
        //    c) Se tudo estiver correto, ele retorna um objeto 'auth' totalmente autenticado.
        //    d) Se algo estiver errado (senha inválida, usuário não existe), ele lança uma exceção.
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 3. Se a autenticação foi bem-sucedida, extraímos o objeto Usuario e geramos o token.
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        // 4. Retorna o token dentro do nosso DTO com um status 200 OK.
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}