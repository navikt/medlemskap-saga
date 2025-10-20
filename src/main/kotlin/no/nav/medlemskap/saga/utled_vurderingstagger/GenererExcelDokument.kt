package no.nav.medlemskap.saga.utled_vurderingstagger

import UttrekkAnalyse
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class GenererExcelDokument {

    fun generer(vurdering: List<UttrekkAnalyse>): ByteArray {
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

        // Create header row

        // Write the workbook to a ByteArrayOutputStream
        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        val excelBytes = outputStream.toByteArray()
        return excelBytes
    }
}