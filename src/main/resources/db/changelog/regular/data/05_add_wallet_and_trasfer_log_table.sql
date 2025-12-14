CREATE TABLE training.wallet
(
    id      BIGSERIAL PRIMARY KEY,
    balance DOUBLE PRECISION NOT NULL,
    version BIGINT           NOT NULL DEFAULT 0
);

CREATE TABLE training.transfer_log
(
    id      BIGSERIAL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    status  VARCHAR(255) NOT NULL CHECK (status IN ('SUCCESS', 'FAILURE'))
);

