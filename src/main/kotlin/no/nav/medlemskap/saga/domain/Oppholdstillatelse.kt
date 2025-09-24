package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class Oppholdstillatelse(
    val id: String,
    val sporsmalstekst: String?,
    val svar:Boolean,
    val vedtaksdato: LocalDate,
    val vedtaksTypePermanent:Boolean,
    val perioder:List<Periode> = mutableListOf()
)