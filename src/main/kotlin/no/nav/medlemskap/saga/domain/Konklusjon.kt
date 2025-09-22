package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class Konklusjon(
    val hvem: String = "SP6000",
    val dato:LocalDate,
    val status: Svar,
    val avklaringsListe: List<Avklaring> = emptyList()
)