package br.com.fiap.sprint.api_gestao_contas.repository;

import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Data JPA cria a busca por email automaticamente a partir do nome do método
    Optional<Usuario> findByEmail(String email);
}