package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import java.time.LocalDate

data class UdiPeriode(
    val fom: LocalDate,
    val tom: LocalDate?
)