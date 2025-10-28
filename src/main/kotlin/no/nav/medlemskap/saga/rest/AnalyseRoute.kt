package no.nav.medlemskap.saga.rest

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import io.ktor.server.auth.authenticate
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import no.nav.medlemskap.saga.generer_uttrekk.GenererExcelDokument
import no.nav.medlemskap.saga.service.AnalyseService

fun Routing.analyseRoute(service: AnalyseService) {

    route("/analyse") {

        route("/hentUttrekk") {
            authenticate("azureAuth") {
                get("/{aarMaaned}") {
                    val årMånedParam =call.parameters["aarMaaned"]!!
                    val uttrekksdata = service.hentVurderingerForAnalyse(årMånedParam)

                    if (uttrekksdata.isEmpty()) {
                        call.respond(HttpStatusCode.NoContent) // 204 – ingen innhold
                        return@get
                    }

                    val excelBytes = GenererExcelDokument.generer(uttrekksdata)

                    call.response.header(HttpHeaders.ContentDisposition, "attachment; filename=\"uttrekk-${årMånedParam}.xlsx\"")
                    call.respondBytes(excelBytes, ContentType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                }
            }
        }
    }

}