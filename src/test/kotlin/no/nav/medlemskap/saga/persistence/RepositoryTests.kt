package no.nav.medlemskap.saga.persistence

import org.junit.jupiter.api.Assertions.assertNotNull
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
        private val postgresqlContainer     = MyPostgreSQLContainer("postgres:12")
            .withDatabaseName("medlemskap")
            .withUsername("postgres")
            .withPassword("test")
    }

    @Test
    fun `lagre medlemskap vurdering`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val repo:MedlemskapVurdertRepository = PostgressMedlemskapVurdertRepository(dsb.getDataSource())
        repo.lagreVurdering(UUID.randomUUID().toString(),Date(),fileContent)
        assertNotNull("complete")

    }
    @Test
    fun `opprettDataSource fra enviroment`() {

        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val repo:MedlemskapVurdertRepository = PostgressMedlemskapVurdertRepository(dsb.getDataSource())

        assertNotNull("complete")

    }
}