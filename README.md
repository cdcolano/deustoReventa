# deustoReventa
_Lo primero que hay que realizar es compilar el código_

```
mvn clean compile
```
## Base de datos
_Crear una base de datos llamada productsDB en MySQL y dar permisos al usuario por defecto_
```

DROP SCHEMA IF EXISTS productsDB;
DROP USER 'spq'@'localhost';

CREATE SCHEMA productsDB;

CREATE USER 'spq'@'localhost' IDENTIFIED BY 'spq';
GRANT ALL ON productsDB.* TO 'spq'@'localhost';

```
_Despues crear las tablas en la base de datos_
```
mvn datanucleus:schema-create
```
_Por último,en dos ventanas distintas ejecutar_

```
_mvn jetty:run_
_mvn exec:java -Pclient
```
