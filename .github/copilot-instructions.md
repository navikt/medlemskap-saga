# Copilot Instructions for medlemskap-saga

## Build & Test

```bash
# Build fat JAR (shadowJar)
./gradlew shadowJar

# Run all tests (requires Docker for TestContainers)
./gradlew test

# Run a single test class
./gradlew test --tests "no.nav.medlemskap.saga.persistence.RepositoryTests"

# Run a single test method
./gradlew test --tests "no.nav.medlemskap.saga.persistence.RepositoryTests.lagre medlemskap vurdering"
```

JVM target is 21. Tests use JUnit 5 with TestContainers (PostgreSQL). Docker must be running.

## Architecture

This is a NAV microservice (Kotlin/Ktor 3, deployed on NAIS/GKE) that persists membership assessments (vurderinger). It has two input channels and two storage targets:

**Input:**
- Kafka consumer on topic `medlemskap.medlemskap-vurdert` — main ingestion path
- REST API for queries and manual operations

**Dual storage pattern — every vurdering is stored twice:**
1. `vurdering` table — raw JSON (JSONB), used for lookups by fnr/soknadId/period
2. `vurdering_analyse` table — normalized columns extracted from the JSON, used for CSV export and analysis

The Kafka flow: `Consumer.flow()` → `SagaService.handle()` → stores to both tables → commits offset.

**REST routes:**
- `SagaRoutes` — `/findVureringerByFnr`, `/flexvurdering`, `/vurdering` (query vurderinger)
- `AnalyseRoute` — `/analyse/hentUttrekk/{YYYYMM}` (generates CSV, uploads to GCS bucket)
- `NullstillTestdataTestRoute` — `/test/slett-vurdering` (dev-gcp only, deletes test data)
- `NaisRoutes` — `/isAlive`, `/isReady`, `/metrics`

**Key integrations:**
- PostgreSQL via HikariCP + kotliquery (queries) and raw JDBC (streaming CSV)
- Flyway for migrations (`src/main/resources/db/migration/`, V1–V14)
- Google Cloud Storage for CSV export files
- Azure AD JWT authentication on all business routes

## Conventions

- **Language:** The codebase and domain terminology is in Norwegian (vurdering = assessment, fnr = fødselsnummer/national ID, ytelse = benefit type, svar = answer). Commit messages and comments use Norwegian.
- **Entry point:** `no.nav.medlemskap.saga.ApplicationKt.main()` — starts Flyway migration, Kafka consumer flow in GlobalScope, then Ktor HTTP server on port 8080.
- **Configuration:** Environment variables accessed via `System.getenv()` typed as `Environment = Map<String, String>`. The `Configuration` class uses `natpryce/konfig` with `".configProperty()"` extension. Key env vars: `DB_HOST`, `DB_PORT`, `DB_DATABASE`, `DB_USERNAME`, `DB_PASSWORD`, `KAFKA_BROKERS`, `GCP_BUCKET_NAME`, `AZURE_APP_CLIENT_ID`.
- **Domain enums:** `Svar` (JA, NEI, UAVKLART), `Ytelse` (SYKEPENGER, BARNE_BRILLER, LOVME_GCP).
- **Period filtering:** `objectMapper.kt` contains complex period-matching logic with day tolerances (16/21 days). Understand `begynnerIPerioden()`, `erInnenforEllerSammePeriodeMedDagerDiffFør()`, and related functions before modifying query logic.
- **Database:** Migrations are sequential (V1–V14). The `vurdering` table stores fnr inside JSONB at path `json->'datagrunnlag'->>'fnr'` with a GIN index. Never add a plain fnr column — queries use the JSON path.
- **Testing:** Repository tests extend `AbstractContainerDatabaseTest` and use a shared PostgreSQL TestContainer. Test JSON fixtures live in `src/test/resources/`. There are no Kafka integration tests.
- **CSV streaming:** `VurderingForAnalyseRepository.hentOgSkrivVurderinger()` uses raw JDBC with `fetchSize=1000` for memory-efficient streaming. Don't convert this to kotliquery — it needs cursor-based streaming.
- **Tag extraction:** `utled_vurderingstagger/` package extracts structured tags from raw vurdering JSON for the analyse table. Each brukerinput field has a corresponding `*Tag` data class.
