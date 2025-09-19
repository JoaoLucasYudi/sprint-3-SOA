-- Cria a tabela para armazenar os dados dos usuários e seus limites
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    limite_deposito_mensal DECIMAL(19, 2) NOT NULL,
    data_criacao TIMESTAMP,
    status VARCHAR(50) NOT NULL
);

-- Cria a tabela para registrar todas as transações financeiras
CREATE TABLE transacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    tipo_transacao VARCHAR(50) NOT NULL,
    valor DECIMAL(19, 2) NOT NULL,
    data_transacao TIMESTAMP,
    -- Chave estrangeira para garantir que cada transação pertence a um usuário válido
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);