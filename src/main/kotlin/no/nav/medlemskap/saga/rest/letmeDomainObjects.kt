package no.nav.medlemskap.saga.rest

import com.fasterxml.jackson.databind.JsonNode
import no.nav.medlemskap.saga.persistence.VurderingDao
import no.nav.medlemskap.saga.jackson.JacksonParser
import java.util.*

data class VurderingRespons(
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
    vurdering = JacksonParser.parse(vurdering.json)
)
}