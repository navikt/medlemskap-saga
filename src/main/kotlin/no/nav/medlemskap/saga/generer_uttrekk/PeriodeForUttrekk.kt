package no.nav.medlemskap.saga.generer_uttrekk

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object PeriodeForUttrekk {

    fun finnPeriode(parameter: String): Pair<LocalDate, LocalDate> {
        val formatter = DateTimeFormatter.ofPattern("yyyyMM")
        val årOgMåned = YearMonth.parse(parameter, formatter)
        val førsteDag = årOgMåned.atDay(1)
        val sisteDag = årOgMåned.atEndOfMonth()

        return førsteDag to sisteDag
    }
}