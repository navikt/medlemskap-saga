package no.nav.medlemskap.saga.persistence

import com.fasterxml.jackson.databind.JsonNode
import no.nav.medlemskap.saga.rest.objectMapper
import java.time.LocalDate

import java.util.*

data class VurderingDao(val id: String, val soknadId: String, val date: Date, val json: String)
data class VurderingDao2(val id: String, val soknadId: String, val date: Date, val json: JsonNode)

data class Periode(val fom: LocalDate, val tom: LocalDate) {

}

fun Periode.begynnerIPerioden(periode: Periode):Boolean{
    return  (
            (fom.isEqual(periode.fom)) ||
            (fom.isAfter(periode.fom) && fom.isBefore(periode.tom))
            )
}
fun Periode.erMindreEnDagerFørPeriodeStarter(dager: Int, periode: Periode): Boolean {
    return fom.minusDays(dager.toLong()).isBefore(periode.fom) && ( tom.isAfter(periode.tom) || tom.isEqual(periode.tom))
}
fun Periode.erInnenforEllerSammePeriodeMedDagerDiffFør(dager:Int, periode: Periode) : Boolean{
        return fom.isAfter(periode.fom.minusDays(dager.toLong())) &&
                (tom.isBefore(periode.tom) || tom.isEqual(periode.tom))
}
fun Periode.erdirektePaaFolgende(periode: Periode) : Boolean{
    return fom.isEqual(periode.tom.plusDays(1))
}

fun VurderingDao.fnr():String {
    return objectMapper.readTree(json).get("datagrunnlag").get("fnr").asText()
}
fun VurderingDao.periode():Periode {
    val  periode = objectMapper.readTree(json).get("datagrunnlag").get("periode")
    return Periode(LocalDate.parse(periode.get("fom").asText()),LocalDate.parse(periode.get("tom").asText()));

}

