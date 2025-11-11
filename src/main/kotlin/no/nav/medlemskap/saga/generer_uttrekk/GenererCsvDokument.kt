package no.nav.medlemskap.saga.generer_uttrekk

import mu.KotlinLogging
import no.nav.medlemskap.saga.domain.VurderingForAnalyseUttrekk
import no.nav.medlemskap.saga.domain.tilListe
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

object GenererCsvDokument {

    private val logger = KotlinLogging.logger("GenererCsvDokument")

    fun generer(vurderinger: List<VurderingForAnalyseUttrekk>): ByteArray {

        logger.info("Starter generering av CSV-dokument for ${vurderinger.size} vurderinger")


        // Header
        val headers = listOf(
            "dato",
            "ytelse",
            "fom",
            "tom",
            "foerste_dag_for_ytelse",
            "start_dato_for_ytelse",
            "svar",
            "aarsaker",
            "konklusjon",
            "avklaringsliste",
            "nye_spoersmaal",
            "antall_dager_med_sykmelding",
            "statsborgerskap",
            "statsborgerskapskategori",
            "arbeid_utenfor_norge",
            "utfoert_arbeid_utenfor_norge",
            "utfoert_arbeid_utenfor_norge_land",
            "utfoert_arbeid_utenfor_norge_fom",
            "utfoert_arbeid_utenfor_norge_tom",
            "utfoert_arbeid_utenfor_norge_antall_perioder",
            "opphold_utenfor_eos",
            "opphold_utenfor_eos_land",
            "opphold_utenfor_eos_fom",
            "opphold_utenfor_eos_tom",
            "opphold_utenfor_eos_antall_perioder",
            "opphold_utenfor_eos_grunn",
            "opphold_utenfor_norge",
            "opphold_utenfor_norge_land",
            "opphold_utenfor_norge_fom",
            "opphold_utenfor_norge_tom",
            "opphold_utenfor_norge_antall_perioder",
            "opphold_utenfor_norge_grunn",
            "oppholdstillatelse_oppgitt",
            "oppholdstillatelse_oppgitt_fom",
            "oppholdstillatelse_oppgitt_tom",
            "oppholdstillatelse_oppgitt_antall_perioder",
            "oppholdstillatelse_udi_fom",
            "oppholdstillatelse_udi_tom",
            "oppholdstillatelse_udi_type",
            "kilde"
        )

        val outputStream = ByteArrayOutputStream()
        val writer = OutputStreamWriter(outputStream, StandardCharsets.UTF_8)

        writer.appendLine(headers.joinToString(","))

        vurderinger.forEach { vurdering ->
            val row = vurdering.tilListe().joinToString(",") { escapeCsv(it) }
            writer.appendLine(row)
        }

        writer.flush()
        val csvBytes = outputStream.toByteArray()

        logger.info { "CSV-dokument generert" }

        return csvBytes
    }

    private fun escapeCsv(value: String): String {
        return if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            "\"" + value.replace("\"", "\"\"") + "\""
        } else {
            value
        }
    }
}