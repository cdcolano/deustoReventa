DROP SCHEMA IF EXISTS productsDB;
DROP USER IF EXISTS 'spq'@'localhost';

CREATE SCHEMA deustoReventaDB;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON deustoReventaDB.* TO 'spq'@'localhost';
