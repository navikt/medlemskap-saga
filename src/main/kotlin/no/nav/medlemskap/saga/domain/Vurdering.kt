package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.domain.datagrunnlag.Datagrunnlag
import no.nav.medlemskap.saga.domain.konklusjon.KonklusjonFraSP6000
import no.nav.medlemskap.saga.domain.resultat.ResultatFraMedlemskapOppslag
import java.time.LocalDateTime

data class Vurdering(
    val tidspunkt : LocalDateTime,
    val datagrunnlag: Datagrunnlag,
    val resultat: ResultatFraMedlemskapOppslag,
    val konklusjon: KonklusjonFraSP6000
)