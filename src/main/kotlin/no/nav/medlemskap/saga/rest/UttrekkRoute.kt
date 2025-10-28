package no.nav.medlemskap.saga.rest

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.server.auth.authenticate
import io.ktor.server.response.header
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import no.nav.medlemskap.saga.generer_uttrekk.GenererExcelDokument
import no.nav.medlemskap.saga.service.UttrekkService

fun Routing.uttrekkRoute(service: UttrekkService) {

    route("/hentUttrekk") {
        authenticate("azureAuth") {
            get("/{aarMaaned}") {
                val aarMaanedParam =call.parameters["aarMaaned"]!!
                val uttrekksdata = service.hentVurderingerForAnalyse(aarMaanedParam)
                val excelBytes = GenererExcelDokument.generer(uttrekksdata)
                call.response.header(HttpHeaders.ContentDisposition, "attachment; filename=\"Lovme.xlsx\"")
                call.respondBytes(excelBytes, ContentType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            }
        }
    }
}