package no.nav.medlemskap.saga.persistence

import javax.sql.DataSource
import kotliquery.queryOf
import kotliquery.sessionOf
import kotliquery.using
import java.time.LocalDate

interface VurderingForAnalyseRepository {

    fun lagreVurderingForAnalyse(
        dato: LocalDate,
        ytelse: String,
        fom: LocalDate,
        tom: LocalDate,
        fnr: String,
        foerste_dag_for_ytelse: LocalDate,
        start_dato_for_ytelse: LocalDate,
        svar: String,
        aarsaker: Array<String>,
        konklusjon: String,
        avklaringsliste: Array<String>,
        nye_spoersmaal: Boolean,
        antall_dager_med_sykmelding: Long,
        statsborgerskap: Array<String>,
        statsborgerskapskategori: String,
        arbeid_utenfor_norge: Boolean,
        utfoert_arbeid_utenfor_norge: String,
        opphold_utenfor_eos: String,
        opphold_utenfor_norge: String,
        oppholdstillatelse_oppgitt: String,
        oppholdstillatelse_udi_fom: LocalDate?,
        oppholdstillatelse_udi_tom: LocalDate?,
        oppholdstillatelse_udi_type: String
    )
}

class VurderingForAnalyseRepositoryImpl(val dataSource: DataSource) : VurderingForAnalyseRepository {

    val INSERT_VURDERING_ANALYSE = "INSERT INTO vurdering_analyse(" +
            "dato, " +
            "ytelse, " +
            "fom, " +
            "tom, " +
            "fnr, " +
            "foerste_dag_for_ytelse, " +
            "start_dato_for_ytelse, " +
            "svar, " +
            "aarsaker, " +
            "konklusjon, " +
            "avklaringsliste, " +
            "nye_spoersmaal, " +
            "antall_dager_med_sykmelding, " +
            "statsborgerskap, " +
            "statsborgerskapskategori, " +
            "arbeid_utenfor_norge, " +
            "utfoert_arbeid_utenfor_norge, " +
            "opphold_utenfor_eos, " +
            "opphold_utenfor_norge, " +
            "oppholdstillatelse_oppgitt, " +
            "oppholdstillatelse_udi_fom, " +
            "oppholdstillatelse_udi_tom, " +
            "oppholdstillatelse_udi_type" +
            ") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "(to_json(?::utfoert_arbeid_utenfor_norge)), " +
            "(to_json(?::opphold_utenfor_eos)), " +
            "(to_json(?::opphold_utenfor_norge)), " +
            "(to_json(?::oppholdstillatelse_oppgitt)), ?, ?, ?" +
            ");"


    override fun lagreVurderingForAnalyse(
        dato: LocalDate,
        ytelse: String,
        fom: LocalDate,
        tom: LocalDate,
        fnr: String,
        foerste_dag_for_ytelse: LocalDate,
        start_dato_for_ytelse: LocalDate,
        svar: String,
        aarsaker: Array<String>,
        konklusjon: String,
        avklaringsliste: Array<String>,
        nye_spoersmaal: Boolean,
        antall_dager_med_sykmelding: Long,
        statsborgerskap: Array<String>,
        statsborgerskapskategori: String,
        arbeid_utenfor_norge: Boolean,
        utfoert_arbeid_utenfor_norge: String,
        opphold_utenfor_eos: String,
        opphold_utenfor_norge: String,
        oppholdstillatelse_oppgitt: String,
        oppholdstillatelse_udi_fom: LocalDate?,
        oppholdstillatelse_udi_tom: LocalDate?,
        oppholdstillatelse_udi_type: String
    ) {

        using(sessionOf(dataSource)) { session ->
            session.transaction {
                it.run(
                    queryOf(
                        INSERT_VURDERING_ANALYSE,
                        dato,
                        ytelse,
                        fom,
                        tom,
                        fnr,
                        foerste_dag_for_ytelse,
                        start_dato_for_ytelse,
                        svar,
                        aarsaker,
                        konklusjon,
                        avklaringsliste,
                        nye_spoersmaal,
                        antall_dager_med_sykmelding,
                        statsborgerskap,
                        statsborgerskapskategori,
                        arbeid_utenfor_norge,
                        utfoert_arbeid_utenfor_norge,
                        opphold_utenfor_eos,
                        opphold_utenfor_norge,
                        oppholdstillatelse_oppgitt,
                        oppholdstillatelse_udi_fom,
                        oppholdstillatelse_udi_tom,
                        oppholdstillatelse_udi_type
                    )
                        .asExecute
                )
            }

        }
    }


}