package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class Avklaring(
    val regel_id:String,
    val avklaringstekst:String,
    val svar:String,
    val status:String,
    val beskrivelse:String?,
    val hvem:String,
    val tidspunkt:LocalDate
)