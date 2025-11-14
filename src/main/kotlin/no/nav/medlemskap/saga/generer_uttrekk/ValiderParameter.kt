package no.nav.medlemskap.saga.generer_uttrekk

object ValiderParameter {

    fun validerParameter(årMaanedParam: String): Pair<Int, Int> {

        val regex = Regex("^\\d{6}$")
        require(regex.matches(årMaanedParam)) { "Ugyldig format for 'aarMaaned parameter'. Forventet YYYYMM" }

        val årStr = årMaanedParam.take(4)
        val månedStr = årMaanedParam.takeLast(2)

        val år = årStr.toIntOrNull() ?: throw IllegalArgumentException("Ugyldig år: $årStr")
        val måned = månedStr.toIntOrNull() ?: throw IllegalArgumentException("Ugyldig måned: $månedStr")

        require(måned in 1..12) { "Måned må være mellom 01 og 12" }

        return år to måned
    }
}