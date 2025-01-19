# Reading Tracker

Reading Tracker es una aplicación web donde los usuarios pueden guardar sus lecturas para tener un registro. Cada libro tiene como información un título, autor, páginas, 
estado (leído o no leído) y fecha en que fue guardado. De este modo los usuarios pueden registrar sus lecturas así como guardar futuras lecturas pendientes. Sobre los libros
se pueden realizar las operaciones básicas CRUD: Create, Read, Update y Delete. De este modo un usuario puede crear un libro, cambiar su estado y eliminarlo.
Para crear una cuenta simplemente se necesita un nombre de usuario y una contraseña de al menos 8 caracteres.

## Tecnologías utilizadas

<img src="https://github.com/devicons/devicon/blob/master/icons/html5/html5-plain-wordmark.svg" height="100" width="100"><img src="https://github.com/devicons/devicon/blob/master/icons/css3/css3-plain-wordmark.svg" height="100" width="100"><img src="https://github.com/devicons/devicon/blob/master/icons/javascript/javascript-original.svg" height="100" width="100"><img src="https://github.com/devicons/devicon/blob/master/icons/vuejs/vuejs-original.svg" height="100" width="100"><img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" height="100" width="100"><img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original-wordmark.svg" height="100" width="100"><img src="https://github.com/devicons/devicon/blob/master/icons/postgresql/postgresql-plain-wordmark.svg" height="100" width="100">
<img src="https://github.com/devicons/devicon/blob/master/icons/docker/docker-plain-wordmark.svg" height="100" width="100">
<img src="https://github.com/devicons/devicon/blob/master/icons/nginx/nginx-original.svg" height="100" width="100">
Utilicé Java con Spring Boot para el backend, con dependencias como Spring Security y Spring Data Rest, entre otras, la autenticación se realiza con JSON Web Tokens. 
El frontend usa JavaScript con Vue y la base de datos es PostgreSQL. Para el deploy cada parte (frontend, backend y base de datos) corre en un contenedor de Docker diferente y Nginx sirve como reverse proxy para manejar 
las peticiones y redirigirlas al frontend o el backend según sea necesario, además de encargarse de manejar la encriptación con https. 
