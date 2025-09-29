package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class OppholdstillatelseOppgittTag(
    val oppholdstillatelseOppgitt: Boolean,
    val oppholdstillatelseOppgittFom: String,
    val oppholdstillatelseOppgittTom: String,
    val oppholdstillatelseOppgittAntallPerioder: Int,
) {
    constructor(oppholdstillatelseOppgitt: OppholdstillatelseOppgitt) : this(
        oppholdstillatelseOppgitt.oppholdstillatelseOppgitt(),
        oppholdstillatelseOppgitt.oppholdstillatelseOppgittFom(),
        oppholdstillatelseOppgitt.oppholdstillatelseOppgittTom(),
        oppholdstillatelseOppgitt.oppholdstillatelseAntallPerioder()
    )
}