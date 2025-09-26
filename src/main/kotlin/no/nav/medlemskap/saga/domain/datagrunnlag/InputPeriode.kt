package no.nav.medlemskap.saga.domain.datagrunnlag

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class InputPeriode(
    val fom: LocalDate,
    val tom: LocalDate
) {
    fun antallDager(): Long {
        return ChronoUnit.DAYS.between(fom, tom) + 1
    }
}