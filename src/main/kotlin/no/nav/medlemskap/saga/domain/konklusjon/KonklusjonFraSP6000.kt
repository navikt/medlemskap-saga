package no.nav.medlemskap.saga.domain.konklusjon

import no.nav.medlemskap.saga.domain.konklusjon.Avklaring
import no.nav.medlemskap.saga.domain.Svar
import no.nav.medlemskap.saga.domain.konklusjon.UtledetInformasjon

data class KonklusjonFraSP6000(
    val status: Svar,
    val avklaringsListe: List<Avklaring> = emptyList(),
    val utledetInformasjoner: List<UtledetInformasjon> = emptyList()
) {
    fun enUtledetInformasjon(): UtledetInformasjon {
          return utledetInformasjoner.firstOrNull() ?:
          throw IllegalStateException("Listen med utledet informasjon skal alltid ha ett element, men var tom")
    }
}