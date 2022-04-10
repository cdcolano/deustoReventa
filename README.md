# deustoReventa
_Lo primero que hay que realizar es compilar el código_
´´´
mvn clean compile
´´´

Despues crear las tablas en la base de datos
...
_mvn datanucleus:schema-create_
...
Por último,en dos ventanas distintas ejecutar:

_mvn jetty:run_
_mvn exec:java -Pclient
