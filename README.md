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
_Despues realizar el enhance_
```
mvn datanucleus:enhance
```

```
_Despues crear las tablas en la base de datos_
```
mvn datanucleus:schema-create
```
_Lanzar el servidor_

```
mvn jetty:run
```

_Lanzar el cliente_

```
mvn exec:java -Pclient
```
