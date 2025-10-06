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
    fun hentUtførtArbeidUtenforNorge(): UtfortArbeidUtenforNorge? {
        return this.utfortAarbeidUtenforNorge
    }

    fun hentOppholdUtenforEØS(): OppholdUtenforEOS? {
        return this.oppholdUtenforEos
    }

    fun hentOppholdUtenforNorge(): OppholdUtenforNorge? {
        return this.oppholdUtenforNorge
    }

    fun hentOppholdstillatelseOppgitt(): OppholdstillatelseOppgitt? {
        return this.oppholdstilatelse
    }
}