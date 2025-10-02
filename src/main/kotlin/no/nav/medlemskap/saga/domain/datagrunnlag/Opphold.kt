package no.nav.medlemskap.saga.domain.datagrunnlag

data class Opphold(
    val id: String,
    val land: String,
    val grunn: String,
    val perioder: List<Periode>
)