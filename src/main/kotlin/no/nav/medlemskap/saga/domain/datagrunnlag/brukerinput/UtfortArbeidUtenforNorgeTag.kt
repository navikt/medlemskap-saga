package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class UtfortArbeidUtenforNorgeTag(
    val utførtArbeidUtenforNorge: Boolean,
    val utførtArbeidUtenforNorgeLand: String,
    val utførtArbeidUtenforNorgeFom: String,
    val utførtArbeidUtenforNorgeTom: String,
    val utførtArbeidUtenforNorgeAntallPerioder: Int
) {
    constructor(utførtArbeidUtenforNorge: UtfortArbeidUtenforNorge) : this(
        utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeOppgitt(),
        utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteLandetOppgitt(),
        utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteOppgitteFom(),
        utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteOppgitteTom(),
        utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeAntallPerioderOppgitt()
    )
}