CREATE TABLE echo_beacon (
    id BIGSERIAL PRIMARY KEY,
    numero_identificacao INT UNIQUE,
    status_conexao VARCHAR(255) NOT NULL
);
