package no.nav.medlemskap.saga.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import no.nav.medlemskap.saga.domain.VurderingForAnalyseUttrekk
import no.nav.medlemskap.saga.generer_uttrekk.PeriodeForUttrekk
import no.nav.medlemskap.saga.generer_uttrekk.VurderingMapper
import no.nav.medlemskap.saga.persistence.VurderingForAnalyseRepository
import no.nav.medlemskap.saga.utled_vurderingstagger.UtledVurderingstagger
import java.time.LocalDate

class AnalyseService(val vurderingForAnalyseRepository: VurderingForAnalyseRepository) {

    val logger = KotlinLogging.logger("AnalyseService")

    fun lagreTilVurderingForAnalyse(vurderingSomJson: String, callId: String) {
        val vurderingForAnalyse = UtledVurderingstagger.utled(vurderingSomJson, callId)
        lagreTilVurderingForAnalyse(vurderingForAnalyse)
    }

    fun lagreTilVurderingForAnalyse(vurderingForAnalyse: VurderingForAnalyse) {
        val mapper = jacksonObjectMapper()

        vurderingForAnalyseRepository.lagreVurderingForAnalyse(
            dato = LocalDate.now(),
            ytelse = vurderingForAnalyse.ytelse.name,
            fom = vurderingForAnalyse.fom,
            tom = vurderingForAnalyse.tom,
            fnr = vurderingForAnalyse.fnr,
            foerste_dag_for_ytelse = vurderingForAnalyse.førsteDagForYtelse,
            start_dato_for_ytelse = vurderingForAnalyse.startDatoForYtelse,
            svar = vurderingForAnalyse.svar.name,
            aarsaker = vurderingForAnalyse.årsaker.toTypedArray(),
            konklusjon = vurderingForAnalyse.konklusjon.name,
            avklaringsliste = vurderingForAnalyse.avklaringsListe.toTypedArray(),
            nye_spoersmaal = vurderingForAnalyse.nyeSpørsmål,
            antall_dager_med_sykmelding = vurderingForAnalyse.antallDagerMedSykmelding,
            statsborgerskap = vurderingForAnalyse.statsborgerskap.toTypedArray(),
            statsborgerskapskategori = vurderingForAnalyse.statsborgerskapskategori.name,
            arbeid_utenfor_norge = vurderingForAnalyse.arbeidUtenforNorge,
            utfoert_arbeid_utenfor_norge = mapper.writeValueAsString(vurderingForAnalyse.utførtArbeidUtenforNorgeTag),
            opphold_utenfor_eos = mapper.writeValueAsString(vurderingForAnalyse.oppholdUtenforEØSTag),
            opphold_utenfor_norge = mapper.writeValueAsString(vurderingForAnalyse.oppholdUtenforNorgeTag),
            oppholdstillatelse_oppgitt = mapper.writeValueAsString(vurderingForAnalyse.oppholdstillatelseOppgittTag),
            oppholdstillatelse_udi_fom = vurderingForAnalyse.oppholdstillatelseUDIFom,
            oppholdstillatelse_udi_tom = vurderingForAnalyse.oppholdstillatelseUDITom,
            oppholdstillatelse_udi_type = vurderingForAnalyse.oppholdstillatelseUDIType,
            kilde = vurderingForAnalyse.kilde,
            nav_call_id = vurderingForAnalyse.navCallId
        )
    }

    fun hentVurderingerForAnalyse(parameter: String): List<VurderingForAnalyseUttrekk> {
        val (førsteDag, sisteDag) = PeriodeForUttrekk.finnPeriode(parameter)

        val vurderingForAnalyseDAO = vurderingForAnalyseRepository.hentVurderingerForAnalyse(førsteDag, sisteDag)

        if (vurderingForAnalyseDAO.isEmpty()) {
            logger.info { "Ingen vurderinger funnet for perioden $førsteDag - $sisteDag" }
        } else {
            logger.info { "${vurderingForAnalyseDAO.size} vurderinger funnet for perioden $førsteDag - $sisteDag" }
        }

        var formaterteVurderinger: List<VurderingForAnalyseUttrekk>
        try {
            formaterteVurderinger = vurderingForAnalyseDAO
                .map { VurderingMapper.tilVurderingForAnalyseUttrekk(it) }
        } catch (exception: Exception) {
            logger.error(exception) { "Feil ved mapping av vurderinger" }
            throw exception
        }

        return formaterteVurderinger
    }
}