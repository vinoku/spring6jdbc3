CREATE SCHEMA IF NOT EXISTS spring6jdbc3;

SET SCHEMA spring6jdbc3;

CREATE TABLE IF NOT EXISTS speaker (id IDENTITY NOT NULL PRIMARY KEY,
name VARCHAR (100) NOT NULL);


