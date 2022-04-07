package no.nav.medlemskap.saga.rest

import io.ktor.application.call
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import mu.KotlinLogging
import java.util.*
import kotlin.text.get

private val secureLogger = KotlinLogging.logger("tjenestekall")
private val logger = KotlinLogging.logger { }
fun Routing.sagaRoutes() {
    route("/vurdering") {
        authenticate("azureAuth") {
            post{
                logger.info("kall autentisert")

                val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
                val azp = callerPrincipal.payload.getClaim("azp").asString()
                secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
                val callId = call.callId ?: UUID.randomUUID().toString()
                try{
                    val request = call.receive<Request>()
                    call.respond("soon to contain vurdering")
                }
                catch (t:Throwable){
                    call.respond(t.stackTrace)

                }
                call.respond("soon to contain vurdering")

            }
            get("{fnr}"){
                call.respond("soon to contain vurdering by fnr")
            }
        }
    }

}
