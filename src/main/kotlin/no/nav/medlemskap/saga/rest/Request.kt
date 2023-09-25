package no.nav.medlemskap.saga.rest

import java.time.LocalDate

data class Request(
    val fnr: String,
    val førsteDagForYtelse: LocalDate?,
    val periode: InputPeriode,
    val ytelse: Ytelse?,
)

data class InputPeriode(
    val fom: LocalDate,
    val tom: LocalDate
)
enum class Ytelse {
    SYKEPENGER,
    AAP,
}

data class FlexRequest(
    val sykepengesoknad_id: String,
    val fnr: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val ytelse: Ytelse = Ytelse.SYKEPENGER,
)
data class FlexVurderingRespons(
    val sykepengesoknad_id: String,
    val vurdering_id: String,
    val fnr: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val status:String
)

