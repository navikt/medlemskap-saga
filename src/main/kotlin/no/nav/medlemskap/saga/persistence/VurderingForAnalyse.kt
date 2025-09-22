package no.nav.medlemskap.saga.persistence

import no.nav.medlemskap.saga.domain.Avklaring
import no.nav.medlemskap.saga.domain.Svar
import no.nav.medlemskap.saga.rest.Ytelse
import no.nav.medlemskap.saga.domain.Årsak
import java.time.LocalDate

data class VurderingForAnalyse(
    val ytelse: Ytelse,
    val fom: LocalDate,
    val tom: LocalDate,
    val fnr: String,
    val førsteDagForYtelse: LocalDate,
    val startDatoForYtelse: LocalDate,
    val svar: Svar,
    val årsaker: List<Årsak>,
    val konklusjon: Svar,
    val avklaringsListe: List<Avklaring>,
    ) {


}