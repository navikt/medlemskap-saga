package no.nav.medlemskap.saga.domain.datagrunnlag.udi

data class GjeldendeOppholdsstatus(
    val oppholdstillatelsePaSammeVilkar: OppholdstillatelsePaSammeVilkar?,
    val eosellerEFTAOpphold: EOSellerEFTAOpphold?,
    val uavklart: Uavklart?,
    val ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum: IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum?
)

data class Uavklart(
    val uavklart: Boolean = false
)