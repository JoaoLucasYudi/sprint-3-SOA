package br.com.fiap.sprint.api_gestao_contas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotação já define o status HTTP padrão para esta exceção
@ResponseStatus(HttpStatus.CONFLICT)
public class RecursoJaExistenteException extends RuntimeException {
    public RecursoJaExistenteException(String message) {
        // O construtor simplesmente passa a mensagem de erro para a classe pai (RuntimeException)
        super(message);
    }
}