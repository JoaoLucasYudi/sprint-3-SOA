package br.com.fiap.sprint.api_gestao_contas.controller;

import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioResponseDTO;
// 1. IMPORT DA MUDANÇA: Importamos a INTERFACE em vez da classe concreta.
import br.com.fiap.sprint.api_gestao_contas.service.UsuarioServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    // 2. PONTO-CHAVE DA MUDANÇA: A injeção de dependência agora é feita pela INTERFACE.
    // O Spring se encarrega de encontrar uma classe (@Service) que implementa
    // esta interface (no caso, a sua classe UsuarioService) e injetá-la aqui.
    // Para o controller, tanto faz qual classe executa o serviço, desde que
    // ela siga o contrato definido pela interface.
    private UsuarioServiceInterface usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        // 3. NENHUMA MUDANÇA AQUI: O código dentro do método permanece idêntico.
        // Como tanto a interface quanto a classe original possuem o método "cadastrar",
        // a chamada continua funcionando perfeitamente.
        UsuarioResponseDTO responseDTO = usuarioService.cadastrar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}