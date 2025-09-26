package no.nav.medlemskap.saga.domain.datagrunnlag

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode

data class Opphold(
    val id: String,
    val land: String,
    val grunn: String,
    val perioder: List<Periode>
)