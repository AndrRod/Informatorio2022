# Java - Spring Boot (Proyecto: API REST) INFORMATORIO 2022 🚀

## Desarrollado por 🖥️  [AndresRodriguez](https://www.linkedin.com/in/andres-rodriguez-60a166208/) - [GitHub](https://github.com/AndrRod) - [PortFolio](https://andresporfolio.herokuapp.com/)

### Objetivo

Proyecto API REST - El objeto es crear un proyecto escalable, donde se utilizan conceptos vinculados java 8, buenas practicas, programación funcional, seguridad y docker. 


### Herramientas utilizadas
- 👉 Java y Spring Boot
- 👉 Las rutas siguen el patrón REST
- 👉 Librería Spring Security
- 👉 Encriptación de contraseñas (PasswordEncoder)
- 👉 Utilización de tokens para validar rol de usuario a fin de que pueda utilizar determinados paths (access_token)  y refresh tokens para actualizar el periodo de vida del token (refresh_token).
- 👉 Manejo de Excepciones (Carpeta exception - excepciones particulares reutilizables - manejo general de excepciones)
- 👉 Manejo de Mensajes de respuestas (Clases reutilizables, properties exclusivos para mensajes, MessageSource)
- 👉 Paginación (Mediante una clase reutilizable llamada PaginationMessage).
- 👉 Utilización de DTOs (creación manual).
- 👉 Soft delete (borrado logico).
- 👉 Queries (consultas SQL en capas Repositories).

---------------------------

### Para ejecutar la API 👇🏻

* Descargar el [Proyecto](https://github.com/AndrRod/Informatorio2022/archive/refs/heads/main.zip) o [Clonarlo](https://github.com/AndrRod/Informatorio2022.git) .
* En la consola se dirige a la carpeta del proyecto (```  /Informatorio2022-main ``` ).
* Ejecuta los siguientes comandos: ``` mvn clean install ```  y luego ``` mvn spring-boot:run ``` .
* Pruebe ejecutar los endpoints, siguiendo la documentación de la API detallada a continuación.

## Requerimientos técnicos
## 1. A. Modelado de Base de Datos

**User:** es una ENTITY y contiene:
    
     - id. (autogenerado)
     - firstName.
     - lastName.
     - email.
     - password.
     - role. (role User autogenerado)
     - createDate (fecha creación autogenerada)
     - delete.

**Role:** es un ENUM y contiene los siguientes roles:

    - "ROLE_USER";
    - "ROLE_COLLABORATOR";    
    - "ROLE_OWNER";


---------------------------

## 2. Autenticación de Usuarios
El usuario despues de registrarse y logearse, obteniene un token, el cual es necesario y requerido para acceder a los demás paths, una vez que pasa 30 minutos el token queda desactualizado o vencido, lo que obliga a que el usuario vuelva a generarlo mediante un nuevo login.

### POST (registro)
	http://localhost:8080/auth/register

Ejemplo:

    {
        "firstName": "andres",
        "lastName": "rodriguez",
        "email": "rodrigueza.federacion@gmail.com",
        "password": "12345689"
    }
Ejemplo creación exitosa:
    
![img.png](img.png)

Ejemplo error en la creación:

![img_1.png](img_1.png)

### POST (login)

	http://localhost:8080/auth/login

Ejemplo:

    {   
        "email": "rodrigueza.federacion@gmail.com",
        "password": "12345689"  
    }

Ejemplo login exitoso:

![img_4.png](img_4.png)

Ejemplo login con error:

![img_3.png](img_3.png)

### TOKENS
Para acceder a cualquier path que requiera rol ("ROLE_USER", "ROLE_OWNER" O "ROLE_COLLABORATOR), una vez logeado, se debe ingresar el token.
El token se encuentra codificado y está compuesto por tres partes como se ve en la imagen:

![img_5.png](img_5.png)

Acceder a un path con token

![img_10.png](img_10.png)

Acceder a un path sin token

![img_11.png](img_11.png)



Los tokens tienen un periodo de duración (access_token = 10 minutos y refresh_token = 30 minutos). 

Token vencido:

![img_8.png](img_8.png)

Actualización de token:

Post:

        http://localhost:8080/auth/refresh

Ejemplo:

        {
            "refresh_token": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RyaWd1ZXphLmZlZGVyYWNpb25AZ21haWwuY29tIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvbG9naW4iLCJleHAiOjE2NTE1MTU4NjB9.cFGlQOVZPZKPehbYePopqa3dkbMVUGE29Nn9zCVriAM"
        }
Respuesta:

![img_12.png](img_12.png)

### POST (actualizar rol de usuario)

	http://localhost:8080/users/role/{id}

Ejemplo:

Path:

    http://localhost:8080/users/role/14

Body:

    {
        "roleName": "USER"
    }
Ejemplo de actualización exitosa de rol:

![img_7.png](img_7.png)

Ejemplo error actualización de rol:

![img_6.png](img_6.png)


## 3. QUERIES (en capa Repositories)

a. Example: Busqueda filtrando por subentidad. 

        @Query("SELECT v FROM Vote v WHERE v.event.id = :id")
        List<Vote> listVoteByEventId(@Param("id") Long id);

b. Example: Informa la cantidad de Votos recibidos por una entidad dependiendo el "name" y "id" del evento que participa.

        @Query("SELECT COUNT(v) FROM Vote v WHERE v.entrepreneurshipVoted.name = :name AND v.event.id = :id")
        Long countVoteByEntreprNameAndByEventId(@Param("name") String name, @Param("id") Long id);

c. Example: Informa los Usuarios que contengan cadena String en el columna "name" y los trae de manera paginada.

    @Query(value = "SELECT u FROM User u WHERE u.firstName LIKE %:firstName%")
    Page<User> findByNameAprox(@Param("firstName") String firstName, Pageable pageable);

d. Example: Informa los Usuarios cuya fecha de creación coincidan en la busqueda y los trae de manera paginada.

    @Query(value = "SELECT u FROM User u WHERE u.creationDate >= :startDate AND u.creationDate <= :finishDate")
    Page<User> findByCreationDateAprox(@Param("startDate") LocalDateTime startDate, @Param("finishDate") LocalDateTime finishDate, Pageable pageable);

---------------------------
🎁 Hecho por  [AndresRodriguez](https://www.linkedin.com/in/andres-rodriguez-60a166208/) !!!!
