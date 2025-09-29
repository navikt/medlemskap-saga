package no.nav.medlemskap.saga.utled_vurderingstagger

import no.nav.medlemskap.saga.domain.Vurdering
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforEOSTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgittTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorgeTag
import no.nav.medlemskap.sykepenger.lytter.jakson.JacksonParser

class UtledVurderingstagger {
    fun utled(jsonVurdering: String): VurderingForAnalyse {

        val vurdering = JacksonParser().parse(jsonVurdering)
        val vurderingFraJson: Vurdering = JacksonParser().toDomainObject(vurdering)

        val datagrunnlag = vurderingFraJson.datagrunnlag
        val resultat = vurderingFraJson.resultat
        val konklusjon = vurderingFraJson.konklusjon.first()
        val brukerinput = vurderingFraJson.datagrunnlag.brukerinput
        val oppholdstillatelseUDI = vurderingFraJson.datagrunnlag.oppholdstillatelse

        return VurderingForAnalyse(
            datagrunnlag.ytelse,
            datagrunnlag.periode.fom,
            datagrunnlag.periode.tom,
            datagrunnlag.fnr,
            datagrunnlag.førsteDagForYtelse,
            datagrunnlag.startDatoForYtelse,
            resultat.svar,
            resultat.årsaker.map { it.regelId },
            konklusjon.status,
            konklusjon.avklaringsListe.map { it.regel_id },
            brukerinput.utfortAarbeidUtenforNorge != null,
            datagrunnlag.periode.antallDager(),
            datagrunnlag.pdlpersonhistorikk.finnAktiveStatsborgerskap(),
            konklusjon.enUtledetInformasjon().informasjon.tilKategori(),

            brukerinput.arbeidUtenforNorge,
            UtfortArbeidUtenforNorgeTag(
                brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgeOppgitt(),
                brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgeFørsteLandetOppgitt(),
                brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgePeriodeFom(),
                brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgePeriodeTom(),
                brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgeAntallPerioderOppgitt()
            ),
            OppholdUtenforEOSTag(
                brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSOppgitt(),
                brukerinput.hentOppholdUtenforEØS().oppgittOppholdUtenforEØSLand(),
                brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSPeriodeFom(),
                brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSPeriodeTom(),
                brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSAntallPerioderOppgitt(),
                brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSGrunn()
            ),
            OppholdUtenforNorgeTag(
                brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgeOppgitt(),
                brukerinput.hentOppholdUtenforNorge().oppgittOppholdUtenforNorgeLand(),
                brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgePeriodeFom(),
                brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgePeriodeTom(),
                brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgeAntallPerioderOppgitt(),
                brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgeGrunn()
            ),
            OppholdstillatelseOppgittTag(brukerinput.hentOppholdstillatelseOppgitt()),
            oppholdstillatelseUDI?.hentOppholdstillatelseUDIFom() ?: "",
            oppholdstillatelseUDI?.hentOppholdstillatelseUDITom() ?: "",
            oppholdstillatelseUDI?.hentOppholdstillatelseUDIType() ?: "",
        )
    }
}