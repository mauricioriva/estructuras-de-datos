
Proyecto 2 Estructuras de Datos con su representación en SVG 

Las estructuras que puede representar el programa son: ArbolBinario, ArbolBinarioOrdenado, ArbolBinarioCompleto, ArbolRojinegro,
ArbolAVL, Lista, Pila, Cola, Arreglo, MonticuloMinimo y MonticuloArreglo.

Si en la entrada del programa hay almohadillas(#) escribirlas despues del elemento en la linea sin ningun espacio.
No ingresar lineas en blanco sino el programa terminara.

Un ejemplo sería:

ArbolRojinegro 23 55 43 78 56 37 42 12 90 9 45 3


Una opcion para poder correr el programa es:

	$] cat ArchivoEntrada | java -jar proyecto2.jar > ArchivoSalida

Para generar el archivo proyecto2.jar se usa maven

	$] mvn install
