package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode

data class ArbeidUtenforNorge(
    val id: String,
    val arbeidsgiver: String,
    val land:String,
    val perioder: List<Periode>
)