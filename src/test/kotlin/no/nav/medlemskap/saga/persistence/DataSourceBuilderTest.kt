package no.nav.medlemskap.saga.persistence

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DataSourceBuilderTest {

    @Test
    fun `migrate kaster exception når databaseforbindelsen feiler`() {
        val builder = DataSourceBuilder(
            mapOf("DB_JDBC_URL" to "jdbc:postgresql://localhost:1/ukjent")
        )

        assertThrows(Exception::class.java) {
            builder.migrate()
        }
    }
}
