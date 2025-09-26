package no.nav.medlemskap.saga.service

import no.nav.medlemskap.saga.domain.Vurdering
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import no.nav.medlemskap.sykepenger.lytter.jakson.JacksonParser

class UtledVurderingstagger {
    fun utled(jsonVurdering: String): VurderingForAnalyse {

        val vurdering = JacksonParser().parse(jsonVurdering)
        val vurderingFraJson: Vurdering = JacksonParser().toDomainObject(vurdering)

        return VurderingForAnalyse(
            vurderingFraJson.datagrunnlag.ytelse,
            vurderingFraJson.datagrunnlag.periode.fom,
            vurderingFraJson.datagrunnlag.periode.tom,
            vurderingFraJson.datagrunnlag.fnr,
            vurderingFraJson.datagrunnlag.førsteDagForYtelse,
            vurderingFraJson.datagrunnlag.startDatoForYtelse,
            vurderingFraJson.resultat.svar,
            vurderingFraJson.resultat.årsaker.map { it.regelId },
            vurderingFraJson.konklusjon.status,
            vurderingFraJson.konklusjon.avklaringsListe.map { it.regel_id },
            vurderingFraJson.datagrunnlag.brukerinput.utfortAarbeidUtenforNorge != null,
            vurderingFraJson.datagrunnlag.periode.antallDager(),
            vurderingFraJson.datagrunnlag.pdlpersonhistorikk.finnAktiveStatsborgerskap(),
            vurderingFraJson.konklusjon.enUtledetInformasjon().informasjon.tilKategori(),
            vurderingFraJson.datagrunnlag.brukerinput.arbeidUtenforNorge,
            vurderingFraJson.datagrunnlag.brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgeOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgeFørsteLandetOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgePeriodeFom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgePeriodeTom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentUtførtArbeidUtenforNorge().utførtArbeidUtenforNorgeAntallPerioderOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforEØS().oppgittOppholdUtenforEØSLand(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSPeriodeFom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSPeriodeTom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSAntallPerioderOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforEØS().oppholdUtenforEØSGrunn(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgeOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforNorge().oppgittOppholdUtenforNorgeLand(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgePeriodeFom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgePeriodeTom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgeAntallPerioderOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdUtenforNorge().oppholdUtenforNorgeGrunn(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdstillatelseOppgitt().oppholdstillatelseOppgitt(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdstillatelseOppgitt().oppholdstillatelseOppgittFom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdstillatelseOppgitt().oppholdstillatelseOppgittTom(),
            vurderingFraJson.datagrunnlag.brukerinput.hentOppholdstillatelseOppgitt().oppholdstillatelseAntallPerioder(),
        )
    }
}