CREATE TABLE car (
                     id BIGSERIAL PRIMARY KEY,
                     brand VARCHAR(255),
                     model VARCHAR(255),
                     price DECIMAL(12, 2)
);

CREATE TABLE person (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        age INT,
                        has_license BOOLEAN,
                        car_id BIGINT NOT NULL REFERENCES car(id)
);