package no.nav.medlemskap.saga.utled_vurderingstagger

import no.nav.medlemskap.saga.domain.Vurdering
import no.nav.medlemskap.saga.domain.VurderingForAnalyse
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforEOSTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgittTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorgeTag
import no.nav.medlemskap.sykepenger.lytter.jakson.JacksonParser

object UtledVurderingstagger {

    fun utled(vurderingSomJson: String): VurderingForAnalyse {

        val vurdering = JacksonParser.parse(vurderingSomJson)
        val vurderingFraJson: Vurdering = JacksonParser.toDomainObject(vurdering)

        val datagrunnlag = vurderingFraJson.datagrunnlag
        val resultat = vurderingFraJson.resultat
        val konklusjon = vurderingFraJson.konklusjon.first()
        val brukerinput = vurderingFraJson.datagrunnlag.brukerinput
        val oppholdstillatelseUDI = vurderingFraJson.datagrunnlag.oppholdstillatelse

        return VurderingForAnalyse(
            ytelse = datagrunnlag.ytelse,
            fom = datagrunnlag.periode.fom,
            tom = datagrunnlag.periode.tom,
            fnr = datagrunnlag.fnr,
            førsteDagForYtelse = datagrunnlag.førsteDagForYtelse,
            startDatoForYtelse = datagrunnlag.startDatoForYtelse,
            svar = resultat.svar,
            årsaker = resultat.årsaker.map { it.regelId },
            konklusjon = konklusjon.status,
            avklaringsListe = konklusjon.avklaringsListe.map { it.regel_id },
            nyeSpørsmål = brukerinput.utfortAarbeidUtenforNorge != null,
            antallDagerMedSykmelding = datagrunnlag.periode.antallDager(),
            statsborgerskap = datagrunnlag.pdlpersonhistorikk.finnAktiveStatsborgerskap(),
            statsborgerskapskategori = konklusjon.enUtledetInformasjon().informasjon.tilKategori(),
            arbeidUtenforNorge = brukerinput.arbeidUtenforNorge,
            utførtArbeidUtenforNorgeTag = UtfortArbeidUtenforNorgeTag.fra(brukerinput.hentUtførtArbeidUtenforNorge()),
            oppholdUtenforEOSTag = OppholdUtenforEOSTag.fra(brukerinput.hentOppholdUtenforEØS()),
            oppholdUtenforNorgeTag = OppholdUtenforNorgeTag.fra(brukerinput.hentOppholdUtenforNorge()),
            oppholdstillatelseOppgittTag = OppholdstillatelseOppgittTag.fra(brukerinput.hentOppholdstillatelseOppgitt()),
            oppholdstillatelseUDIFom = oppholdstillatelseUDI?.hentOppholdstillatelseUDIFom(),
            oppholdstillatelseUDITom = oppholdstillatelseUDI?.hentOppholdstillatelseUDITom(),
            oppholdstillatelseUDIType = oppholdstillatelseUDI?.hentOppholdstillatelseUDIType() ?: ""
        )
    }
}