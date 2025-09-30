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

    fun utførtArbeidUtenforNorgeFørsteOppgitteFom(): String =
        arbeidUtenforNorge
            .firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.fom
            ?: ""

    fun utførtArbeidUtenforNorgeFørsteOppgitteTom(): String =
        arbeidUtenforNorge.firstOrNull()
            ?.perioder
            ?.firstOrNull()
            ?.tom
            ?: ""

    fun utførtArbeidUtenforNorgeAntallPerioderOppgitt(): Int =
        arbeidUtenforNorge.size
}