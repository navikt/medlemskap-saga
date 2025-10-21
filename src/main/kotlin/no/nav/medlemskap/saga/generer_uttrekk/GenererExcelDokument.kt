package no.nav.medlemskap.saga.generer_uttrekk

import no.nav.medlemskap.saga.domain.VurderingForAnalyseDAO
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.xssf.usermodel.XSSFWorkbook


class GenererExcelDokument {

    fun generer(vurdering: List<VurderingForAnalyseDAO>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Data Sheet")

        val header = sheet.createRow(0)
        header.createCell(0).setCellValue("dato")
        header.createCell(1).setCellValue("ytelse")
        header.createCell(2).setCellValue("fom")
        header.createCell(3).setCellValue("tom")
        header.createCell(4).setCellValue("fnr")
        header.createCell(5).setCellValue("foerste_dag_for_ytelse")
        header.createCell(6).setCellValue("start_dato_for_ytelse")
        header.createCell(7).setCellValue("svar")
        header.createCell(8).setCellValue("aarsaker")
        header.createCell(9).setCellValue("konklusjon")
        header.createCell(10).setCellValue("avklaringsliste")
        header.createCell(11).setCellValue("nye_spoersmaal")
        header.createCell(12).setCellValue("antall_dager_med_sykmelding")
        header.createCell(13).setCellValue("statsborgerskap")
        header.createCell(14).setCellValue("statsborgerskapskategori")
        header.createCell(15).setCellValue("arbeid_utenfor_norge")
        header.createCell(16).setCellValue("utfoert_arbeid_utenfor_norge")
        header.createCell(17).setCellValue("opphold_utenfor_eos")
        header.createCell(18).setCellValue("opphold_utenfor_norge")
        header.createCell(19).setCellValue("oppholdstillatelse_oppgitt")
        header.createCell(20).setCellValue("oppholdstillatelse_udi_fom")
        header.createCell(21).setCellValue("oppholdstillatelse_udi_tom")
        header.createCell(22).setCellValue("oppholdstillatelse_udi_type")
/*
        vurdering.forEachIndexed {
            index, data ->
            val rad = sheet.createRow(index + 1)
            rad.createCell(0).setCellValue(data.dato)
            rad.createCell(1).setCellValue(data.ytelse)
            rad.createCell(2).setCellValue(data.fom)
            rad.createCell(3).setCellValue(data.tom)
            rad.createCell(4).setCellValue(data.fnr)
            rad.createCell(5).setCellValue(data.foerste_dag_for_ytelse)
            rad.createCell(6).setCellValue(data.start_dato_for_ytelse)
            rad.createCell(7).setCellValue(data.svar)
            rad.createCell(8).setCellValue(data.aarsaker.joinToString(", "))
            rad.createCell(9).setCellValue(data.konklusjon)
            rad.createCell(10).setCellValue(data.avklaringsliste.joinToString(", "))
            rad.createCell(11).setCellValue(data.nye_spoersmaal)
            rad.createCell(12).setCellValue(data.antall_dager_med_sykmelding.toString())
            rad.createCell(13).setCellValue(data.statsborgerskap.joinToString(", "))
            rad.createCell(14).setCellValue(data.statsborgerskapskategori)
            rad.createCell(15).setCellValue(data.arbeid_utenfor_norge)
            rad.createCell(16).setCellValue(data.utfoert_arbeid_utenfor_norge)
            rad.createCell(17).setCellValue(data.dato)
            rad.createCell(18).setCellValue(data.dato)
            rad.createCell(19).setCellValue(data.dato)
            rad.createCell(20).setCellValue(data.dato)
            rad.createCell(21).setCellValue(data.dato)
            rad.createCell(22).setCellValue(data.dato)
        }
*/
        /*val dataRow1 = sheet.createRow(1)
        dataRow1.createCell(0).setCellValue("Alice")
        dataRow1.createCell(1).setCellValue(30)*/

        // Write the workbook to a ByteArrayOutputStream
        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        val excelBytes = outputStream.toByteArray()
        return excelBytes
    }
}