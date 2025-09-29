package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class UtfortArbeidUtenforNorge(
    val id: String = "",
    val sporsmalstekst: String? = "",
    val svar: Boolean = false,
    val arbeidUtenforNorge: List<ArbeidUtenforNorge> = emptyList()
) {

    fun utførtArbeidUtenforNorgeOppgitt(): Boolean = svar

    fun utførtArbeidUtenforNorgeFørsteLandetOppgitt(): String =
        arbeidUtenforNorge
            .firstOrNull()
            ?.land
            ?: ""

    fun utførtArbeidUtenforNorgePeriodeFom(): String =
        arbeidUtenforNorge
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun utførtArbeidUtenforNorgePeriodeTom(): String =
        arbeidUtenforNorge.firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun utførtArbeidUtenforNorgeAntallPerioderOppgitt(): Int =
        arbeidUtenforNorge.size
}