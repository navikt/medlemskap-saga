package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold

data class OppholdUtenforNorge(
    val id: String = "",
    val sporsmalstekst: String? = "",
    val svar: Boolean = false,
    val oppholdUtenforNorge: List<Opphold> = emptyList()
) {
    fun oppholdUtenforNorgeOppgitt(): Boolean = svar

    fun oppgittOppholdUtenforNorgeLand(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.land
            ?: ""

    fun oppholdUtenforNorgePeriodeFom(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun oppholdUtenforNorgePeriodeTom(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.tom
            ?: ""

    fun oppholdUtenforNorgeAntallPerioderOppgitt(): Int = oppholdUtenforNorge.size

    fun oppholdUtenforNorgeGrunn(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.grunn
            ?: ""
}