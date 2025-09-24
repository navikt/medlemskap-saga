package no.nav.medlemskap.saga.domain

import java.time.LocalDateTime

data class Vurdering(
    val tidspunkt : LocalDateTime,
    val datagrunnlag: Datagrunnlag,
    val resultat: ResultatFraMedlemskapOppslag,
    val konklusjon: KonklusjonFraSP6000
)