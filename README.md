# springboot-servicio-items
## Servicio REST creado con SpringBoot documentado con Swagger que consume otro Servicio por RestTemplate y/o Feign implementando un balanceo de carga con Eureka o Ribbon

Este proyecto es un ejemplo de construcción de una API Rest creada con Springboot.
La información expuesta por la API es generada por la implementacion de un Cliente RestTemplate y/o Feign que consume los datos del servicio Productos y esta balanceado con Ribbon o Eureka.
La API se Auto-documenta con las dependencias de Swagger

> Para implementar Swagger en la API:
  - Insertar las dependencias "springfox-swagger2" y "springfox-swagger-ui" en el POM.xml
  - Crear la clase SwaggerConfig.java

> Implemantar Eureka:
- Eureka contiene Ribbon por lo que si se configura, las dependencias de Ribbon se tienen que comentar

> Actuator:
- Es una dependencia que nos permite actualizar configuraciones properties sin tener que reiniciar el servicio. 
	Se realiza por medio de un endpoint "/actuator/refresh" y una anotacion @RefreshScope
	Actualiza la configuracion realizada en las properties en el servidor de configuración 	"config-server" que fueron previamente modificadas y subidas al repositorio de GIT.
	Esto lo hace sin tener que reiniciar el servicio

> Configurarlo para el Config Server (Servidor de Configuracion):
- Para que funcione como ciente de Config Server, hay que implementar la dependencia "spring-cloud-starter-config"
- Crear un archivo de propiedades de arranque "bootstrap.properties" (con la ruta del Config Server) para que ni bien 
  arranque el servicio-items busque en Config Server toda la configuracion del repositorio GIT, y luego de eso se registre 
  en Eureka.
  El orden de ejecución de los archivos properties es, 1ro bootstrap.properties y 2do application.properties  

> Implenta Resilience4J: 
- Como funciona el Patron CircuitBreaker: 
Se establece una “Ventana Deslizante” en 100 peticiones slidingWindowsSize(100), con un “Umbral de Fallas” de 50 peticiones failureRateThreshold(50). Si la tasa de error sobrepasa ese Umbral, entonces se entra en estado Abierto de CircuitBreaker (Cortocircuito). Y cuando se cumplan las 100 peticiones establecidas en la Ventana Deslizante, todas las posteriores van a retornar error hasta que se cumpla el tiempo establecido para el cortocircuito, configurado en waitDurationInOpenState(60000 ms). 
Al cumplirse waitDurationInOpenState se pasa a un estado Semi Abierto por 10 peticiones, configurado en permittedNumberOfCallsInHalfOpenState(10). 
En este caso es el mismo criterio que arriba, si en esas 10 peticiones del estado Semi Abierto, el 50% retorna error entonces se vuelve al estado Abierto, de lo contrario se pasa a estado Cerrado y se procede con normalidad.
Este mecanismo o patrón de comunicación, sirve para gestionar los errores y evitar caídas en cascada.


> Configuraciones:
- Impl: Importar como proyecto Maven
- URL: http://localhost:8002/swagger-ui.html
- Postman: /springboot-servicio-producto/src/main/resources/items.postman_collection.json


> Subir Proyecto a GitHub:
- git init
- git add .
- git commit -m "first commit"
- git remote add origin https://github.com/canitanoa/springboot-servicio-item.git
- git push -u origin master