package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold

data class OppholdUtenforNorge(
    val id: String = "",
    val sporsmalstekst: String? = "",
    val svar: Boolean = false,
    val oppholdUtenforNorge: List<Opphold> = emptyList()
) {
    fun oppholdUtenforNorgeOppgitt(): Boolean = svar

    fun oppholdUtenforNorgeFørsteOppgitteLand(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.land
            ?: ""

    fun oppholdUtenforNorgeFørsteOppgitteFom(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun oppholdUtenforNorgeFørsteOppgitteTom(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.tom
            ?: ""

    fun oppholdUtenforNorgeAntallPerioderOppgitt(): Int = oppholdUtenforNorge.size

    fun oppholdUtenforNorgeFørsteOppgitteGrunn(): String =
        oppholdUtenforNorge
            .firstOrNull()
            ?.grunn
            ?: ""
}