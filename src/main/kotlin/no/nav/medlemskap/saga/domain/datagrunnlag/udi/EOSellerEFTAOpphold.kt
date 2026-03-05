package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import java.util.Date

data class EOSellerEFTAOpphold(
    val periode: Periode?,
    val eosellerEFTAOppholdType: EOSellerEFTAOppholdType,
    val eosellerEFTAGrunnlagskategoriOppholdsrettType: EOSellerEFTAGrunnlagskategoriOppholdsrettType?,
    val eosellerEFTAGrunnlagskategoriOppholdstillatelseType: EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType?
)

data class Periode(val fom: String, val tom: String= Date().toString())

enum class EOSellerEFTAOppholdType(val kodeverdi: String) {
    EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT("EOSellerEFTABeslutningOmOppholdsrett"),
    EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT("EOSellerEFTAVedtakOmVarigOppholdsrett"),
    EOS_ELLER_EFTA_OPPHOLDSTILLATELSE("EOSellerEFTAOppholdstillatelse");

    companion object {
        fun fraEOSellerEFTAOppholdTypeVerdi(EOSellerEFTAOppholdTypeVerdi: String?): EOSellerEFTAOppholdType? {
            return values().firstOrNull { it.kodeverdi == EOSellerEFTAOppholdTypeVerdi }
        }
    }
}

enum class EOSellerEFTAGrunnlagskategoriOppholdsrettType(val kodeverdi: String) {
    VARIG("Varig"),
    INGEN_INFORMASJON("IngenInformasjon"),
    FAMILIE("Familie"),
    TJENESTEYTING_ELLER_ETABLERING("TjenesteytingEllerEtablering"),
    UAVKLART("Uavklart");

    companion object {
        fun fraEOSellerEFTAGrunnlagskategoriOppholdsrettType(
            eosEllerEFTAGrunnlagskategoriOppholdsrettType: String?
        ): EOSellerEFTAGrunnlagskategoriOppholdsrettType? {
            return EOSellerEFTAGrunnlagskategoriOppholdsrettType.values()
                .firstOrNull { it.kodeverdi == eosEllerEFTAGrunnlagskategoriOppholdsrettType }
        }
    }
}

enum class EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType(val kodeverdi: String) {
    EGNE_MIDLER_ELLER_FASTE_PERIODISKE_YTELSER("EgneMidlerEllerFastePeriodiskeYtelser"),
    ARBEID("Arbeid"),
    UTDANNING("Utdanning"),
    FAMILIE("Familie"),
    TJENESTEYTING_ELLER_ETABLERING("TjenesteytingEllerEtablering"),
    UAVKLART("Uavklart");

    companion object {
        fun fraEOSellerEFTAGrunnlagskategoriOppholdsTillatelseType(
            eosEllerEFTAGrunnlagskategoriOppholdsTillatelseType: String?
        ): EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType? {
            return EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType.values()
                .firstOrNull { it.kodeverdi == eosEllerEFTAGrunnlagskategoriOppholdsTillatelseType }
        }
    }
}