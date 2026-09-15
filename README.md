# CoreApi

Servicio central del proyecto **TwitchSpamDetector**. Administra canales/streamers,
recibe los veredictos del Motor de Moderación, decide la acción final según la
configuración de cada canal y guarda el historial de mensajes marcados como spam.

Generado a partir del contrato `core-api-openapi.yaml` (contract-first, escrito a mano
siguiendo la misma estructura por capas de tus otros proyectos Spring Boot).

## Stack

- Java 21
- Spring Boot 4.1.1 (Spring Framework 7)
- Spring Data JPA + PostgreSQL
- Flyway (migraciones)
- Lombok
- Maven

## Estructura

```
controller/   endpoints REST (Channel, Verdict, Health)
entity/       entidades JPA
repository/   Spring Data JPA
request/      DTOs de entrada
response/     DTOs de salida
mapper/       entity <-> DTO
service/      lógica de negocio (+ impl/)
exception/    excepciones + manejador global -> ErrorResponse
```

## Cómo correrlo

Con Docker (levanta Postgres + el servicio):

```bash
docker compose up --build
```

El servicio queda expuesto en `http://localhost:8080/api/v1`, igual que en el contrato.

Localmente sin Docker (necesitas un Postgres propio y setear las variables
`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, o dejarlas en los
valores por defecto de `application.yml`):

```bash
./mvnw spring-boot:run        # Linux/macOS/Git Bash
.\mvnw.cmd spring-boot:run    # PowerShell / cmd
```

El proyecto trae Maven Wrapper (`mvnw` / `mvnw.cmd`), así que no necesitas tener
Maven instalado ni en el PATH: el script descarga Maven 3.9.16 la primera vez
que lo corres y lo reusa después. Úsalo en vez de `mvn` directo.

## Endpoints (según el contrato)

- `GET /channels` — lista canales
- `POST /channels` — registra un canal
- `GET /channels/{channelId}` — obtiene un canal
- `POST /channels/{channelId}/verdicts` — recibe un veredicto del Motor de Moderación, decide la acción y la guarda
- `GET /channels/{channelId}/verdicts` — historial paginado (para el dashboard)
- `GET /health` — estado del servicio

## Notas importantes / pendientes

0. **Fix aplicado: Flyway no corría.** En Spring Boot 4 agregar solo
   `flyway-core` ya no basta para que la auto-configuración de Flyway se
   active (se modularizó en starters separados). Sin el starter dedicado,
   Flyway se queda en silencio (ni siquiera loguea su banner de arranque) y
   Hibernate revienta al validar el schema porque las tablas nunca se
   crearon. El `pom.xml` ya usa `spring-boot-starter-flyway` +
   `flyway-database-postgresql`, que es lo correcto para Boot 4.
   Si ya habías corrido `docker compose up` antes con el pom viejo, borra el
   volumen de Postgres para partir de una base limpia:
   `docker compose down -v` y después `docker compose up --build`.

1. **Regla de decisión de acción es un placeholder.** El contrato dice que la acción
   final se decide "según la configuración de cada canal", pero el schema `Channel`
   todavía no tiene campos de configuración (p. ej. umbrales de `spamScore` por
   canal). Mientras tanto, `VerdictServiceImpl` usa una regla global fija por
   umbrales (`>=0.9` → timeout, `>=0.6` → delete, si no → warn, y `none` si
   `isSpam=false`). Está marcado con un `TODO` en el código. Cuando definas los
   campos de configuración por canal, hay que: agregarlos a `ChannelEntity` +
   una migración Flyway nueva, y mover la lógica de `decideAction(...)` a que
   los use.

2. **El objeto paginado de `GET /channels/{channelId}/verdicts`** está definido
   como un schema inline en tu YAML (sin nombre propio). Le puse el nombre
   `PagedVerdictRecordsResponse` en el código; si en algún momento retocas el
   contrato, te recomiendo nombrar ese schema como `PagedVerdictRecords` para
   que quede 1:1 y sea más fácil de generar/documentar a futuro.

3. **Sin seguridad** a propósito (igual que decidiste para este proyecto):
   no hay JWT ni validación de origen en estos endpoints todavía.

4. **Sin RabbitMQ aquí**: según lo que ya tienes definido, el Motor de
   Moderación llama a este servicio por HTTP directo (`POST
   /channels/{channelId}/verdicts`), así que este servicio no necesita listener
   de cola, solo el controller REST.

5. El test `CoreApiApplicationTests` usa `@SpringBootTest`, por lo que necesita
   una base de datos accesible (levanta `docker compose up postgres` antes de
   correr `mvn test`, o agrega Testcontainers más adelante).
