package no.nav.medlemskap.saga.domain.datagrunnlag

import java.time.LocalDate

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

    fun hentOppholdUtenforEØS(): OppholdUtenforEos {
        return this.oppholdUtenforEos ?: OppholdUtenforEos(
            id = "",
            sporsmalstekst = "",
            svar = false,
            oppholdUtenforEOS = emptyList()
        )
    }

    fun hentOppholdUtenforNorge(): OppholdUtenforNorge {
        return this.oppholdUtenforNorge ?: OppholdUtenforNorge(
            id = "",
            sporsmalstekst = "",
            svar = false,
            oppholdUtenforNorge = emptyList()
        )
    }

    fun hentOppholdstillatelseOppgitt(): Oppholdstillatelse {
        return this.oppholdstilatelse ?: Oppholdstillatelse(
            id = "",
            sporsmalstekst = "",
            svar = false,
            vedtaksdato = LocalDate.MAX,
            vedtaksTypePermanent = false,
            perioder = emptyList()
        )
    }
}