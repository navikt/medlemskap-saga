package no.nav.medlemskap.saga.domain

data class ArbeidUtenforNorge(
    val id: String,
    val arbeidsgiver:String,
    val land:String,
    val perioder: Periode
)