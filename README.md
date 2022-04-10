# deustoReventa
_Lo primero que hay que realizar es compilar el código_

```
mvn clean compile
```

_Despues crear las tablas en la base de datos_
´´´
mvn datanucleus:schema-create
´´´
_Por último,en dos ventanas distintas ejecutar_

´´´
_mvn jetty:run_
_mvn exec:java -Pclient
´´´
