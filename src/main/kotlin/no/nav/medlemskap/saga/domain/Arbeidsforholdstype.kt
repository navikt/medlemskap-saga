package no.nav.medlemskap.saga.domain

enum class Arbeidsforholdstype(val kodeverdi: String) {
    FRILANSER("frilanserOppdragstakerHonorarPersonerMm"),
    MARITIMT("maritimtArbeidsforhold"),
    NORMALT("ordinaertArbeidsforhold"),
    FORENKLET("forenkletOppgjoersordning"),
    ANDRE("pensjonOgAndreTyperYtelserUtenAnsettelsesforhold");

    companion object {
        fun fraArbeidsforholdtypeVerdi(arbeidsforholdstypeVerdi: String): Arbeidsforholdstype {
            return values().first { it.kodeverdi == arbeidsforholdstypeVerdi }
        }
    }
}