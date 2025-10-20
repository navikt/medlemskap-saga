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
import no.nav.medlemskap.saga.utled_vurderingstagger.GenererExcelDokument
import no.nav.medlemskap.saga.utled_vurderingstagger.VurderingForAnalyseService

fun Routing.uttrekkRoute(service: VurderingForAnalyseService) {

    route("/hentUttrekk") {
        authenticate("azureAuth") {
            get("/{aarMaaned}") {
                val aarMaanedParam =call.parameters["aarMaaned"]!!
                val uttrekk = service.hentVurderingerForAnalyse(aarMaanedParam)
                val excelBytes = GenererExcelDokument().generer(uttrekk)
                call.response.header(HttpHeaders.ContentDisposition, "attachment; filename=\"lovme.xlsx\"")
                call.respondBytes(excelBytes, ContentType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            }
        }
    }
}