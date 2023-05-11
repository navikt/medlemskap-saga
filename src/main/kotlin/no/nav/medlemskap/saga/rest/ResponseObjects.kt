package no.nav.medlemskap.saga.rest

import java.util.*

data class PersonIDRespons(
    val personID:String,
    val inputId:String,
    val inputType:String
)

class PersonVurderingerResponse(
    val personID: UUID,
    val vurderinger:List<VurderingV2>
)

class PersonSoknaderResponse(
    val personID: UUID,
    val soknader:List<Soknad>
)

class VurderingV2(
    val soknadID : UUID,
    val vurderingsID:UUID
)

class Soknad(
    val soknadID : UUID
)

