package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class UtfortArbeidUtenforNorgeTag(
    val utførtArbeidUtenforNorge: Boolean,
    val utførtArbeidUtenforNorgeLand: String,
    val utførtArbeidUtenforNorgeFom: String,
    val utførtArbeidUtenforNorgeTom: String,
    val utførtArbeidUtenforNorgeAntallPerioder: Int
) {

    companion object {
        fun fra(utførtArbeidUtenforNorge: UtfortArbeidUtenforNorge?): UtfortArbeidUtenforNorgeTag? {
            if (utførtArbeidUtenforNorge == null) return null

            return UtfortArbeidUtenforNorgeTag(
                utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeOppgitt(),
                utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteLandetOppgitt(),
                utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteOppgitteFom(),
                utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteOppgitteTom(),
                utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeAntallPerioderOppgitt()
            )
        }
    }
}