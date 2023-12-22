package no.nav.medlemskap.saga.rest.security

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

enum class Roles {
    CAN_WRITE,
    CAN_READ

}

 fun haveAccess(call: ApplicationCall, canWrite: Roles):Boolean {

    val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
    if (!callerPrincipal.getRoles().contains(canWrite)){
        return false
    }
    return true

}

fun JWTPrincipal.getRoles():Set<Roles>{
    var groups:Array<String> = emptyArray()
    try{
     groups = payload.getClaim("groups").asArray(String::class.java)}
    catch (t:Throwable){

    }
    return groups.map { findRolesToGroup(it) }.toSet()

}
fun findRolesToGroup(group:String):Roles{

    return clientIdToYRolesMap.getOrDefault(group,Roles.CAN_READ)
}

val clientIdToYRolesMap: Map<String, Roles> = hashMapOf(
    "611669fa-de5c-4dc2-a0e8-6041a019992a" to Roles.CAN_WRITE, // 0000-AZ-speil-brukere - PROD
    "f787f900-6697-440d-a086-d5bb56e26a9c" to Roles.CAN_WRITE, // tbd - PROD
    "c21e58b6-d838-4cb0-919a-398cd40117e3" to Roles.CAN_WRITE, // 0000-AZ-speil-kode7 - PROD
    "59f26eef-0a4f-4038-bf46-3a5b2f252155" to Roles.CAN_WRITE, // 0000-GA-SYK-BESLUTTER - PROD
    "c811e9f4-26a6-43f9-b930-8c83365683a9" to Roles.CAN_WRITE, // DEV- FLEX
    "317665ad-1402-458e-b8e6-9cb50afc1252" to Roles.CAN_WRITE, //  DEV - speil-brukere-dev
    "dec3ee50-b683-4644-9507-520e8f054ac2" to Roles.CAN_WRITE // DEV Trygdeetaten test bruker for Lovme
)
