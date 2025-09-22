package no.nav.medlemskap.saga.domain

data class Brukerinput(
    val arbeidUtenforNorge: Boolean,
    val oppholdstilatelse: Oppholdstilatelse?=null,
    val utfortAarbeidUtenforNorge: UtfortArbeidUtenforNorge?=null,
    val oppholdUtenforEos: OppholdUtenforEos?=null,
    val oppholdUtenforNorge: OppholdUtenforNorge?=null
)
