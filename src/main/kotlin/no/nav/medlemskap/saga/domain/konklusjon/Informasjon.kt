package no.nav.medlemskap.saga.domain.konklusjon

enum class Informasjon() {
    TREDJELANDSBORGER,
    EØS_BORGER,
    TREDJELANDSBORGER_MED_EOS_FAMILIE,
    NORSK_BORGER,
    IKKE_SJEKKET_UT;

    fun tilKategori(): Statsborgerskapskategori =
        when (this) {
            TREDJELANDSBORGER -> Statsborgerskapskategori.ANDRE_BORGERE
            TREDJELANDSBORGER_MED_EOS_FAMILIE -> Statsborgerskapskategori.ANDRE_BORGERE_MED_EØS_FAMILIE
            EØS_BORGER -> Statsborgerskapskategori.EØS_BORGER
            NORSK_BORGER -> Statsborgerskapskategori.NORSK_BORGER
            IKKE_SJEKKET_UT -> Statsborgerskapskategori.IKKE_SJEKKET_UT
        }
}