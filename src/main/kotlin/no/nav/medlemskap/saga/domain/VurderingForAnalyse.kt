package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.rest.Ytelse
import java.time.LocalDate

data class VurderingForAnalyse(
    val ytelse: Ytelse,
    val fom: LocalDate,
    val tom: LocalDate,
    val fnr: String,
    val førsteDagForYtelse: LocalDate,
    val startDatoForYtelse: LocalDate,
    val svar: Svar,
    val årsaker: List<String>,
    val konklusjon: Svar,
    val avklaringsListe: List<String>,
    val nyeSpørsmål: Boolean,
    val antallDagerMedSykmelding: Long,
    val statsborgerskap: List<String>,
    val statsborgerskapskategori: Statsborgerskapskategori,
    val arbeidUtenforNorge: Boolean,
    val utførtArbeidUtenforNorge: Boolean,
    val utførtArbeidUtenforNorgeLand: String,
    val utførtArbeidUtenforNorgeFom: String,
    val utførtArbeidUtenforNorgeTom: String,
    val utførtArbeidUtenforNorgeAntallPerioder: Int
)