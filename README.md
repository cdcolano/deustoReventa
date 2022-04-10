# deustoReventa
Lo primero que hay que realizar es compilar el código

_mvn clean compile_

_mvn datanucleus:schema-create_

En dos ventanas distintas ejecutar:

_mvn jetty:run_
_mvn exec:java -Pclient
