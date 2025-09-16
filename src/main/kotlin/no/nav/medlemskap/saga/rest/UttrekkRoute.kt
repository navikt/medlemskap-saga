package no.nav.medlemskap.saga.rest

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import mu.KotlinLogging
import no.nav.medlemskap.saga.service.SagaService
import no.nav.medlemskap.saga.service.UttrekkService
import org.slf4j.MarkerFactory
import java.time.YearMonth

private val logger = KotlinLogging.logger { }
private val teamLogs = MarkerFactory.getMarker("TEAM_LOGS")


fun Routing.uttrekkRoute(uttrekkService: UttrekkService) {

    route("/hentUttrekk") {
        authenticate("azureAuth") {
            get("/{maaned}") {
                val maanedParam = call.parameters["maaned"] ?: return@get call.respondText(
                    "Mangler path parameter 'maaned'",
                    status = HttpStatusCode.BadRequest
                )

                val år = maanedParam.split("-")[0]
                val måned = maanedParam.split("-")[1]

                call.respond(HttpStatusCode.OK, "Dette skal være endepunktet for hentUttrekk for måned $måned og år $år")
            }
        }
    }
}