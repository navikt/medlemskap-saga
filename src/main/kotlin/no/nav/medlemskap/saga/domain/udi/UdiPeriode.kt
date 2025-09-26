package no.nav.medlemskap.saga.domain.udi

import java.time.LocalDate

data class UdiPeriode(
    val fom: LocalDate,
    val tom: LocalDate?
)