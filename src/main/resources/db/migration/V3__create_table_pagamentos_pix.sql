CREATE TABLE pagamentos_pix (
    id BIGINT PRIMARY KEY,
    forma_de_pagamento VARCHAR(20) NOT NULL,
    CONSTRAINT fk_pagamentos_pix FOREIGN KEY (id) REFERENCES pagamentos (id) ON DELETE CASCADE
);