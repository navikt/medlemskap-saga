package no.nav.medlemskap.saga.domain.datagrunnlag

data class OppholdUtenforEOSTag(
    val oppholdUtenforEØS: Boolean,
    val oppholdUtenforEØSLand: String,
    val oppholdUtenforEØSFom: String,
    val oppholdUtenforEØSTom: String,
    val oppholdUtenforEØSAntallPerioder: Int,
    val oppholdUtenforEØSGrunn: String,
)
