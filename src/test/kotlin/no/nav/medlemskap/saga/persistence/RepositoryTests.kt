package no.nav.medlemskap.saga.persistence

import no.nav.medlemskap.saga.rest.objectMapper
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.util.*
import java.util.logging.Level
import java.util.logging.LogManager


class MyPostgreSQLContainer(imageName: String) : PostgreSQLContainer<MyPostgreSQLContainer>(imageName)
@org.testcontainers.junit.jupiter.Testcontainers
class RepositoryTests : AbstractContainerDatabaseTest() {
    init {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").level = Level.OFF
    }
    companion object {
        // will be shared between test methods
        @Container
        private val postgresqlContainer     = MyPostgreSQLContainer("postgres:14")
            .withDatabaseName("medlemskap")
            .withUsername("postgres")
            .withPassword("test")
    }

    @Test
    fun `lagre medlemskap vurdering`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val ytelse = kotlin.runCatching { objectMapper.readTree(fileContent).get("datagrunnlag").get("ytelse").asText() }.getOrElse { "UKJENT" }
        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_JDBC_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val repo = PostgresMedlemskapVurdertRepository(dsb.getDataSource())
        repo.lagreVurdering(UUID.randomUUID().toString(),Date(),fileContent,ytelse)
        repo.lagreVurdering(UUID.randomUUID().toString(),Date(),fileContent,ytelse)
        repo.lagreVurdering(UUID.randomUUID().toString(),Date(),fileContent,ytelse)
        repo.lagreVurdering(UUID.randomUUID().toString(),Date(),fileContent,ytelse)
        assertNotNull("complete")
        val result = repo.hentVurderinger()
        assertTrue(result.size==4,"result set should contain 4 elements")

    }
    @Test
    fun `hente vurdering skal kunne returnere flere rader`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val ytelse = kotlin.runCatching { objectMapper.readTree(fileContent).get("datagrunnlag").get("ytelse").asText() }.getOrElse { "UKJENT" }
        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_JDBC_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val repo = PostgresMedlemskapVurdertRepository(dsb.getDataSource())
        val sykepengesoknadId=UUID.randomUUID().toString()
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        assertNotNull("complete")
        val result = repo.finnVurdering(sykepengesoknadId)
        assertTrue(result.size==4,"result set should contain 4 elements")
    }
    @Test
    fun `hente vurdering med fnr skal kunne returnere flere rader`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering_UAVKLART.json").readText(Charsets.UTF_8)
        val ytelse = kotlin.runCatching { objectMapper.readTree(fileContent).get("datagrunnlag").get("ytelse").asText() }.getOrElse { "UKJENT" }
        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_JDBC_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val repo = PostgresMedlemskapVurdertRepository(dsb.getDataSource())
        val sykepengesoknadId=UUID.randomUUID().toString()
        val fnr="23089039444"
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        repo.lagreVurdering(sykepengesoknadId,Date(),fileContent,ytelse)
        assertNotNull("complete")
        val result = repo.finnVurderingMedFnr(fnr)
        println(result.size)
        assertTrue(result.size==4,"result set should contain 4 elements")

    }
    @Test
    fun `opprettDataSource fra enviroment`() {

        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_JDBC_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val repo:MedlemskapVurdertRepository = PostgresMedlemskapVurdertRepository(dsb.getDataSource())

        assertNotNull("complete")

    }
}