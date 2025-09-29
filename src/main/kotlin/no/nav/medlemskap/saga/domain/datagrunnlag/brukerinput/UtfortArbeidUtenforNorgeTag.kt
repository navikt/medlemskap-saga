package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class UtfortArbeidUtenforNorgeTag(
    val utførtArbeidUtenforNorge: Boolean,
    val utførtArbeidUtenforNorgeLand: String,
    val utførtArbeidUtenforNorgeFom: String,
    val utførtArbeidUtenforNorgeTom: String,
    val utførtArbeidUtenforNorgeAntallPerioder: Int
)