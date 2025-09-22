package no.nav.medlemskap.saga.domain

data class Arbeidsforhold(
    val periode: ArbeidsForholdPeriode,
    val arbeidsforholdstype: Arbeidsforholdstype,
    var arbeidsavtaler: List<Arbeidsavtale>,
    val permisjonPermittering: List<PermisjonPermittering>?
)
