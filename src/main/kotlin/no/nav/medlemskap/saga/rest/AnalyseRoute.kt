package no.nav.medlemskap.saga.rest

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.medlemskap.saga.service.AnalyseService
import java.io.OutputStream

fun Routing.analyseRoute(service: AnalyseService, storage: Storage) {

    val logger = mu.KotlinLogging.logger("AnalyseRoute")

    route("/analyse") {

        route("/hentUttrekk") {
            authenticate("azureAuth") {
                post("/{aarMaaned}") {
                    val årMånedParam = call.parameters["aarMaaned"]!!

                    logger.info("Mottatt forespørsel om uttrekk for periode: $årMånedParam")

                    try {
                        val år = årMånedParam.substringBefore("-")

                        val bucketNavn = "medlemskap-saga-vurderinger"
                        val objectName = "$år/uttrekk-$årMånedParam.csv"

                        val blobInfo = BlobInfo.newBuilder(bucketNavn, objectName)
                            .setContentType("text/csv; charset=utf-8")
                            .build()

                        storage.writer(blobInfo).use { writer ->
                            service.hentOgSkrivFilTilCsv(årMånedParam, writer as OutputStream)
                        }

                        logger.info("Uttrekk lagret i GCS: gs://$bucketNavn/$objectName")

                        call.respond(
                            HttpStatusCode.OK,
                            "Uttrekk lagret i GCS med navn $objectName"
                        )
                    } catch (e: Exception) {
                        logger.error(e) { "Feil ved generering av CSV for $årMånedParam" }
                        call.respond(HttpStatusCode.InternalServerError, "Feil ved generering av CSV")
                    }
                }
            }
        }
    }
}