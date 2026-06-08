CREATE TABLE IF NOT EXISTS pagamentos (
    id BIGSERIAL PRIMARY KEY,
    valor NUMERIC(19, 2) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    numero VARCHAR(19) NOT NULL,
    expiracao VARCHAR(10) NOT NULL,
    codigo VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    forma_de_pagamento VARCHAR(20) NOT NULL,
    pedido_id BIGINT NOT NULL
    );
