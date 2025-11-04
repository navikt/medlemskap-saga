package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class OppholdstillatelseOppgittTag(
    val oppholdstillatelseOppgitt: Boolean,
    val oppholdstillatelseOppgittFom: String,
    val oppholdstillatelseOppgittTom: String,
    val oppholdstillatelseOppgittAntallPerioder: Int,
) {
    companion object {
        fun fra(oppholdstillatelseOppgitt: OppholdstillatelseOppgitt?): OppholdstillatelseOppgittTag? {
            if (oppholdstillatelseOppgitt == null) return null

            return OppholdstillatelseOppgittTag(
                oppholdstillatelseOppgitt.oppholdstillatelseOppgitt(),
                oppholdstillatelseOppgitt.oppholdstillatelseFørsteOppgitteFom(),
                oppholdstillatelseOppgitt.oppholdstillatelseFørsteOppgitteTom(),
                oppholdstillatelseOppgitt.oppholdstillatelseAntallPerioderOppgitt()
            )
        }

    }
}

fun OppholdstillatelseOppgittTag?.formater(): String {
    if (this == null) return ""
    return this.oppholdstillatelseOppgitt.toString()
}