CREATE TABLE pagamentos_cartao (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    numero VARCHAR(19) NOT NULL,
    expiracao VARCHAR(10) NOT NULL,
    codigo VARCHAR(3) NOT NULL,
    forma_de_pagamento VARCHAR(20) NOT NULL,
    CONSTRAINT fk_pagamentos_cartao FOREIGN KEY (id) REFERENCES pagamentos (id) ON DELETE CASCADE
);