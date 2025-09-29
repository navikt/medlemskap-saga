package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class OppholdUtenforNorgeTag(
    val oppholdUtenforNorge: Boolean,
    val oppholdUtenforNorgeLand: String,
    val oppholdUtenforNorgeFom: String,
    val oppholdUtenforNorgeTom: String,
    val oppholdUtenforNorgeAntallPerioder: Int,
    val oppholdUtenforNorgeGrunn: String
)
