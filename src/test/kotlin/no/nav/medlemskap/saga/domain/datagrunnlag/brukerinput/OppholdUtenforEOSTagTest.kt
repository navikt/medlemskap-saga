package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold
import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OppholdUtenforEOSTagTest {

    @Test
    fun `skal ha tomme felter når det ikke finnes OppholdUtenforEOS`() {
        val defaultOppholdUtenforEOS = OppholdUtenforEOS()
        val tag = OppholdUtenforEOSTag(defaultOppholdUtenforEOS)

        assertFalse(tag.oppholdUtenforEØS)
        assertEquals("", tag.oppholdUtenforEØSLand)
        assertEquals("", tag.oppholdUtenforEØSFom)
        assertEquals("", tag.oppholdUtenforEØSTom)
        assertEquals(0, tag.oppholdUtenforEØSAntallPerioder)
        assertEquals("", tag.oppholdUtenforEØSGrunn)
    }

    @Test
    fun `skal utlede verdier fra OppholdUtenforEOS`() {
        val opphold = listOf(
            Opphold(
                id = "1",
                land = "Sverige",
                grunn = "Ferie",
                perioder = listOf(Periode(fom = "2024-01-01", tom = "2024-01-15"))
            ),
            Opphold(
                id = "2",
                land = "Sverige",
                grunn = "Ferie",
                perioder = listOf(Periode(fom = "2024-02-01", tom = "2024-02-10"))
            )
        )
        val oppholdUtenforEOS = OppholdUtenforEOS(
            svar = true,
            oppholdUtenforEOS = opphold
        )
        val tag = OppholdUtenforEOSTag(oppholdUtenforEOS)

        assertTrue(tag.oppholdUtenforEØS)
        assertEquals("Sverige", tag.oppholdUtenforEØSLand)
        assertEquals("2024-01-01", tag.oppholdUtenforEØSFom)
        assertEquals("2024-01-15", tag.oppholdUtenforEØSTom)
        assertEquals(2, tag.oppholdUtenforEØSAntallPerioder)
        assertEquals("Ferie", tag.oppholdUtenforEØSGrunn)
    }
}