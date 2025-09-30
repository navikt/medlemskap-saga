package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold
import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OppholdUtenforNorgeTagTest {

    @Test
    fun `skal ha tomme verdier når det ikke finnes OppholdUtenforNorge`() {
        val defaultOppholdUtenforNorge = OppholdUtenforNorge()
        val tag = OppholdUtenforNorgeTag(defaultOppholdUtenforNorge)

        assertFalse(tag.oppholdUtenforNorge)
        assertEquals("", tag.oppholdUtenforNorgeLand)
        assertEquals("", tag.oppholdUtenforNorgeFom)
        assertEquals("", tag.oppholdUtenforNorgeTom)
        assertEquals(0, tag.oppholdUtenforNorgeAntallPerioder)
        assertEquals("", tag.oppholdUtenforNorgeGrunn)
    }

    @Test
    fun `skal utlede verdier fra OppholdUtenforNorge`() {
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
        val oppholdUtenforNorge = OppholdUtenforNorge(
            svar = true,
            oppholdUtenforNorge = opphold
        )
        val tag = OppholdUtenforNorgeTag(oppholdUtenforNorge)

        assertTrue(tag.oppholdUtenforNorge)
        assertEquals("Sverige", tag.oppholdUtenforNorgeLand)
        assertEquals("2024-01-01", tag.oppholdUtenforNorgeFom)
        assertEquals("2024-01-15", tag.oppholdUtenforNorgeTom)
        assertEquals(2, tag.oppholdUtenforNorgeAntallPerioder)
        assertEquals("Ferie", tag.oppholdUtenforNorgeGrunn)
    }
}