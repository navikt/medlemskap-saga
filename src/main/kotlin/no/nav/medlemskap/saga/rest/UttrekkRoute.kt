package no.nav.medlemskap.saga.rest

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Routing.uttrekkRoute() {

    route("/hentUttrekk") {
        authenticate("azureAuth") {
            get("/{aarMaaned}") {
                val aarMaanedParam =call.parameters["aarMaaned"]!!

                //val uttrekkService = uttrekkService.lagUttrekkForPeriode(maanedParam)

                call.respond(HttpStatusCode.OK, "Dette skal være endepunktet for hentUttrekk for parameter: $aarMaanedParam")
            }
        }
    }
}