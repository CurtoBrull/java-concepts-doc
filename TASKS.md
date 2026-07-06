# Tareas del proyecto Java Concepts Doc

Documento de seguimiento de cambios, tareas realizadas y pendientes.

## Estado general

- **Repositorio**: https://github.com/CurtoBrull/java-concepts-doc
- **Estado**: Desplegado en Render con base de datos Neon.
- **Último commit en main**: `2649296` feat: add README, Actuator, optimize DataLoader startup, fix Dockerfile and Render health check

---

## Tareas completadas

### Fase 1: Estructura base y contenido inicial

- [x] Crear proyecto Spring Boot 3 con Thymeleaf, JPA y PostgreSQL/H2.
- [x] Definir entidades: `Concept`, `SubConcept`, `ConceptQuestion`, `SubConceptQuestion`.
- [x] Crear DTOs, repositorios, servicio y controladores.
- [x] Crear layout Thymeleaf con sidebar y vistas de mapa, concepto, subconcepto y búsqueda.
- [x] Añadir contenido inicial de Java Core, Spring, Spring Boot y JPA/Hibernate.
- [x] Implementar `DataLoader` idempotente para cargar/actualizar contenido.

### Fase 2: Ampliación de contenido

- [x] Añadir bloque `CLEAN_CODE_SOLID` con SOLID y Clean Code.
- [x] Añadir bloque `TOOLS` con Maven, Git, Docker, SQL, Logging y Gradle.
- [x] Añadir conceptos avanzados: Programación Funcional, Design Patterns, JVM/GC, Serialización.
- [x] Añadir conceptos de Spring: REST API, Servlets/Filtros, OpenAPI/Swagger, JWT.
- [x] Añadir conceptos de Spring Boot: Microservicios, Actuator, WebClient/RestTemplate, Profiles/Config, Spring Cache, Mensajería.
- [x] Añadir conceptos de JPA/Hibernate: Transacciones, JDBC.
- [x] Ampliar Testing: tests de integración, TDD, cobertura y calidad.

### Fase 3: UX/UI y arquitectura

- [x] Hacer colapsables las categorías del menú lateral.
- [x] Añadir botones "Expandir todo" / "Colapsar todo".
- [x] Persistir estado del sidebar en `localStorage`.
- [x] Resaltar enlace activo y expandir su categoría automáticamente.
- [x] Reordenar bloques de forma natural: JAVA CORE → JPA HIBERNATE → SPRING → SPRING BOOT → CLEAN CODE SOLID → TOOLS.
- [x] Optimizar `DataLoader`: reducir consultas de inicio con `@BatchSize` y `saveAll`.
- [x] Corregir que el sidebar no se renderizaba en páginas de concepto, subconcepto y búsqueda.

### Fase 4: Preparación para producción

- [x] Crear `Dockerfile` multi-etapa para Render.
- [x] Crear `render.yaml` con configuración de despliegue.
- [x] Añadir `README.md` con instrucciones de desarrollo y despliegue.
- [x] Añadir Spring Boot Actuator y endpoint `/actuator/health`.
- [x] Configurar health check de Render en `/actuator/health`.
- [x] Corregir `Dockerfile` para evitar copiar múltiples jars.
- [x] Desplegar en Render con base de datos Neon.

---

## Tareas pendientes

### Alta prioridad

- [x] Verificar que la aplicación desplegada responde correctamente en producción.
- [x] Comprobar que todos los conceptos y subconceptos se cargan en la URL de Render.
- [ ] Redesplegar en Render el último commit (`479dc9f`) para que el sidebar aparezca también en páginas de concepto/subconcepto/búsqueda.
- [ ] Revisar logs de Render en busca de errores o advertencias.

### Media prioridad

- [x] Añadir botón "Copiar" en los bloques de código.
- [ ] Añadir tests unitarios para `ConceptService`.
- [ ] Añadir tests de integración para controllers con `@WebMvcTest`.
- [ ] Mejorar visualización de código en móvil.
- [ ] Añadir modo oscuro / toggle de tema.
- [ ] Mejorar el buscador para incluir también respuestas de Q&A.

### Baja prioridad

- [ ] Añadir funcionalidad de "marcar como favorito" o "leído".
- [ ] Generar sitemap.xml para SEO.
- [ ] Añadir metadatos Open Graph en las páginas.
- [ ] Implementar paginación en el mapa si el contenido sigue creciendo.
- [ ] Considerar internacionalización (i18n) para contenido en inglés.

---

## Notas técnicas

- **Java 25**: la app compila a Java 21 pero puede ejecutarse en Java 25. Spring Boot 3.3.5 solo certifica hasta Java 23, por lo que aparecen advertencias.
- **Lombok**: no se utiliza por incompatibilidad con Java 25.
- **Base de datos**: H2 en desarrollo local, PostgreSQL (Neon) en producción.
- **Seguridad**: no hay autenticación; la app es pública.
- **Variables de entorno en producción**: `NEON_URL`, `NEON_USER`, `NEON_PASSWORD`, `SPRING_PROFILES_ACTIVE=neon`.

## Bugs corregidos recientemente

- **Sidebar vacío en conceptos/subconceptos/búsqueda**: el `blockTree` solo se añadía al modelo en `/map`. Solución: añadir `conceptService.getBlockTree()` en los métodos `conceptDetail`, `subConceptDetail` y `search` de `ConceptController`. Corrección en commit `479dc9f`.

---

## Cómo actualizar este fichero

Cuando se complete una tarea, moverla de "Pendientes" a "Completadas" indicando la fase correspondiente. Cuando surja una nueva tarea, añadirla a "Pendientes" con su prioridad.
