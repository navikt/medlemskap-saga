package no.nav.medlemskap.saga.domain

data class Brukerinput(
    val arbeidUtenforNorge: Boolean,
    val oppholdstilatelse: Oppholdstillatelse? = null,
    val utfortAarbeidUtenforNorge: UtfortArbeidUtenforNorge? = null,
    val oppholdUtenforEos: OppholdUtenforEos? = null,
    val oppholdUtenforNorge: OppholdUtenforNorge? = null
) {

    fun hentUtførtArbeidUtenforNorge(): UtfortArbeidUtenforNorge {
        return this.utfortAarbeidUtenforNorge ?: UtfortArbeidUtenforNorge(
            id = "",
            sporsmalstekst = "",
            svar = false,
            arbeidUtenforNorge = emptyList()
        )
    }

}
