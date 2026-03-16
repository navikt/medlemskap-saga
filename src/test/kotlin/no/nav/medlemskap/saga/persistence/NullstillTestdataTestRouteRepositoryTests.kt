package no.nav.medlemskap.saga.persistence

import no.nav.medlemskap.saga.rest.objectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.util.*
import java.util.logging.Level
import java.util.logging.LogManager

@org.testcontainers.junit.jupiter.Testcontainers
class NullstillTestdataTestRouteRepositoryTests : AbstractContainerDatabaseTest() {
    init {
        LogManager.getLogManager().getLogger("").level = Level.OFF
    }

    companion object {
        @Container
        private val postgresqlContainer = MyPostgreSQLContainer("postgres:14")
            .withDatabaseName("medlemskap")
            .withUsername("postgres")
            .withPassword("test")
    }

    @Test
    fun `slettVurderingerForFnr sletter kun rader med gitt fnr`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering_UAVKLART.json").readText(Charsets.UTF_8)
        val ytelse = runCatching { objectMapper.readTree(fileContent).get("datagrunnlag").get("ytelse").asText() }.getOrElse { "UKJENT" }
        val fnr = "23089039444"

        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_JDBC_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate()
        val repo = PostgresMedlemskapVurdertRepository(dsb.getDataSource())

        repo.lagreVurdering(UUID.randomUUID().toString(), Date(), fileContent, ytelse)
        repo.lagreVurdering(UUID.randomUUID().toString(), Date(), fileContent, ytelse)

        val førSletting = repo.finnVurderingMedFnr(fnr)
        assertTrue(førSletting.size >= 2, "Skal ha minst 2 vurderinger før sletting")

        val slettet = repo.slettVurderingerForFnr(fnr)
        assertTrue(slettet >= 2, "Skal ha slettet minst 2 rader")

        val etterSletting = repo.finnVurderingMedFnr(fnr)
        assertEquals(0, etterSletting.size, "Skal ikke ha noen vurderinger etter sletting")
    }
}
