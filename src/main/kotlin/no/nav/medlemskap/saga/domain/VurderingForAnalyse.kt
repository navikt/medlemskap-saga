package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforEOSTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgittTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.Ytelse
import no.nav.medlemskap.saga.domain.konklusjon.Statsborgerskapskategori
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
    val utførtArbeidUtenforNorgeTag: UtfortArbeidUtenforNorgeTag,
    val oppholdUtenforEOSTag: OppholdUtenforEOSTag,
    val oppholdUtenforNorgeTag: OppholdUtenforNorgeTag,
    val oppholdstillatelseOppgittTag: OppholdstillatelseOppgittTag,
    val oppholdTillatelseUDIFom: String,
    val oppholdTillatelseUDITom: String,
    val oppholdTillatelseUDIType: String,
)