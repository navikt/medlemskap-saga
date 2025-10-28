package no.nav.medlemskap.saga.utled_vurderingstagger

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtledKildeTest {

    @Test
    fun skal_utlede_riktig_kilde_fra_kanal() {
        val kanal1 = "/"
        val kanal2 = "kafka"
        val kanal3 = "SOME_OTHER_KANAL"

        val forventetKilde1 = "speil"
        val forventetKilde2 = "sykepengesoknad-backend"
        val forventetKilde3 = ""

        assertEquals(forventetKilde1, UtledKilde.utledFra(kanal1))
        assertEquals(forventetKilde2, UtledKilde.utledFra(kanal2))
        assertEquals(forventetKilde3, UtledKilde.utledFra(kanal3))
    }
}