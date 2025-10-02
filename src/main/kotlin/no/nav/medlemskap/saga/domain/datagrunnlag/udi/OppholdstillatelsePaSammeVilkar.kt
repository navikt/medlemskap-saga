package no.nav.medlemskap.saga.domain.datagrunnlag.udi

data class OppholdstillatelsePaSammeVilkar(
    val periode: UdiPeriode,
    val type: String? = null,
    val soknadIkkeAvgjort: Boolean? = null
)