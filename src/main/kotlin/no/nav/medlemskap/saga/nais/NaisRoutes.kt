package no.nav.medlemskap.saga.nais

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.prometheus.client.exporter.common.TextFormat
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Job
import no.nav.medlemskap.saga.lytter.Metrics

fun Routing.naisRoutes(
    consumeJob: Job,
) {

    get("/isAlive") {
        if (consumeJob.isActive) {
            call.respondText("Alive!", ContentType.Text.Plain, HttpStatusCode.OK)
        } else {
            call.respondText("Not alive :(", ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    get("/isReady") {
        call.respondText("Ready!", ContentType.Text.Plain, HttpStatusCode.OK)
    }
    get("/metrics") {
        call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
            writeMetrics004(this, Metrics.registry)
        }
    }
}