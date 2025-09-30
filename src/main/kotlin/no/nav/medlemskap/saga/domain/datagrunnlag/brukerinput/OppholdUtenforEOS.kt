package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold

data class OppholdUtenforEOS(
    val id: String = "",
    val sporsmalstekst: String? = "",
    val svar: Boolean = false,
    val oppholdUtenforEOS: List<Opphold> = emptyList()
) {
    fun oppholdUtenforEØSOppgitt(): Boolean = svar

    fun ppholdUtenforEØSFørsteOppgitteLand(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.land
            ?: ""

    fun oppholdUtenforEØSFørsteOppgitteFom(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun oppholdUtenforEØSFørsteOppgitteTom(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.tom
            ?: ""

    fun oppholdUtenforEØSAntallPerioderOppgitt(): Int = oppholdUtenforEOS.size

    fun oppholdUtenforEØSFørsteOppgitteGrunn(): String =
        oppholdUtenforEOS
            .firstOrNull()
            ?.grunn
            ?: ""

}