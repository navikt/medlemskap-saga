package no.nav.medlemskap.saga.utled_vurderingstagger

object UtledKilde {

    fun utledFra(kanal: String): String {
        return when (kanal.lowercase()) {
            "/" -> "speil"
            "kafka" -> "sykepengesoknad-backend"
            else -> ""
        }
    }
}