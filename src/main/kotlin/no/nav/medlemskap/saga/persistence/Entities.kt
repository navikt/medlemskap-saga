package no.nav.medlemskap.saga.persistence

import no.nav.medlemskap.saga.rest.objectMapper
import java.time.LocalDate

import java.util.*

data class VurderingDao(val id: String, val soknadId: String, val date: Date, val json: String)
data class Periode(val fom: LocalDate, val tom: LocalDate)

fun Periode.begynnerIPerioden(periode: Periode):Boolean{
    return  (
            (fom.isEqual(periode.fom)) ||
            (fom.isAfter(periode.fom) && fom.isBefore(periode.tom))
            )
}

fun VurderingDao.fnr():String {
    return objectMapper.readTree(json).get("datagrunnlag").get("fnr").asText()
}
fun VurderingDao.periode():Periode {
    val  periode = objectMapper.readTree(json).get("datagrunnlag").get("periode")
    return Periode(LocalDate.parse(periode.get("fom").asText()),LocalDate.parse(periode.get("tom").asText()));

}

