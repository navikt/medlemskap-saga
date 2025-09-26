package no.nav.medlemskap.saga.domain

data class PdlPersonHistorikk(
    val statsborgerskap: List<Statsborgerskap> = emptyList()
) {
    fun finnAktiveStatsborgerskap(): List<String>  {
        return statsborgerskap
            .filter { !it.historisk }
            .map { it.landkode }
    }
}
