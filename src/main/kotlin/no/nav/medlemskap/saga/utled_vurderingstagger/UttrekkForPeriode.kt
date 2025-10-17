package no.nav.medlemskap.saga.utled_vurderingstagger
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class UttrekkForPeriode {

    data class Periode(val startDato: LocalDate, val sluttDato: LocalDate)

    fun hentUttrekkForDato(yyyymm: String): Periode {
        val formatter = DateTimeFormatter.ofPattern("yyyyMM")
        val aarOgMaaned = YearMonth.parse(yyyymm, formatter)
        val startDato = aarOgMaaned.atDay(1)
        val sluttDato = aarOgMaaned.atEndOfMonth()

        return Periode(startDato, sluttDato)
    }
}

    //val sporring = "SELECT * FROM vurdering_analyse WHERE date BETWEEN $startDato AND $sluttDato"
    // input: YYYYMM
    // Hva er startdato og sluttdato for måneden i det året?
    // Konvertere til spørring select * from vurdering_analyse WHERE date between x AND y
