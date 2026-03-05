package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import java.time.LocalDate

data class IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
    val utvistMedInnreiseForbud: UtvistMedInnreiseForbud?,
    var avslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak: AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak?,
    var ovrigIkkeOpphold: OvrigIkkeOpphold?
)

data class UtvistMedInnreiseForbud(
    val innreiseForbud: JaNeiUavklart?
)

data class AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak(
    val avgjorelsesDato: LocalDate?
)

data class OvrigIkkeOpphold(
    val ovrigIkkeOppholdsKategori: OvrigIkkeOppholdsKategori?
)

enum class JaNeiUavklart(val jaNeiUavklart: String) {
    JA("Ja"),
    NEI("Nei"),
    UAVKLART("Uavklart");

    companion object {
        fun fraJaNeiUavklartVerdi(jaNeiUavklartVerdi: String?): JaNeiUavklart? {
            if (jaNeiUavklartVerdi.isNullOrEmpty()) return null
            return valueOf(jaNeiUavklartVerdi.uppercase())
        }
    }
}

enum class OvrigIkkeOppholdsKategori(val kodeverdi: String) {
    OPPHEVET_INNREISEFORBUD("OpphevetInnreiseforbud"),
    ANNULERING_AV_VISUM("AnnuleringAvVisum"),
    UTLOPT_OPPHOLDSTILLATELSE("UtloptOppholdstillatelse"),
    UTLOPT_EO_SELLER_EFTA_OPPHOLDSRETT_ELLER_EO_SELLER_EFTA_OPPHOLDSTILLATELSE("UtloptEOSellerEFTAOppholdsrettEllerEOSellerEFTAOppholdstillatelse");

    companion object {
        fun fraOvrigIkkeOppholdsKategoriType(
            ovrigIkkeOppholdsKategori: String?
        ): OvrigIkkeOppholdsKategori? {
            return OvrigIkkeOppholdsKategori.values()
                .firstOrNull { it.kodeverdi == ovrigIkkeOppholdsKategori }
        }
    }
}