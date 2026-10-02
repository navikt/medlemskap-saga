package no.nav.medlemskap.saga.nais

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.medlemskap.saga.lytter.Metrics

fun Routing.naisRoutes(isConsumerActive: () -> Boolean) {

    get("/isAlive") {
        if (isConsumerActive()) {
            call.respondText("Alive!", ContentType.Text.Plain, HttpStatusCode.OK)
        } else {
            call.respondText("Not alive :(", ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    get("/isReady") {
        call.respondText("Ready!", ContentType.Text.Plain, HttpStatusCode.OK)
    }
    get("/metrics") {
        call.respondText(Metrics.registry.scrape(), ContentType.parse("text/plain; version=0.0.4; charset=utf-8"))
    }
}