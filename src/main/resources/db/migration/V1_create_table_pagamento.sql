CREATE TABLE IF NOT EXISTS pagamentos (
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) DEFAULT NULL,
    numero VARCHAR(19) DEFAULT NULL,
    expiracao VARCHAR(7) NOT NULL,
    codigo VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    forma_de_pagamento_id bigint(20) NOT NULL,
    pedido_id bigint(20) NOT NULL,
);