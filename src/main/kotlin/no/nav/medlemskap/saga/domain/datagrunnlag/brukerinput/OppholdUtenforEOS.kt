package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold

data class OppholdUtenforEOS(
    val id: String = "",
    val sporsmalstekst: String? = "",
    val svar: Boolean = false,
    val oppholdUtenforEOS: List<Opphold> = emptyList()
) {
    fun oppholdUtenforEØSOppgitt(): Boolean = svar

    fun oppgittOppholdUtenforEØSLand(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.land
            ?: ""

    fun oppholdUtenforEØSPeriodeFom(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun oppholdUtenforEØSPeriodeTom(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.tom
            ?: ""

    fun oppholdUtenforEØSAntallPerioderOppgitt(): Int = oppholdUtenforEOS.size

    fun oppholdUtenforEØSGrunn(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.grunn
            ?: ""

}