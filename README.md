# deustoReventa

## Base de datos
_Crear una base de datos llamada productsDB en MySQL y dar permisos al usuario por defecto_
```

DROP SCHEMA IF EXISTS productsDB;
DROP USER IF EXISTS 'spq'@'localhost';

CREATE SCHEMA productsDB;

CREATE USER 'spq'@'localhost' IDENTIFIED BY 'spq';
GRANT ALL ON productsDB.* TO 'spq'@'localhost';
```

_Lo primero que hay que realizar es compilar el código_

```
mvn clean compile
```

_Despues lanzar los test unitarios_
```
mvn test -Punit
```

_Despues realizar el enhance_
```
mvn datanucleus:enhance
```


_Despues crear las tablas en la base de datos_
```
mvn datanucleus:schema-create
```

_Rellenar Datos de prueba_
```
mvn exec:java -Pdatos
```
_Lanzar el servidor_

```
mvn jetty:run
```

_Si se desea lanzar los test de integracion, sino saltar este paso_

```
mvn test -Pintegration
```

_Si se desea lanzar los test de performance, sino saltar este paso_

```
mvn test -Pperformance
```

_Lanzar el cliente_

```
mvn exec:java -Pclient
```

_Para generar la documentacion_

```
mvn doxygen:report
```
```
mvn validate
```

