package no.nav.medlemskap.saga.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import no.nav.medlemskap.saga.domain.VurderingForAnalyseDAO
import no.nav.medlemskap.saga.generer_uttrekk.PeriodeForUttrekk
import no.nav.medlemskap.saga.persistence.VurderingForAnalyseRepository
import no.nav.medlemskap.saga.generer_uttrekk.UttrekkForPeriode
import no.nav.medlemskap.saga.utled_vurderingstagger.UtledVurderingstagger
import java.time.LocalDate

class UttrekkService(
    val vurderingForAnalyseRepository: VurderingForAnalyseRepository
) {

    fun lagreTilVurderingForAnalyse(vurderingSomJson: String) {
        val vurderingForAnalyse = UtledVurderingstagger.utled(vurderingSomJson)
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

    fun hentVurderingerForAnalyse(parameter: String): List<VurderingForAnalyseDAO> {
        val (førsteDag, sisteDag) = PeriodeForUttrekk.finnPeriode(parameter)
        return vurderingForAnalyseRepository.hentVurderingerForAnalyse(førsteDag, sisteDag)
    }
}