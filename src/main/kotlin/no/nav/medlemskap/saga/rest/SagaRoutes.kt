package no.nav.medlemskap.saga.rest

import com.fasterxml.jackson.databind.JsonNode
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.persistence.Periode
import no.nav.medlemskap.saga.persistence.VurderingDao
import no.nav.medlemskap.saga.service.SagaService
import no.nav.medlemskap.sykepenger.lytter.jakson.JacksonParser
import org.slf4j.MarkerFactory
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger { }
private val teamLogs = MarkerFactory.getMarker("TEAM_LOGS")

fun Routing.sagaRoutes(service: SagaService) {
    route("/findVureringerByFnr") {
        authenticate("azureAuth") {
            post{
                logger.info("kall autentisert, url : /findVureringerByFnr")

                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                logger.info(teamLogs, "EvalueringRoute: azp-claim i principal-token: {}", azp)
                try{
                    val request = call.receive<FnrRequest>()
                    val vurderinger = service.finnAlleVurderingerForFnr(request.fnr)
                    call.respond(vurderinger.map { mapToFnrResponse(it) })
                }
                catch (t:Throwable){
                    call.respond(t.stackTrace)

                }
            }
        }
    }
    route("/flexvurdering") {
        authenticate("azureAuth") {
            post{
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                logger.info(teamLogs, "EvalueringRoute: azp-claim i principal-token: {} ", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                logger.info("kall autentisert, url : /flexvurdering",
                    kv("callId", callId))

                try {
                    val request = call.receive<FlexRequest>()
                    val vurderinger = service.finnAlleVurderingerForFnr(request.fnr)
                    logger.info(
                        teamLogs,
                        "Antall vurderinger found in db for ${request.fnr} is : ${vurderinger.size}",
                        kv("fnr", request.fnr),
                        kv("callId", callId)
                    )

                    val match = vurderinger.firstOrNull { it.soknadId==request.sykepengesoknad_id }
                    if (match!=null){
                        call.respond(HttpStatusCode.OK,mapToFlexVurderingsRespons(match))
                    }
                    val periode = Periode(request.fom, request.tom)
                    val vurdering = filterVurderinger(vurderinger, periode, request.fnr)
                    if (vurdering.isEmpty) {
                        logger.info(
                            teamLogs,
                            "flexvurdering cache miss for  ${request.fnr}, søknadID :  ${request.sykepengesoknad_id}",
                            kv("fnr", request.fnr),
                            kv("callId", callId),
                            kv("periode", periode)
                        )
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = vurdering.get()
                        logger.info(
                            teamLogs,
                            "flexvurdering cache hit for  ${request.fnr} søknadID :  ${request.sykepengesoknad_id}",
                            kv("fnr", request.fnr),
                            kv("callId", callId),
                            kv("periode", periode),
                            kv("soknadId", response.soknadId),
                            kv("id", response.id)
                        )
                        call.respond(HttpStatusCode.OK, mapToFlexVurderingsRespons(response))
                    }
                } catch (t: Throwable) {
                    call.respond(t.stackTrace)
                }
            }
        }
    }
    route("/vurdering") {
        authenticate("azureAuth") {
            get("/{soknadId}") {
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                logger.info(teamLogs, "EvalueringRoute: azp-claim i principal-token: {} ", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                logger.info("kall autentisert, url : /vurdering/{soknadId}",
                    kv("callId", callId))
                val soknadID = call.parameters["soknadId"]
                /*
                * Henter ut nødvendige parameter. kan evnt endres senere ved behov
                * */
                if (soknadID.isNullOrBlank()){
                    logger.warn { "bad request. Ingen soknadID oppgitt" }
                    call.respond(HttpStatusCode.BadRequest,"soknadId request parameter forventet")

                }
                else{
                    val vurderinger = service.medlemskapVurdertRepository.finnVurdering(soknadID)
                    val vurdering = vurderinger.sortedByDescending { it.id }.firstOrNull()
                    if (vurdering!=null){
                        logger.info { "vurdering funnet for soknadID $soknadID" }
                        call.respond(HttpStatusCode.OK,mapToLetmeResponse(vurdering))
                    }
                    else{
                        logger.warn { "ingen vurdering funnet for soknadID $soknadID" }
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
            post{
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                logger.info(teamLogs, "EvalueringRoute: azp-claim i principal-token: {} ", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                logger.info("kall autentisert, url : /vurdering",
                    kv("callId", callId))

                try {
                    val request = call.receive<Request>()
                    val vurderinger = service.finnAlleVurderingerForFnr(request.fnr)
                    logger.info(
                        teamLogs,
                        "Antall vurderinger found in db for ${request.fnr} is : ${vurderinger.size}",
                        kv("fnr", request.fnr),
                        kv("callId", callId)
                    )
                    val periode = Periode(request.periode.fom, request.periode.tom)
                    val vurdering = filterVurderinger(vurderinger, periode, request.fnr)
                    if (vurdering.isEmpty) {
                        logger.info(
                            teamLogs,
                            "cache miss for  ${request.fnr}",
                            kv("fnr", request.fnr),
                            kv("callId", callId),
                            kv("periode", periode)
                        )
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = vurdering.get()
                        logger.info(
                            teamLogs,
                            "cache hit for  ${request.fnr}",
                            kv("fnr", request.fnr),
                            kv("callId", callId),
                            kv("periode", periode),
                            kv("soknadId", response.soknadId),
                            kv("id", response.id)
                        )
                        call.respond(HttpStatusCode.OK, response.json)
                    }
                } catch (t: Throwable) {
                    call.respond(t.stackTrace)
                }
            }
        }
    }
}



fun mapToFlexVurderingsRespons(match: VurderingDao): FlexVurderingRespons {
    val jsonNode:JsonNode = JacksonParser().parse(match.json)
    return FlexVurderingRespons(
        sykepengesoknad_id = match.soknadId,
        vurdering_id = match.id,
        fnr = jsonNode.fnr(),
        fom = jsonNode.fom(),
        tom = jsonNode.tom(),
        status = jsonNode.statusKonklusjon())
}
fun JsonNode.status():String{
    return this.get("resultat").get("svar").asText()
}
fun JsonNode.statusKonklusjon():String{
    runCatching { this.get("konklusjon").get(0).get("status").asText() }
        .onSuccess { return it }
        .onFailure {
            logger.warn(
                teamLogs,
                "Gammel modell i respons for vurdering",
                kv("fnr", this.fnr()),
            )
            return status() }
    return ""

}
fun JsonNode.fom():LocalDate{
    val fom = this.get("datagrunnlag").get("periode").get("fom").asText()
    return LocalDate.parse(fom)
}
fun JsonNode.tom():LocalDate{
    val tom = this.get("datagrunnlag").get("periode").get("tom").asText()
    return LocalDate.parse(tom)
}
fun JsonNode.fnr():String{
    return this.get("datagrunnlag").get("fnr").asText()
}