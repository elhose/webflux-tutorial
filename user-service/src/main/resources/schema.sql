DROP TABLE IF EXISTS users, user_transaction;

CREATE TABLE IF NOT EXISTS users
(
    id      INT PRIMARY KEY NOT NULL,
    name    TEXT,
    balance MONEY
);

CREATE TABLE IF NOT EXISTS user_transaction
(
    id               INT PRIMARY KEY NOT NULL,
    user_id          INT             NOT NULL,
    price            MONEY,
    transaction_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);


