package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import java.time.LocalDate

data class OppholdstillatelseOppgitt(
    val id: String = "",
    val sporsmalstekst: String? = "",
    val svar: Boolean = false,
    val vedtaksdato: LocalDate = LocalDate.MAX,
    val vedtaksTypePermanent: Boolean = false,
    val perioder: List<Periode> = mutableListOf()
) {
    fun oppholdstillatelseOppgitt(): Boolean = svar

    fun oppholdstillatelseOppgittFom(): String =
        perioder
            .firstOrNull()
            ?.fom
            ?: ""

    fun oppholdstillatelseOppgittTom(): String =
        perioder
            .firstOrNull()
            ?.tom
            ?: ""

    fun oppholdstillatelseAntallPerioder(): Int = perioder.size
}