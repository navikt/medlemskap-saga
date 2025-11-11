package no.nav.medlemskap.saga.rest

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import io.ktor.server.auth.authenticate
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import no.nav.medlemskap.saga.generer_uttrekk.GenererCsvDokument
import no.nav.medlemskap.saga.service.AnalyseService
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun Routing.analyseRoute(service: AnalyseService) {

    val logger = mu.KotlinLogging.logger("AnalyseRoute")

    route("/analyse") {

        route("/hentUttrekk") {
            authenticate("azureAuth") {
                get("/{aarMaaned}") {
                    val årMånedParam = call.parameters["aarMaaned"]!!

                    logger.info("Mottatt forespørsel om uttrekk for periode: $årMånedParam")

                    try {
                        val file = File("/tmp/uttrekk-$årMånedParam.csv")

                        FileOutputStream(file).use { outputStream ->
                            service.hentOgSkrivFilTilCsv(årMånedParam, outputStream)
                        }

                        call.response.header(
                            HttpHeaders.ContentDisposition,
                            "attachment; filename=\"uttrekk-${årMånedParam}.csv\""
                        )
                        call.respondFile(file)

                    } catch (e: Exception) {
                        logger.error(e) { "Feil ved generering av CSV for $årMånedParam" }
                        call.respond(HttpStatusCode.InternalServerError, "Feil ved generering av CSV")
                    }
                }
            }
        }
    }

}