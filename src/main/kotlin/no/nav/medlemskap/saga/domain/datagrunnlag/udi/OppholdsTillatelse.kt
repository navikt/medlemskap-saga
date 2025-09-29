package no.nav.medlemskap.saga.domain.datagrunnlag.udi

data class OppholdsTillatelse(
    val gjeldendeOppholdsstatus: GjeldendeOppholdsstatus?
) {
    fun hentOppholdstillatelseUDIFom(): String =
        gjeldendeOppholdsstatus
            ?.oppholdstillatelsePaSammeVilkar
            ?.periode
            ?.fom
            ?.toString()
            ?: ""

    fun hentOppholdstillatelseUDITom(): String {
        return gjeldendeOppholdsstatus
            ?.oppholdstillatelsePaSammeVilkar
            ?.periode
            ?.tom
            ?.toString()
            ?: ""
    }

    fun hentOppholdstillatelseUDIType(): String {
        return gjeldendeOppholdsstatus
            ?.oppholdstillatelsePaSammeVilkar
            ?.type
            ?: ""
    }
}