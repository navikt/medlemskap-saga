package no.nav.medlemskap.saga.utled_vurderingstagger

import UttrekkAnalyse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.nav.medlemskap.saga.persistence.VurderingForAnalyseRepository
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import java.time.LocalDate

class VurderingForAnalyseService(
    val vurderingForAnalyseRepository: VurderingForAnalyseRepository,
    val utledVurderingstagger: UtledVurderingstagger
) {

    fun lagreTilVurderingForAnalyse(json: String) {
        val vurderingForAnalyse = utledVurderingstagger.utled(json)
        lagreTilVurderingForAnalyse(vurderingForAnalyse)
    }

    fun lagreTilVurderingForAnalyse(vurderingForAnalyse: VurderingForAnalyse) {
        val mapper = jacksonObjectMapper()

        vurderingForAnalyseRepository.lagreVurderingForAnalyse(
            LocalDate.now(),
            vurderingForAnalyse.ytelse.name,
            vurderingForAnalyse.fom,
            vurderingForAnalyse.tom,
            vurderingForAnalyse.fnr,
            vurderingForAnalyse.førsteDagForYtelse,
            vurderingForAnalyse.startDatoForYtelse,
            vurderingForAnalyse.svar.name,
            vurderingForAnalyse.årsaker.toTypedArray(),
            vurderingForAnalyse.konklusjon.name,
            vurderingForAnalyse.avklaringsListe.toTypedArray(),
            vurderingForAnalyse.nyeSpørsmål,
            vurderingForAnalyse.antallDagerMedSykmelding,
            vurderingForAnalyse.statsborgerskap.toTypedArray(),
            vurderingForAnalyse.statsborgerskapskategori.name,
            vurderingForAnalyse.arbeidUtenforNorge,
            mapper.writeValueAsString(vurderingForAnalyse.utførtArbeidUtenforNorgeTag),
            mapper.writeValueAsString(vurderingForAnalyse.oppholdUtenforEOSTag),
            mapper.writeValueAsString(vurderingForAnalyse.oppholdUtenforNorgeTag),
            mapper.writeValueAsString(vurderingForAnalyse.oppholdstillatelseOppgittTag),
            vurderingForAnalyse.oppholdstillatelseUDIFom,
            vurderingForAnalyse.oppholdstillatelseUDITom,
            vurderingForAnalyse.oppholdstillatelseUDIType
        )
    }

    fun hentVurderingerForAnalyse(aarMaanedParam: String): List<UttrekkAnalyse> {
        // Får inn årmnd parameter -> må konverteres til fom og tom
        // Kalle på metode i repo for å hente ut uttrekk og sende inn fom og tom
        // Returner liste med uttrekk analyse
        return emptyList()

    }
}