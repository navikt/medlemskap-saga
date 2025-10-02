package no.nav.medlemskap.saga.domain.resultat

import no.nav.medlemskap.saga.domain.Svar
import no.nav.medlemskap.saga.domain.resultat.Årsak

data class ResultatFraMedlemskapOppslag (
    val svar: Svar,
    val årsaker: List<Årsak> = listOf(),
)