package no.nav.medlemskap.saga.generer_uttrekk
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class UttrekkForPeriode(parameter: String) {

    val førsteDag: LocalDate
    val sisteDag: LocalDate

    init {
        val formatter = DateTimeFormatter.ofPattern("yyyyMM")
        val årOgMåned = YearMonth.parse(parameter, formatter)
        førsteDag = årOgMåned.atDay(1)
        sisteDag = årOgMåned.atEndOfMonth()
    }
}