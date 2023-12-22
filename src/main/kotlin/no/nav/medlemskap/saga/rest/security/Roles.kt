package no.nav.medlemskap.saga.rest.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

enum class Roles {
    CAN_WRITE,
    CAN_READ

}

fun validateAutorization(call: ApplicationCall, canWrite: Roles) {

    val callerPrincipal: JWTPrincipal = call.authentication.principal()!!

    val navIdent = callerPrincipal!!.payload.getClaim("NAVident").asString()

}

fun JWTPrincipal.getRoles():Set<Roles>{
    val roles = mutableListOf<Roles>()
    val groups:Array<String> = payload.getClaim("groups").asArray(String::class.java)
    return groups.map { findRolesToGroup(it) }.toSet()

}
fun findRolesToGroup(group:String):Roles{

    return Roles.CAN_WRITE
}

val CAN_WRITE = listOf(
    "611669fa-de5c-4dc2-a0e8-6041a019992a",
    "f787f900-6697-440d-a086-d5bb56e26a9c",
    "c811e9f4-26a6-43f9-b930-8c83365683a9",
    "59f26eef-0a4f-4038-bf46-3a5b2f252155",
    "317665ad-1402-458e-b8e6-9cb50afc1252")
