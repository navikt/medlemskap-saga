package no.nav.medlemskap.saga.domain.datagrunnlag

data class OppholdstillatelseOppgittTag(
    val oppholdstillatelseOppgitt: Boolean,
    val oppholdstillatelseOppgittFom: String,
    val oppholdstillatelseOppgittTom: String,
    val oppholdstillatelseOppgittAntallPerioder: Int,
)
