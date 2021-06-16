# springboot-servicio-items
## Servicio REST creado con SpringBoot documentado con Swagger que consume otro Servicio por RestTemplate

Este proyecto es un ejemplo de construcción de una API Rest creada con Springboot.
La información expuesta por la API es generada por la implementacion de un Cliente RestTemplate que consume los datos del servicio Productos.
La API se Auto-documenta con las dependencias de Swagger.r

> Para implementar Swagger en la API:
  - Insertar las dependencias "springfox-swagger2" y "springfox-swagger-ui" en el POM.xml
  - Crear la clase SwaggerConfig.java

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