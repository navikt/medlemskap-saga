package no.nav.medlemskap.saga.rest

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.persistence.MedlemskapVurdertRepository
import no.nav.medlemskap.saga.persistence.VurderingForAnalyseRepository
import org.slf4j.MarkerFactory

private val logger = KotlinLogging.logger { }
private val teamLogs = MarkerFactory.getMarker("TEAM_LOGS")

fun Routing.nullstillTestdataTestRoute(
    medlemskapVurdertRepository: MedlemskapVurdertRepository,
    vurderingForAnalyseRepository: VurderingForAnalyseRepository
) {
    route("/testdata") {
        authenticate("azureAuth") {
            post("/slett-vurdering") {
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                logger.info(teamLogs, "NullstillTestdataTestRoute: azp-claim i principal-token: {}", azp)

                try {
                    val request = call.receive<FnrRequest>()
                    val fnr = request.fnr

                    logger.info(teamLogs, "Sletter testdata for fnr", kv("fnr", fnr))

                    val slettetVurderinger = medlemskapVurdertRepository.slettVurderingerForFnr(fnr)
                    val slettetAnalyse = vurderingForAnalyseRepository.slettVurderingerForAnalyseForFnr(fnr)

                    logger.info(
                        teamLogs,
                        "Testdata er slettet for fnr: slettet $slettetVurderinger vurderinger og $slettetAnalyse analyse-rader",
                        kv("fnr", fnr),
                        kv("slettetVurderinger", slettetVurderinger),
                        kv("slettetAnalyse", slettetAnalyse)
                    )

                    call.respond(
                        HttpStatusCode.OK,
                        mapOf(
                            "fnr" to fnr,
                            "slettetVurderinger" to slettetVurderinger,
                            "slettetVurderingAnalyse" to slettetAnalyse
                        )
                    )
                } catch (t: Throwable) {
                    logger.error("Feil ved sletting av testdata", t)
                    call.respond(HttpStatusCode.InternalServerError, "Feil ved nullstilling av testdata: ${t.message}")
                }
            }
        }
    }
}
