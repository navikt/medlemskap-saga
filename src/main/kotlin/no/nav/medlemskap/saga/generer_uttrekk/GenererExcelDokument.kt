package no.nav.medlemskap.saga.generer_uttrekk

import no.nav.medlemskap.saga.domain.VurderingForAnalyseUttrekk
import no.nav.medlemskap.saga.domain.tilListe
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.xssf.usermodel.XSSFWorkbook

object GenererExcelDokument {

    fun generer(vurderinger: List<VurderingForAnalyseUttrekk>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Data Sheet")

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

        val headerRad = sheet.createRow(0)

        //Oppretter header-rad
        headers.forEachIndexed { kolonneNummer, headerNavn ->
            headerRad.createCell(kolonneNummer).setCellValue(headerNavn)
        }

        //Oppretter rader og fyller inn data
        vurderinger.forEachIndexed { radIndeks, vurdering ->
            val rad = sheet.createRow(radIndeks + 1)
            vurdering.tilListe().forEachIndexed { kolonneIndeks, kolonneVerdi ->
                rad.createCell(kolonneIndeks).setCellValue(kolonneVerdi)
            }
        }

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        val excelBytes = outputStream.toByteArray()
        return excelBytes
    }
}