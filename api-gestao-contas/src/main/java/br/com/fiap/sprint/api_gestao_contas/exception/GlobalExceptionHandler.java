package br.com.fiap.sprint.api_gestao_contas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.stream.Collectors;

// A anotação @RestControllerAdvice torna esta classe um componente global para tratar exceções
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura erros de validação dos DTOs (acionados pela anotação @Valid no Controller).
     * Retorna um status 400 Bad Request com um mapa dos campos e seus respectivos erros.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Coleta todos os erros de validação de campo em um mapa
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage()
                ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Captura a nossa exceção personalizada para recursos duplicados (ex: email já cadastrado).
     * Retorna um status 409 Conflict com uma mensagem de erro simples.
     */
    @ExceptionHandler(RecursoJaExistenteException.class)
    public ResponseEntity<Map<String, String>> handleRecursoJaExistenteException(RecursoJaExistenteException ex) {
        // Cria um mapa simples para a mensagem de erro
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(ValidacaoNegocioException.class)
    public ResponseEntity<Map<String, String>> handleValidacaoNegocioException(ValidacaoNegocioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }
}