# AREP-LAB04
Autor: David Leonardo Piñeros Cortés
##   Taller de de modularización con virtualización e Introducción a Docker

El objetivo de este taller es desplegar una aplicación de mensajes basado en Spring-Boot. El usuario puede ingresar mensajes y se deben mostrarse los últimos 10 registros en la base de datos con su respectiva fecha. 

## Instalación y Ejecución

Las siguientes instrucciones son para ejecutar el proyecto LOCALMENTE. El primer paso es instalar git, maven, java y docker en su equipo con las siguientes versiones:
* Apache Maven 3.9.6
* Java 21
* Docker 25.0.3

y luego de esto clonar el repositorio desde la terminal de la siguiente manera:
```
git clone https://github.com/leoncico/AREP-LAB04.git
```
Seguido de esto, se ingresa a la carpeta resultante y se ejecutan los siguientes comandos de Maven:

```
mvn clean install
```
A continuación, ingresa el último comando para construir los contenedores

```
docker-compose up -d
```

Se debe esperar 1 o 2 minutos hasta que las máquinas inicie. Luego ya debe estar en ejecución el servidor así que ingresa desde un Browser a la dirección [localhost:8080/logs.html]() para interactuar con la aplicación web.

Para probar la funcionalidad ingresa un mensaje y oprime el botón de enviar. Debería mostrarse el mensaje que ingresaste en la tabla inferior
![](/img/01.png)

## Arquitectura
-   Web-Bootstrap (Cliente Web):
    -   Contiene un campo de entrada y un botón que permiten al usuario ingresar una cadena de texto.
    -   Al hacer clic en el botón, el cliente envía el texto a un servicio REST y luego actualiza la pantalla con la respuesta en formato JSON que recibe del servidor.
-   APP-LB-RoundRobin (Balanceador de Carga Round Robin):
    -   Es un componente clave que implementa un algoritmo de balanceo de carga de Round Robin.
    -   Este componente recibe las peticiones REST del cliente web y se encarga de distribuirlas equitativamente entre las tres instancias de LogService.
    -   Esto asegura que la carga de procesamiento esté distribuida de manera uniforme entre las instancias, lo que mejora la eficiencia y la escalabilidad de la aplicación.
-   Docker Engine:
    -   Se está utilizando un entorno Docker para la ejecución de los servicios.
    -   Dentro de Docker, se han desplegado múltiples instancias del servicio LogService y una base de datos MongoDB.
-   LogService (Instancias 1, 2, 3):
    -   Estas son tres instancias del mismo servicio LogService que se encargan de recibir las peticiones que les delega el balanceador de carga.
    -   Cada instancia puede procesar la petición de forma independiente y devolver una respuesta.
-   MongoDB:
    -   Se utiliza una base de datos MongoDB para almacenar información, específicamente los mensajes con las fechas ingresadas.
    -   Cada instancia de LogService puede interactuar con esta base de datos para almacenar o recuperar datos.
-   AWS EC2:
    -   Toda la arquitectura está desplegada en una instancia de EC2 de Amazon Web Services.
    -   Esta instancia proporciona la infraestructura subyacente donde se ejecuta el motor Docker y todos los servicios relacionados.
-   Security Group:
    -   El Security Group de AWS actúa como un firewall virtual que controla el tráfico entrante y saliente hacia la instancia de EC2.
    -   Asegura que solo el tráfico permitido (como las peticiones web y las conexiones a MongoDB) pueda llegar a los servicios alojados en Docker.

![](/img/02.png)

## Evaluación