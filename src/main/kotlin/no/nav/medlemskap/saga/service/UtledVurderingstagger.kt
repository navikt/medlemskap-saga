package no.nav.medlemskap.saga.service

import no.nav.medlemskap.saga.domain.JacksonParser
import no.nav.medlemskap.saga.domain.Vurdering
import no.nav.medlemskap.saga.persistence.VurderingForAnalyse

class UtledVurderingstagger {
    fun utled(jsonVurdering: String): VurderingForAnalyse {

        val vurdering = JacksonParser().ToJson(jsonVurdering)
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
            vurderingFraJson.konklusjon.avklaringsListe.map { it.regel_id }
        )
    }
}