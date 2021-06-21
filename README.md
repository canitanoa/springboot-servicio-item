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