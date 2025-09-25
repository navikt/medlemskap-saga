package no.nav.medlemskap.saga.domain

data class PdlPersonHistorikk(
    val oppholdstilatelser: List<PdlOppholdsTilatelse> = emptyList(),
    val statsborgerskap: List<Statsborgerskap> = emptyList()
) {
    fun finnAktiveStatsborgerskap(): List<String>  {
        return statsborgerskap
            .filter { !it.historisk }
            .map { it.landkode }
    }
}
