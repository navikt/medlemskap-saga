package no.nav.medlemskap.saga.domain.datagrunnlag

import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforEOS
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforNorge
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgitt
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorge

data class Brukerinput(
    val arbeidUtenforNorge: Boolean,
    val oppholdstilatelse: OppholdstillatelseOppgitt? = null,
    val utfortAarbeidUtenforNorge: UtfortArbeidUtenforNorge? = null,
    val oppholdUtenforEos: OppholdUtenforEOS? = null,
    val oppholdUtenforNorge: OppholdUtenforNorge? = null
) {
    fun hentUtførtArbeidUtenforNorge(): UtfortArbeidUtenforNorge {
        return this.utfortAarbeidUtenforNorge ?: UtfortArbeidUtenforNorge()
    }

    fun hentOppholdUtenforEØS(): OppholdUtenforEOS {
        return this.oppholdUtenforEos ?: OppholdUtenforEOS()
    }

    fun hentOppholdUtenforNorge(): OppholdUtenforNorge {
        return this.oppholdUtenforNorge ?: OppholdUtenforNorge()
    }

    fun hentOppholdstillatelseOppgitt(): OppholdstillatelseOppgitt {
        return this.oppholdstilatelse ?: OppholdstillatelseOppgitt()
    }
}