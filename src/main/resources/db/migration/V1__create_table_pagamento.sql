CREATE TABLE pagamentos (
    id BIGSERIAL PRIMARY KEY,
    valor NUMERIC(19, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    pedido_id BIGINT NOT NULL
);