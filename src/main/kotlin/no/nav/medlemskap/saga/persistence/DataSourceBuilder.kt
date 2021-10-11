package no.nav.medlemskap.saga.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import javax.sql.DataSource

class DataSourceBuilder(env: Map<String, String>) {
    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = env["DB_JDBC_URL"] ?: String.format(
            "jdbc:postgresql://%s:%s/%s%s",
            requireNotNull(env["DB_HOST"]) { "database host must be set if jdbc url is not provided" },
            requireNotNull(env["DB_PORT"]) { "database port must be set if jdbc url is not provided" },
            requireNotNull(env["DB_DATABASE"]) { "database name must be set if jdbc url is not provided" },
            env["DB_USERNAME"]?.let { "?user=$it" } ?: "")

        env["DB_USERNAME"]?.let { this.username = it }
        env["DB_PASSWORD"]?.let { this.password = it }
        username=this.username
        password=this.password
        maximumPoolSize = 3
        minimumIdle = 1
        idleTimeout = 10001
        connectionTimeout = 1000
        maxLifetime = 30001
    }

    init {
        if (!env.containsKey("DB_JDBC_URL")) {
            checkNotNull(env["DB_USERNAME"]) { "username must be set when vault is disabled" }
            checkNotNull(env["DB_PASSWORD"]) { "password must be set when vault is disabled" }
        }
    }

    fun getDataSource() = HikariDataSource(hikariConfig)


    fun migrate() =getDataSource().use {
        try {
            runMigration(it)
        }
        catch (t:Throwable){
            //TODO: Logging
        }

    }


    private fun runMigration(dataSource: DataSource) =
        Flyway.configure()
            .dataSource(dataSource)
            .load()
            .migrate()

}