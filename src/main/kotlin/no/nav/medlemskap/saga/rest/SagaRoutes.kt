package no.nav.medlemskap.saga.rest

import io.ktor.http.*
import mu.KotlinLogging
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.persistence.Periode
import no.nav.medlemskap.saga.service.SagaService
import java.util.*

private val logger = KotlinLogging.logger { }
private val secureLogger = KotlinLogging.logger("tjenestekall")
fun Routing.sagaRoutes(service: SagaService) {
    route("/person") {
        authenticate("azureAuth") {
            get{
                logger.info("kall autentisert, url : /findVureringerByFnr")

                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                //call.request.headers.entries().forEach{ logger.info(" ---->Header --> ${it.key} , ${it.value}")}
                val person_Id = call.request.header("Person-Id")
                if (person_Id.isNullOrBlank()){
                    call.respond(HttpStatusCode.BadRequest,"Person-Id ikke funnet i headers")
                }
                try{
                    var personIdentIDB = ""
                    //kjør oppslag mot tabell og se om det funnes nøkkel på person
                    //kjør oppslag mot PDL og hent ut FNR&DNR
                    //hvis hverken fnr eller dnr finne
                    // val vurderinger = service.finnAlleVurderingerForFnr(person_Id!!)
                    call.respond(PersonIDRespons(UUID.randomUUID().toString(),person_Id!!,"FNR"))
                }
                catch (t:Throwable){
                    call.respond(t.stackTrace)

                }
            }

            post{
                logger.info("kall autentisert, url : /Person/")

                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
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
    route("/person/{UUID}/vurderinger") {
        authenticate("azureAuth") {
            get{
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                try{
                    val uuid = call.parameters["UUID"]
                    if (uuid.isNullOrBlank()){
                        call.respond(HttpStatusCode.NotFound)
                    }
                    val personId = UUID.fromString(uuid)
                    //TODO: Bytt ut vurderingV2 med svar fra databasen med vurderiger på denne personen
                    val vurderinger:List<VurderingV2> = service.finnAlleVurderingerForPersonIdent(personId)
                   call.respond(PersonVurderingerResponse(personId, vurderinger))
                }
                catch (t:Throwable){
                    call.respond(t.stackTrace)

                }
            }
        }
    }
    route("/person/{UUID}/soknader") {
        authenticate("azureAuth") {
            get{
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                try{
                    val uuid = call.parameters["UUID"]
                    if (uuid.isNullOrBlank()){
                        call.respond(HttpStatusCode.NotFound)
                    }
                    val personId = UUID.fromString(uuid)
                    //TODO: Bytt ut vurderingV2 med svar fra databasen med vurderiger på denne personen
                    val soknader:List<Soknad> = service.finnAlleSoknaderForPersonIdent(personId)
                    call.respond(PersonSoknaderResponse(personId, soknader))
                }
                catch (t:Throwable){
                    call.respond(t.stackTrace)

                }
            }
        }
    }
    route("/vurdering") {
        authenticate("azureAuth") {
            post{
                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {} ", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                logger.info("kall autentisert, url : /vurdering",
                    kv("callId", callId))

                try {
                    val request = call.receive<Request>()
                    val vurderinger = service.finnAlleVurderingerForFnr(request.fnr)
                    secureLogger.info(
                        "Antall vurderinger found in db for ${request.fnr} is : ${vurderinger.size}",
                        kv("fnr", request.fnr),
                        kv("callId", callId)
                    )
                    val periode = Periode(request.periode.fom, request.periode.tom)
                    val vurdering = filterVurderinger(vurderinger, periode, request.fnr)
                    if (vurdering.isEmpty) {
                        secureLogger.info(
                            "cache miss for  ${request.fnr}",
                            kv("fnr", request.fnr),
                            kv("callId", callId),
                            kv("periode", periode)
                        )
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = vurdering.get()
                        secureLogger.info(
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

