package no.nav.medlemskap.saga.domain.datagrunnlag.udi

data class EOSellerEFTAOpphold(
    val periode: UdiPeriode,
    val eosellerEFTAOppholdType: EOSellerEFTAOppholdType,
    val eosellerEFTAGrunnlagskategoriOppholdsrettType: EOSellerEFTAGrunnlagskategoriOppholdsrettType?,
    val eosellerEFTAGrunnlagskategoriOppholdstillatelseType: EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType?
)

enum class EOSellerEFTAOppholdType(val kodeverdi: String) {
    EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT("EOSellerEFTABeslutningOmOppholdsrett"),
    EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT("EOSellerEFTAVedtakOmVarigOppholdsrett"),
    EOS_ELLER_EFTA_OPPHOLDSTILLATELSE("EOSellerEFTAOppholdstillatelse");
}

enum class EOSellerEFTAGrunnlagskategoriOppholdsrettType(val kodeverdi: String) {
    VARIG("Varig"),
    INGEN_INFORMASJON("IngenInformasjon"),
    FAMILIE("Familie"),
    TJENESTEYTING_ELLER_ETABLERING("TjenesteytingEllerEtablering"),
    UAVKLART("Uavklart");
}

enum class EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType(val kodeverdi: String) {
    EGNE_MIDLER_ELLER_FASTE_PERIODISKE_YTELSER("EgneMidlerEllerFastePeriodiskeYtelser"),
    ARBEID("Arbeid"),
    UTDANNING("Utdanning"),
    FAMILIE("Familie"),
    TJENESTEYTING_ELLER_ETABLERING("TjenesteytingEllerEtablering"),
    UAVKLART("Uavklart");
}