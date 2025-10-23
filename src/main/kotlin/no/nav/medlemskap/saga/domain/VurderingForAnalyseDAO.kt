package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class VurderingForAnalyseDAO(
    val dato: LocalDate,
    val ytelse: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val fnr: String,
    val foerste_dag_for_ytelse: LocalDate,
    val start_dato_for_ytelse: LocalDate,
    val svar: String,
    val aarsaker: Array<String>,
    val konklusjon: String,
    val avklaringsliste: Array<String>,
    val nye_spoersmaal: Boolean,
    val antall_dager_med_sykmelding: Long,
    val statsborgerskap: Array<String>,
    val statsborgerskapskategori: String,
    val arbeid_utenfor_norge: Boolean,
    val utfoert_arbeid_utenfor_norge: String,
    val opphold_utenfor_eos: String,
    val opphold_utenfor_norge: String,
    val oppholdstillatelse_oppgitt: String,
    val oppholdstillatelse_udi_fom: LocalDate?,
    val oppholdstillatelse_udi_tom: LocalDate?,
    val oppholdstillatelse_udi_type: String
)