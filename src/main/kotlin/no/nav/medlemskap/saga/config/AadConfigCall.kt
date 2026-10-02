package no.nav.medlemskap.saga.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache5.*
import io.ktor.client.request.get
import io.ktor.serialization.jackson.*
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.apache.hc.client5.http.impl.routing.SystemDefaultRoutePlanner
import java.net.ProxySelector

@JsonIgnoreProperties(ignoreUnknown = true)
data class AzureAdOpenIdConfiguration(
    @param:JsonProperty("jwks_uri")
    val jwksUri: String,
    @param:JsonProperty("issuer")
    val issuer: String,
    @param:JsonProperty("token_endpoint")
    val tokenEndpoint: String,
    @param:JsonProperty("authorization_endpoint")
    val authorizationEndpoint: String
)

private val logger = KotlinLogging.logger { }

fun getAadConfig(azureAdConfig: Configuration.AzureAd): AzureAdOpenIdConfiguration = runBlocking {
    apacheHttpClient.get("${azureAdConfig.authorityEndpoint}/${azureAdConfig.tenant}/v2.0/.well-known/openid-configuration")
        .body<AzureAdOpenIdConfiguration>().also { logger.info { it } }
}

internal val apacheHttpClient = HttpClient(Apache5) {
    this.expectSuccess = true
    install(ContentNegotiation) {
        register(
            io.ktor.http.ContentType.Application.Json,
            JacksonConverter(
                JsonMapper.builder()
                    .addModule(com.fasterxml.jackson.module.kotlin.KotlinModule.Builder().build())
                    .addModule(JavaTimeModule())
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .defaultPrettyPrinter(
                        DefaultPrettyPrinter().apply {
                            indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                            indentObjectsWith(DefaultIndenter("  ", "\n"))
                        }
                    )
                    .build()
            )
        )
    }

    engine {
        socketTimeout = 45000

        customizeClient { setRoutePlanner(SystemDefaultRoutePlanner(ProxySelector.getDefault())) }
    }
}
