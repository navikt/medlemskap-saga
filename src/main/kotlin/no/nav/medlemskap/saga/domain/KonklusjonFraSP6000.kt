package no.nav.medlemskap.saga.domain


data class KonklusjonFraSP6000(
    val status: Svar,
    val avklaringsListe: List<Avklaring> = emptyList()
)