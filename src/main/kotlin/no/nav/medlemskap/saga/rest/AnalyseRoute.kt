package no.nav.medlemskap.saga.rest

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.generer_uttrekk.ValiderParameter
import no.nav.medlemskap.saga.service.AnalyseService
import java.io.File
import java.io.FileOutputStream

fun Routing.analyseRoute(service: AnalyseService, storage: Storage) {

    val logger = mu.KotlinLogging.logger("AnalyseRoute")

    route("/analyse") {

        route("/hentUttrekk") {
            authenticate("azureAuth") {
                post("/{aarMaaned}") {
                    val årMånedParam = call.parameters["aarMaaned"]!!
                    ValiderParameter.validerParameter(årMånedParam)

                    logger.info("Mottatt forespørsel om uttrekk for periode: $årMånedParam")

                    val bucketNavn = System.getenv("GCP_BUCKET_NAME")
                    val år = årMånedParam.take(4)

                    val objectName = "$år/uttrekk-$årMånedParam.csv"

                    // Opprett midlertidig fil
                    val tempFile = File.createTempFile("uttrekk-$årMånedParam", ".csv")

                    try {
                        // Generer CSV direkte til fil
                        FileOutputStream(tempFile).use { fos ->
                            service.hentOgSkrivFilTilCsv(årMånedParam, fos)
                        }

                        // Lag BlobInfo
                        val blobInfo = BlobInfo.newBuilder(bucketNavn, objectName)
                            .setContentType("text/csv; charset=utf-8")
                            .build()

                        // Last opp fil til GCS
                        storage.create(blobInfo, tempFile.readBytes())

                        logger.info("Uttrekk lagret i GCS: gs://$bucketNavn/$objectName")

                        call.respond(
                            HttpStatusCode.OK,
                            "Uttrekk lagret i GCS med navn $objectName"
                        )
                    } catch (e: Exception) {
                        logger.error( "Feil ved generering av CSV for $årMånedParam",
                            kv("feilmelding", e)
                        )
                        call.respond(HttpStatusCode.InternalServerError, "Feil ved generering av CSV")
                    } finally {
                        // Slett midlertidig fil
                        tempFile.delete()
                    }
                }
            }
        }
    }
}