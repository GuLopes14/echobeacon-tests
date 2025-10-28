CREATE TABLE moto (
        id BIGSERIAL PRIMARY KEY,
        placa VARCHAR(7) NOT NULL,
        chassi VARCHAR(17) NOT NULL,
        modelo VARCHAR(255) NOT NULL,
        problema VARCHAR(150) NOT NULL,
        echo_beacon_id BIGINT,
        CONSTRAINT fk_echo_beacon
        FOREIGN KEY (echo_beacon_id)
        REFERENCES echo_beacon(id)
);
