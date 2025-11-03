package no.nav.medlemskap.saga.persistence

import no.nav.medlemskap.saga.domain.VurderingForAnalyseDAO
import kotliquery.Row
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
        oppholdstillatelse_udi_type: String,
        kilde: String,
        nav_call_id: String
    )
    fun hentVurderingerForAnalyse(førsteDag: LocalDate, sisteDag: LocalDate): List<VurderingForAnalyseDAO>

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
            "oppholdstillatelse_udi_type, " +
            "kilde, " +
            "nav_call_id" +
            ") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "(to_json(?::json)), " +
            "(to_json(?::json)), " +
            "(to_json(?::json)), " +
            "(to_json(?::json)), ?, ?, ?, ?, ?" +
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
        oppholdstillatelse_udi_type: String,
        kilde: String,
        nav_call_id: String
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
                        oppholdstillatelse_udi_type,
                        kilde,
                        nav_call_id
                    )
                        .asExecute
                )
            }

        }
    }

    val tilVurderingForAnalyseDAO: (Row) -> VurderingForAnalyseDAO = { row ->
        VurderingForAnalyseDAO(
            dato = row.localDate("dato"),
            ytelse = row.string("ytelse"),
            fom = row.localDate("fom"),
            tom = row.localDate("tom"),
            foerste_dag_for_ytelse = row.localDate("foerste_dag_for_ytelse"),
            start_dato_for_ytelse = row.localDate("start_dato_for_ytelse"),
            svar = row.string("svar"),
            aarsaker = row.array("aarsaker"),
            konklusjon = row.string("konklusjon"),
            avklaringsliste = row.array("avklaringsliste"),
            nye_spoersmaal = row.boolean("nye_spoersmaal"),
            antall_dager_med_sykmelding = row.long("antall_dager_med_sykmelding"),
            statsborgerskap = row.array("statsborgerskap"),
            statsborgerskapskategori = row.string("statsborgerskapskategori"),
            arbeid_utenfor_norge = row.boolean("arbeid_utenfor_norge"),
            utfoert_arbeid_utenfor_norge = row.string("utfoert_arbeid_utenfor_norge"),
            opphold_utenfor_eos = row.string("opphold_utenfor_eos"),
            opphold_utenfor_norge = row.string("opphold_utenfor_norge"),
            oppholdstillatelse_oppgitt = row.string("oppholdstillatelse_oppgitt"),
            oppholdstillatelse_udi_fom = row.localDateOrNull("oppholdstillatelse_udi_fom"),
            oppholdstillatelse_udi_tom = row.localDateOrNull("oppholdstillatelse_udi_tom"),
            oppholdstillatelse_udi_type = row.string("oppholdstillatelse_udi_type"),
            kilde = row.stringOrNull("kilde").orEmpty()
        )
    }

    val HENT_VURDERINGER_FOR_ANALYSE_FOR_PERIODE = "SELECT DISTINCT * FROM vurdering_analyse WHERE dato BETWEEN ? AND ?"

    override fun hentVurderingerForAnalyse(førsteDag: LocalDate, sisteDag: LocalDate): List<VurderingForAnalyseDAO> {
        return using(sessionOf(dataSource)) {
            it
                .run(
                    queryOf(HENT_VURDERINGER_FOR_ANALYSE_FOR_PERIODE, førsteDag, sisteDag)
                        .map(tilVurderingForAnalyseDAO)
                        .asList
                )
        }
    }
}