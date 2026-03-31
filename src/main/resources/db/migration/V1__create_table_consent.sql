CREATE TABLE IF NOT EXISTS consent (
    id UUID PRIMARY KEY,
    cpf VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    date TIMESTAMP,
    expiration_date TIMESTAMP
);