package no.nav.medlemskap.saga.domain

data class Arbeidsavtale(
    var periode: ArbeidsForholdPeriode,
    val gyldighetsperiode: ArbeidsForholdPeriode,
    val yrkeskode: String,
    val stillingsprosent: Double?,
    val beregnetAntallTimerPrUke: Double?,
) {
    fun getStillingsprosent(): Double {
        if (stillingsprosent == 0.0 && beregnetAntallTimerPrUke != null && beregnetAntallTimerPrUke > 0) {
            val beregnetStillingsprosent = (beregnetAntallTimerPrUke / 37.5) * 100
            return Math.round(beregnetStillingsprosent * 10.0) / 10.0
        }

        return stillingsprosent ?: 100.0
    }
}