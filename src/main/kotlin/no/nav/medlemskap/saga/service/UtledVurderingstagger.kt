package no.nav.medlemskap.saga.service

import no.nav.medlemskap.saga.domain.JacksonParser
import no.nav.medlemskap.saga.domain.Vurdering
import no.nav.medlemskap.saga.persistence.VurderingForAnalyse

class UtledVurderingstagger {
    fun utled(jsonVurdering: String): VurderingForAnalyse {
        val vurdering = JacksonParser().ToJson(jsonVurdering)
        val vurderingFraSP6000: Vurdering = JacksonParser().toDomainObject(vurdering)

        return VurderingForAnalyse(
        vurderingFraSP6000.datagrunnlag.ytelse,
        vurderingFraSP6000.datagrunnlag.periode.fom,
            vurderingFraSP6000.datagrunnlag.periode.tom,
            vurderingFraSP6000.datagrunnlag.fnr,
            vurderingFraSP6000.datagrunnlag.førsteDagForYtelse,
            vurderingFraSP6000.datagrunnlag.startDatoForYtelse,
            vurderingFraSP6000.resultat.svar,
            vurderingFraSP6000.resultat.årsaker,
            vurderingFraSP6000.konklusjon.status,
            vurderingFraSP6000.konklusjon.avklaringsListe
        )

    }
}