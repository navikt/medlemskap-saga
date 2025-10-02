package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import java.time.LocalDate

data class OppholdsTillatelse(
    val gjeldendeOppholdsstatus: GjeldendeOppholdsstatus?
) {
    fun hentOppholdstillatelseUDIFom(): LocalDate? =
        gjeldendeOppholdsstatus
            ?.oppholdstillatelsePaSammeVilkar
            ?.periode
            ?.fom

    fun hentOppholdstillatelseUDITom(): LocalDate? {
        return gjeldendeOppholdsstatus
            ?.oppholdstillatelsePaSammeVilkar
            ?.periode
            ?.tom
    }

    fun hentOppholdstillatelseUDIType(): String {
        return gjeldendeOppholdsstatus
            ?.oppholdstillatelsePaSammeVilkar
            ?.type
            ?: ""
    }
}