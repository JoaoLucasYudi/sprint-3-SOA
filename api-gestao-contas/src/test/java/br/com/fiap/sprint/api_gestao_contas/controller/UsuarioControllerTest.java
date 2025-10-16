package br.com.fiap.sprint.api_gestao_contas.controller;

import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest carrega o contexto completo da sua aplicação para o teste.
@SpringBootTest
// @AutoConfigureMockMvc configura o MockMvc, a ferramenta para simular requisições HTTP.
@AutoConfigureMockMvc
class UsuarioControllerTest {

    // Injeta o MockMvc para performar as requisições.
    @Autowired
    private MockMvc mockMvc;

    // Injeta o ObjectMapper para converter nosso objeto DTO em uma string JSON.
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrar_DeveRetornarStatus201_QuandoDadosForemValidos() throws Exception {
        // --- ARRANGE (Preparação) ---
        var usuarioDTO = new UsuarioRequestDTO();
        usuarioDTO.setNome("Usuario de Teste");
        // Use um email diferente a cada execução do teste para evitar o erro de "email já existe".
        usuarioDTO.setEmail("teste" + System.currentTimeMillis() + "@email.com");
        usuarioDTO.setSenha("senhaForte123");
        usuarioDTO.setLimiteDepositoMensal(new BigDecimal("1000.00"));

        // --- ACT (Ação) ---
        // Simula uma requisição POST para /usuarios.
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                // --- ASSERT (Verificação) ---
                // Espera que o status da resposta seja 201 Created.
                .andExpect(status().isCreated())
                // Espera que a resposta JSON tenha um campo "email" com o valor que enviamos.
                .andExpect(jsonPath("$.email").value(usuarioDTO.getEmail()));
    }
}