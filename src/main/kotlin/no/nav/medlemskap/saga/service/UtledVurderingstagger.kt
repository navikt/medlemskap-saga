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
            vurderingFraJson.konklusjon.enUtledetInformasjon().informasjon.tilKategori()
        )
    }
}