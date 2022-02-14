DROP TABLE IF EXISTS users, transaction;

CREATE TABLE IF NOT EXISTS users
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    balance DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS transaction
(
    id               SERIAL PRIMARY KEY,
    user_id          INT,
    price            DOUBLE PRECISION,
    transaction_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO users(name, balance)
VALUES ('Mike', 2000),
       ('John', 888);
