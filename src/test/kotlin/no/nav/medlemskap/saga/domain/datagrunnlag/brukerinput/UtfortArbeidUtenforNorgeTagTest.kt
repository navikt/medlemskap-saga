package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtfortArbeidUtenforNorgeTagTest {

    @Test
    fun `skal ha tomme felter når det ikke finnes UtfortArbeidUtenforNorge`() {
        val defaultArbeid = UtfortArbeidUtenforNorge()
        val tag = UtfortArbeidUtenforNorgeTag(defaultArbeid)

        assertFalse(tag.utførtArbeidUtenforNorge)
        assertEquals("", tag.utførtArbeidUtenforNorgeLand)
        assertEquals("", tag.utførtArbeidUtenforNorgeFom)
        assertEquals("", tag.utførtArbeidUtenforNorgeTom)
        assertEquals(0, tag.utførtArbeidUtenforNorgeAntallPerioder)
    }

    @Test
    fun `skal utlede verdier fra UtfortArbeidUtenforNorge`() {
        val arbeidUtenforNorge = listOf(
            ArbeidUtenforNorge(
                id = "1",
                arbeidsgiver = "Firma AS",
                land = "Et land",
                perioder = listOf(Periode("2024-05-01", tom = "2024-05-10"))
            ),
            ArbeidUtenforNorge(
                id = "2",
                arbeidsgiver = "Firma AS",
                land = "Et land",
                perioder = listOf(Periode("2024-06-01", tom = "2024-06-15"))
            )
        )

        val utførtArbeidUtenforNorge = UtfortArbeidUtenforNorge(
            svar = true,
            arbeidUtenforNorge = arbeidUtenforNorge
        )

        val tag = UtfortArbeidUtenforNorgeTag(utførtArbeidUtenforNorge)

        assertTrue(tag.utførtArbeidUtenforNorge)
        assertEquals("Et land", tag.utførtArbeidUtenforNorgeLand)
        assertEquals("2024-05-01", tag.utførtArbeidUtenforNorgeFom)
        assertEquals("2024-05-10", tag.utførtArbeidUtenforNorgeTom)
        assertEquals(2, tag.utførtArbeidUtenforNorgeAntallPerioder)
    }
}