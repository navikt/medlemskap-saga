package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import java.time.LocalDate

data class Oppholdstillatelse(
    val gjeldendeOppholdsstatus: GjeldendeOppholdsstatus?
) {
    private fun hentPeriode(): UdiPeriode? {
        val status = gjeldendeOppholdsstatus ?: return null
        return with(status) {
            oppholdstillatelsePaSammeVilkar?.periode
                ?: eosellerEFTAOpphold?.periode
        }
    }

    fun hentOppholdstillatelseUDIFom(): LocalDate? = hentPeriode()?.fom

    fun hentOppholdstillatelseUDITom(): LocalDate? = hentPeriode()?.tom

    fun hentOppholdstillatelseUDIType(): String {
        val status = gjeldendeOppholdsstatus ?: return ""
        return with(status) {
            when {
                oppholdstillatelsePaSammeVilkar != null -> oppholdstillatelsePaSammeVilkar.type ?: ""
                eosellerEFTAOpphold != null             -> eosellerEFTAOpphold.eosellerEFTAOppholdType.name
                uavklart != null                        -> "uavklart"
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum != null -> "ikke_oppholdstillatelse"
                else                                    -> ""
            }
        }
    }
}