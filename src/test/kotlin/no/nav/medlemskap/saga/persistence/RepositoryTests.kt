package no.nav.medlemskap.saga.persistence

import org.junit.ClassRule
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import java.util.logging.Level
import java.util.logging.LogManager
import org.junit.jupiter.api.Assertions.assertNotNull

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
            //.withInitScript("database/INIT.sql")
    }

    @Test
    fun `lagre medlemskap vurdering`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        //var env:Map<String,String> = mapOf()
        //val ds = DataSourceBuilder(env).getDataSource()
        val ds = getDataSource(postgresqlContainer)
        postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        //postgresqlContainer.withUrlParam("user", postgresqlContainer.username)
        //postgresqlContainer.withUrlParam("password", postgresqlContainer.password)
        val dsb = DataSourceBuilder(mapOf("DB_JDBC_URL" to postgresqlContainer.jdbcUrl))
        dsb.migrate();
        val ds2 = dsb.getDataSource()
        val repo:MedlemskapVurdertRepository = PostgressMedlemskapVurdertRepository(dsb.getDataSource())
        repo.lagreVurdering(UUID.randomUUID().toString(),Date(),fileContent)
        assertNotNull("complete")

    }
}