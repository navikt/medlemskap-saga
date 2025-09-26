package no.nav.medlemskap.saga.domain.datagrunnlag

import no.nav.medlemskap.saga.domain.datagrunnlag.Statsborgerskap

data class PdlPersonHistorikk(
    val statsborgerskap: List<Statsborgerskap> = emptyList()
) {
    fun finnAktiveStatsborgerskap(): List<String>  {
        return statsborgerskap
            .filter { !it.historisk }
            .map { it.landkode }
    }
}
