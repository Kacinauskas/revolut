CREATE TABLE account
(
    id          VARCHAR(10)    NOT NULL PRIMARY KEY,
    type        VARCHAR(20)    NOT NULL,
    balance     DECIMAL(20, 2) NOT NULL,
    customer_id INT            NOT NULL
);