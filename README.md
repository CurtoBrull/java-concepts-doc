# Java Concepts Doc

Aplicación web educativa para consultar conceptos, subconceptos y preguntas frecuentes de Java, Spring, Spring Boot, JPA/Hibernate, Clean Code, SOLID y herramientas de desarrollo.

Construida con **Spring Boot 3**, **Thymeleaf**, **Tailwind CSS** y **PostgreSQL** (Neon).

## Características

- Navegación por bloques temáticos colapsables en el menú lateral.
- Páginas de concepto con explicación, ejemplo de código y Q&A.
- Páginas de subconcepto con explicación detallada y Q&A.
- Buscador de conceptos y subconceptos.
- Mapa visual con todos los conceptos organizados por bloque.
- Contenido idempotente: se puede reiniciar la aplicación sin duplicar datos.
- Diseño responsive y resaltado de sintaxis con Highlight.js.

## Stack tecnológico

- Java 21 (compilación)
- Spring Boot 3.3.5
- Spring Data JPA
- Thymeleaf
- PostgreSQL / H2
- Maven
- Tailwind CSS (CDN)
- Highlight.js

## Requisitos

- JDK 21 o superior (se recomienda Eclipse Temurin)
- Maven 3.9+
- (Opcional) Cuenta en [Neon](https://neon.tech) para base de datos en la nube

## Ejecución local con H2

El perfil por defecto usa una base de datos H2 en memoria, ideal para desarrollo:

```bash
mvn clean package -DskipTests
java -jar target/java-concepts-doc-1.0.0.jar
```

La aplicación estará disponible en `http://localhost:8080`.

Puedes acceder a la consola H2 en `http://localhost:8080/h2-console`.

## Ejecución local con Neon

1. Crea un fichero `.env` en la raíz del proyecto:

```env
NEON_URL=jdbc:postgresql://TU_HOST.neon.tech:5432/neondb?sslmode=require&channel_binding=require
NEON_USER=neondb_owner
NEON_PASSWORD=TU_PASSWORD
```

2. Compila y ejecuta con el perfil `neon`:

```bash
mvn clean package -DskipTests
java -jar target/java-concepts-doc-1.0.0.jar --spring.profiles.active=neon
```

## Estructura del proyecto

```
.
├── src/main/java/com/javaconcepts/doc/
│   ├── config/          # DataLoader y configuración
│   ├── controller/      # Controladores web
│   ├── dto/             # Objetos de transferencia
│   ├── entity/          # Entidades JPA
│   ├── repository/      # Repositorios Spring Data
│   └── service/         # Lógica de negocio
├── src/main/resources/
│   ├── templates/       # Vistas Thymeleaf
│   │   ├── layout/      # Layout principal
│   │   └── concepts/    # Páginas de conceptos
│   ├── application.properties       # Perfil por defecto (H2)
│   └── application-neon.properties  # Perfil Neon (PostgreSQL)
├── Dockerfile           # Build multi-etapa para Render
├── render.yaml          # Configuración de despliegue en Render
└── pom.xml
```

## Despliegue en Render

El repositorio incluye `Dockerfile` y `render.yaml` para desplegar en [Render](https://render.com).

### Pasos

1. Crea un nuevo **Web Service** en Render y selecciona el repositorio.
2. Render detectará automáticamente `render.yaml` si tienes **Blueprints** habilitado, o usa el `Dockerfile`.
3. Configura las variables de entorno en el panel de Render:

| Variable | Descripción |
|----------|-------------|
| `NEON_URL` | JDBC URL de tu base de datos Neon |
| `NEON_USER` | Usuario de Neon |
| `NEON_PASSWORD` | Contraseña de Neon |
| `SPRING_PROFILES_ACTIVE` | Debe ser `neon` |

4. Despliega. La aplicación arrancará con el perfil `neon` y cargará el contenido inicial automáticamente.

## Notas importantes

- **Java 25**: la aplicación compila con target Java 21 pero puede ejecutarse en Java 25. Spring Boot 3.3.5 solo certifica hasta Java 23, por lo que aparecerán advertencias en el arranque.
- **Lombok**: no se utiliza porque no es compatible con Java 25. Los getters/setters son explícitos.
- **DataLoader**: el contenido se carga de forma idempotente al iniciar la aplicación. Si añades o modificas conceptos, reinicia la app para actualizar la base de datos.
- **ddl-auto**: en producción (`neon`) está en `update`. En desarrollo local (`H2`) está en `none` porque el esquema se inicializa con el contenido del DataLoader.

## Scripts útiles

```bash
# Compilar
mvn clean package -DskipTests

# Ejecutar localmente con H2
java -jar target/java-concepts-doc-1.0.0.jar

# Ejecutar localmente con Neon
java -jar target/java-concepts-doc-1.0.0.jar --spring.profiles.active=neon

# Limpiar procesos Java (PowerShell)
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
```

## Licencia

Proyecto personal de aprendizaje.
