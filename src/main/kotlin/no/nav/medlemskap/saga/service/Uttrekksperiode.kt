package no.nav.medlemskap.saga.service

import java.time.LocalDate
import java.time.YearMonth

class Uttrekksperiode(årOgMåned: String) {

    val førsteDag: LocalDate
    val sisteDag: LocalDate

    init {
        val årOgMånedSplitt = årOgMåned.split("-")
        val årOgMåned = YearMonth.of(årOgMånedSplitt[0].toInt(), årOgMånedSplitt[1].toInt())

        førsteDag = årOgMåned.atDay(1)
        sisteDag = årOgMåned.atEndOfMonth()

    }
}