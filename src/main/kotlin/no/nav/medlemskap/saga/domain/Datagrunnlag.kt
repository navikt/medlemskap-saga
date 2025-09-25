package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.rest.Ytelse
import java.time.LocalDate

data class Datagrunnlag(
    val ytelse: Ytelse,
    val periode: InputPeriode,
    val fnr: String,
    val førsteDagForYtelse: LocalDate,
    val startDatoForYtelse: LocalDate,
    val brukerinput: Brukerinput,
    val pdlpersonhistorikk: PdlPersonHistorikk,
)