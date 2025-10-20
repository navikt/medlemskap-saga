package no.nav.medlemskap.saga.utled_vurderingstagger
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

    //val sporring = "SELECT * FROM vurdering_analyse WHERE date BETWEEN $startDato AND $sluttDato"
    // input: YYYYMM
    // Hva er startdato og sluttdato for måneden i det året?
    // Konvertere til spørring select * from vurdering_analyse WHERE date between x AND y
