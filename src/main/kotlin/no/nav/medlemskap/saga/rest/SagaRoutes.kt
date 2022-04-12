package no.nav.medlemskap.saga.rest

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.persistence.Periode
import no.nav.medlemskap.saga.service.SagaService
import java.util.*

private val secureLogger = KotlinLogging.logger("tjenestekall")
private val logger = KotlinLogging.logger { }
fun Routing.sagaRoutes(service: SagaService) {
    get("/demo"){
        call.respondText("dette er en test",ContentType.Text.Plain, HttpStatusCode.OK)
    }
    route("/") {
        authenticate("azureAuth") {
            post("vurdering") {
                logger.info("kall autentisert, url : /vurdering")

                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                try {
                    val request = call.receive<Request>()
                    val vurderinger = service.finnAlleVurderingerForFnr(request.fnr)
                    secureLogger.info {
                        "Antall vurderinger found in db for ${request.fnr} is : ${vurderinger.size}"
                        kv("fnr", request.fnr)
                        kv("callId", callId)
                    }
                    val periode = Periode(request.periode.fom, request.periode.tom)
                    val vurdering = filterVurderinger(vurderinger, periode, request.fnr)
                    if (vurdering.isEmpty) {
                        secureLogger.info {
                            "cache miss for  ${request.fnr}"
                            kv("fnr", request.fnr)
                            kv("callId", callId)
                            kv("periode", periode)
                        }
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = vurdering.get()
                        secureLogger.info {
                            "cache hit for  ${request.fnr}"
                            kv("fnr", request.fnr)
                            kv("callId", callId)
                            kv("periode", periode)
                            kv("soknadId", response.soknadId)
                            kv("id", response.id)
                        }
                        call.respond(HttpStatusCode.OK, response.json)
                    }
                } catch (t: Throwable) {
                    call.respond(t.stackTrace)
                }
            }
            post("findVureringerByFnr") {
                logger.info("kall autentisert")

                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                try {
                    val request = call.receive<FnrRequest>()
                    val vurderinger = service.finnAlleVurderingerForFnr(request.fnr)
                    call.respond(vurderinger.map { mapToFnrResponse(it) })
                } catch (t: Throwable) {
                    call.respond(t.stackTrace)

                }
            }
        }
    }
}
