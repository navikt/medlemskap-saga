package no.nav.medlemskap.saga.domain

data class ResultatFraMedlemskapOppslag (
    val svar: Svar,
    val årsaker: List<Årsak> = listOf(),
)