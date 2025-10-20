package no.nav.medlemskap.saga.rest

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import no.nav.medlemskap.saga.utled_vurderingstagger.VurderingForAnalyseService

fun Routing.uttrekkRoute(service: VurderingForAnalyseService) {

    route("/hentUttrekk") {
        authenticate("azureAuth") {
            get("/{aarMaaned}") {
                val aarMaanedParam =call.parameters["aarMaaned"]!!
                val uttrekk = service.hentVurderingerForAnalyse(aarMaanedParam)

                call.respond(HttpStatusCode.OK, "Dette skal være endepunktet for hentUttrekk for parameter: $uttrekk")
            }
        }
    }
}