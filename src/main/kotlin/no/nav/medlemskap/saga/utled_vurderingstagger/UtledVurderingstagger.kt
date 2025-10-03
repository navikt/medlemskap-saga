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
            UtfortArbeidUtenforNorgeTag(brukerinput.hentUtførtArbeidUtenforNorge()),
            OppholdUtenforEOSTag(brukerinput.hentOppholdUtenforEØS()),
            OppholdUtenforNorgeTag(brukerinput.hentOppholdUtenforNorge()),
            OppholdstillatelseOppgittTag(brukerinput.hentOppholdstillatelseOppgitt()),
            oppholdstillatelseUDI?.hentOppholdstillatelseUDIFom(),
            oppholdstillatelseUDI?.hentOppholdstillatelseUDITom(),
            oppholdstillatelseUDI?.hentOppholdstillatelseUDIType() ?: "",
        )
    }
}