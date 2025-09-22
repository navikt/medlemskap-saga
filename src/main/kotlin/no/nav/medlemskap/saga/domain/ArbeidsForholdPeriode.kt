package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class ArbeidsForholdPeriode(
    val fom: LocalDate?,
    val tom: LocalDate?
)
