CREATE TABLE transfer
(
    id             VARCHAR(32)  NOT NULL PRIMARY KEY,
    created_at     TIMESTAMP    NOT NULL,
    beneficiary_id INT          NOT NULL,
    remitter_id    INT          NOT NULL,
    amount         INT          NOT NULL,
    details        VARCHAR(100) NOT NULL,
    account_id     VARCHAR(10)  NOT NULL
);