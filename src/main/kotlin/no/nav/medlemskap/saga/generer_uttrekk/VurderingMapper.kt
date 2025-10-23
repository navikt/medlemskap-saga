package no.nav.medlemskap.saga.generer_uttrekk

import no.nav.medlemskap.saga.domain.Svar
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import no.nav.medlemskap.saga.domain.VurderingForAnalyseDAO
import no.nav.medlemskap.saga.domain.datagrunnlag.Ytelse
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforEOSTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgittTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorgeTag
import no.nav.medlemskap.saga.domain.konklusjon.Statsborgerskapskategori
import no.nav.medlemskap.sykepenger.lytter.jakson.JacksonParser

object VurderingMapper {

    fun tilVurderingForAnalyse(vurderingForAnalyseDAO: VurderingForAnalyseDAO): VurderingForAnalyse {

        val utførtArbeidUtenforNorge: UtfortArbeidUtenforNorgeTag =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.utfoert_arbeid_utenfor_norge)
        val oppholdUtenforEØS: OppholdUtenforEOSTag =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.opphold_utenfor_eos)
        val oppholdUtenforNorge: OppholdUtenforNorgeTag =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.opphold_utenfor_norge)
        val oppholdstillatelseOppgitt: OppholdstillatelseOppgittTag =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.oppholdstillatelse_oppgitt)

        return VurderingForAnalyse(
            dato = vurderingForAnalyseDAO.dato,
            ytelse = Ytelse.valueOf(vurderingForAnalyseDAO.ytelse),
            fom = vurderingForAnalyseDAO.fom,
            tom = vurderingForAnalyseDAO.tom,
            fnr = vurderingForAnalyseDAO.fnr,
            førsteDagForYtelse = vurderingForAnalyseDAO.foerste_dag_for_ytelse,
            startDatoForYtelse = vurderingForAnalyseDAO.start_dato_for_ytelse,
            svar = Svar.valueOf(vurderingForAnalyseDAO.svar),
            årsaker = vurderingForAnalyseDAO.aarsaker.toList(),
            konklusjon = Svar.valueOf(vurderingForAnalyseDAO.konklusjon),
            avklaringsListe = vurderingForAnalyseDAO.avklaringsliste.toList(),
            nyeSpørsmål = vurderingForAnalyseDAO.nye_spoersmaal,
            antallDagerMedSykmelding = vurderingForAnalyseDAO.antall_dager_med_sykmelding,
            statsborgerskap = vurderingForAnalyseDAO.statsborgerskap.toList(),
            statsborgerskapskategori = Statsborgerskapskategori.valueOf(vurderingForAnalyseDAO.statsborgerskapskategori),
            arbeidUtenforNorge = vurderingForAnalyseDAO.arbeid_utenfor_norge,
            utførtArbeidUtenforNorgeTag = utførtArbeidUtenforNorge,
            oppholdUtenforEØSTag = oppholdUtenforEØS,
            oppholdUtenforNorgeTag = oppholdUtenforNorge,
            oppholdstillatelseOppgittTag = oppholdstillatelseOppgitt,
            oppholdstillatelseUDIFom = vurderingForAnalyseDAO.oppholdstillatelse_udi_fom,
            oppholdstillatelseUDITom = vurderingForAnalyseDAO.oppholdstillatelse_udi_tom,
            oppholdstillatelseUDIType = vurderingForAnalyseDAO.oppholdstillatelse_udi_type
        )
    }
}