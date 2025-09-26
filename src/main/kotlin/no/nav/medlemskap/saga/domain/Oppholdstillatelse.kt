package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class Oppholdstillatelse(
    val id: String,
    val sporsmalstekst: String?,
    val svar: Boolean,
    val vedtaksdato: LocalDate,
    val vedtaksTypePermanent: Boolean,
    val perioder: List<Periode> = mutableListOf()
) {
    fun oppholdstillatelseOppgitt(): Boolean = svar

    fun oppholdstillatelseOppgittFom(): String = perioder.firstOrNull()?.fom ?: ""

    fun oppholdstillatelseOppgittTom(): String = perioder.firstOrNull()?.tom ?: ""

    fun oppholdstillatelseAntallPerioder(): Int = perioder.size
}