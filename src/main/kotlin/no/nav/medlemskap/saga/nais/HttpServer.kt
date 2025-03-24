package no.nav.medlemskap.saga.nais
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.metrics.micrometer.*

import org.slf4j.event.Level
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.exporter.common.TextFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import no.nav.medlemskap.saga.MDC_CALL_ID
import no.nav.medlemskap.saga.config.*
import no.nav.medlemskap.saga.config.JwtConfig.Companion.REALM
import no.nav.medlemskap.saga.lytter.Metrics
import no.nav.medlemskap.saga.persistence.DataSourceBuilder
import no.nav.medlemskap.saga.persistence.PostgresMedlemskapVurdertRepository
import no.nav.medlemskap.saga.rest.objectMapper
import no.nav.medlemskap.saga.rest.sagaRoutes
import no.nav.medlemskap.saga.service.SagaService
import org.apache.http.impl.conn.SystemDefaultRoutePlanner
import java.io.Writer
import java.net.ProxySelector
import java.util.*

fun createHttpServer(consumeJob: Job) = embeddedServer(Netty, port = 8080) {
    val useAuthentication: Boolean = true
    val configuration: Configuration = Configuration()
    val azureAdOpenIdConfiguration: AzureAdOpenIdConfiguration = getAadConfig(configuration.azureAd)
    val service: SagaService = SagaService(PostgresMedlemskapVurdertRepository(DataSourceBuilder(System.getenv()).getDataSource()))

        install(CallId) {
            header(MDC_CALL_ID)
            generate { UUID.randomUUID().toString() }
        }

        install(CallLogging) {
            level = Level.INFO
            callIdMdc(MDC_CALL_ID)
        }

        install(MicrometerMetrics) {
            registry = Metrics.registry
        }
        install(ContentNegotiation) {
            register(ContentType.Application.Json, JacksonConverter(objectMapper))
        }

        if (useAuthentication) {
            //logger.info { "Installerer authentication" }
            install(Authentication) {
                jwt("azureAuth") {
                    val jwtConfig = JwtConfig(configuration, azureAdOpenIdConfiguration)
                    realm = REALM
                    verifier(jwtConfig.jwkProvider, azureAdOpenIdConfiguration.issuer)
                    validate { credentials ->
                        jwtConfig.validate(credentials)
                    }
                }
            }
        } else {
            //logger.info { "Installerer IKKE authentication" }
        }

        routing {
            naisRoutes(consumeJob)
            sagaRoutes(service)
        }
    }


suspend fun writeMetrics004(writer: Writer, registry: PrometheusMeterRegistry) {
    withContext(Dispatchers.IO) {
        kotlin.runCatching {
            TextFormat.write004(writer, registry.prometheusRegistry.metricFamilySamples())
        }
    }
}


