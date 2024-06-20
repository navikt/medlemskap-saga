package no.nav.medlemskap.saga.rest

import com.fasterxml.jackson.databind.JsonNode
import no.nav.medlemskap.saga.persistence.VurderingDao
import no.nav.medlemskap.sykepenger.lytter.jakson.JaksonParser
import java.util.*

data class VurderingRespons(
    val dbId:String,
    val soknadId: String,
    val vurderingsDato: Date,
    val vurdering:JsonNode
)

data class PutRequest(
    val dbId:String,
    val soknadId: String,
    val vurderingsDato: Date,
    val vurdering:JsonNode
)


fun mapToLetmeResponse(vurdering: VurderingDao): VurderingRespons {
return VurderingRespons(
    dbId = vurdering.id,
    soknadId = vurdering.soknadId,
    vurderingsDato = vurdering.date,
    vurdering = JaksonParser().parse(vurdering.json)
)
}