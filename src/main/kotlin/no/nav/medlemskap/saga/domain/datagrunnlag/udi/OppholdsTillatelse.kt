package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import java.time.LocalDate

data class OppholdsTillatelse(
    val gjeldendeOppholdsstatus: GjeldendeOppholdsstatus?
) {
    fun hentOppholdstillatelseUDIFom(): LocalDate? {
        val status = if (gjeldendeOppholdsstatus != null) gjeldendeOppholdsstatus else return null

        return when {
            status.oppholdstillatelsePaSammeVilkar != null ->
                status.oppholdstillatelsePaSammeVilkar.periode?.fom
            status.eosellerEFTAOpphold != null ->
                status.eosellerEFTAOpphold.periode?.let { LocalDate.parse(it.fom) }
            else -> null
        }
    }

    fun hentOppholdstillatelseUDITom(): LocalDate? {
        val status = if (gjeldendeOppholdsstatus != null) gjeldendeOppholdsstatus else return null

        return when {
            status.oppholdstillatelsePaSammeVilkar != null ->
                status.oppholdstillatelsePaSammeVilkar.periode?.tom
            status.eosellerEFTAOpphold != null ->
                status.eosellerEFTAOpphold.periode?.let { LocalDate.parse(it.tom) }
            else -> null
        }
    }

    fun hentOppholdstillatelseUDIType(): String {
        val status = if (gjeldendeOppholdsstatus != null) gjeldendeOppholdsstatus else return ""

        return when {
            status.oppholdstillatelsePaSammeVilkar != null ->
                status.oppholdstillatelsePaSammeVilkar.type ?: ""
            status.eosellerEFTAOpphold != null ->
                status.eosellerEFTAOpphold.eosellerEFTAOppholdType.name
            status.uavklart != null ->
                "uavklart"
            status.ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum != null ->
                "ikke_oppholdstillatelse"
            else -> ""
        }
    }
}