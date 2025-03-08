--liquibase formatted sql

--changeset krupenko:1
CREATE DATABASE IF NOT EXISTS monitor_sensors_statistics;

CREATE TABLE sensor_statistics
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    total INT      NOT NULL,
    date  DATETIME NOT NULL
);

CREATE TABLE sensor_statistics_count_by_type
(
    sensor_statistics_id BIGINT       NOT NULL,
    type                 VARCHAR(255) NOT NULL,
    count_by_type        BIGINT       NOT NULL,
    PRIMARY KEY (sensor_statistics_id, type),
    FOREIGN KEY (sensor_statistics_id) REFERENCES sensor_statistics (id)
        ON DELETE CASCADE
);
