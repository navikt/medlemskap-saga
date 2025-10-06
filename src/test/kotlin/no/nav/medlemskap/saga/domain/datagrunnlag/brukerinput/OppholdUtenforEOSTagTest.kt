package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold
import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OppholdUtenforEOSTagTest {

    @Test
    fun `skal ikke utlede felter når det ikke finnes OppholdUtenforEOS`() {
        val fra = OppholdUtenforEOSTag.fra(null)

        assertEquals(fra, null)
    }

    @Test
    fun `skal utlede felter fra OppholdUtenforEOS når svar er Ja`() {
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
        val fra = OppholdUtenforEOSTag.fra(oppholdUtenforEOS)
        val forventet = OppholdUtenforEOSTag(
            oppholdUtenforEØS = true,
            oppholdUtenforEØSLand = "Sverige",
            oppholdUtenforEØSFom = "2024-01-01",
            oppholdUtenforEØSTom = "2024-01-15",
            oppholdUtenforEØSAntallPerioder = 2,
            oppholdUtenforEØSGrunn = "Ferie"
        )

        assertEquals(fra, forventet)
    }

    @Test
    fun `skal ha tomme felter fra OppholdUtenforEOS når svar er Nei`() {
        val oppholdUtenforEOS = OppholdUtenforEOS(
            svar = false,
            oppholdUtenforEOS = emptyList()
        )
        val fra = OppholdUtenforEOSTag.fra(oppholdUtenforEOS)

        val forventet = OppholdUtenforEOSTag(
            oppholdUtenforEØS = false,
            oppholdUtenforEØSLand = "",
            oppholdUtenforEØSFom = "",
            oppholdUtenforEØSTom = "",
            oppholdUtenforEØSAntallPerioder = 0,
            oppholdUtenforEØSGrunn = ""
        )

        assertEquals(fra, forventet)
    }
}