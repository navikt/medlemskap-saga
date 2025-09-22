package no.nav.medlemskap.saga.domain

import java.time.LocalDateTime

data class Vurdering(
    val tidspunkt : LocalDateTime,
    val kanal:String,
    val datagrunnlag: Datagrunnlag,
    val resultat: GammelkjøringResultat,
    val konklusjon: Konklusjon
) {

}