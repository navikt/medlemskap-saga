package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Opphold
import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OppholdUtenforNorgeTagTest {

    @Test
    fun `skal ikke utlede felter når det ikke finnes OppholdUtenforNorge`() {
        val fra = OppholdUtenforNorgeTag.fra(null)

        assertEquals(fra, null)
    }

    @Test
    fun `skal utlede felter fra OppholdUtenforNorge når svar er Ja`() {
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
        val fra = OppholdUtenforNorgeTag.fra(oppholdUtenforNorge)

        val forventet = OppholdUtenforNorgeTag(
            oppholdUtenforNorge = true,
            oppholdUtenforNorgeLand = "Sverige",
            oppholdUtenforNorgeFom = "2024-01-01",
            oppholdUtenforNorgeTom = "2024-01-15",
            oppholdUtenforNorgeAntallPerioder = 2,
            oppholdUtenforNorgeGrunn = "Ferie"
        )

        assertEquals(fra, forventet)
    }


    @Test
    fun `skal ha tomme felter fra OppholdUtenforNorge når svar er Nei`() {
        val oppholdUtenforNorge = OppholdUtenforNorge(
            svar = false,
            oppholdUtenforNorge = emptyList()
        )
        val fra = OppholdUtenforNorgeTag.fra(oppholdUtenforNorge)

        val forventet = OppholdUtenforNorgeTag(
            oppholdUtenforNorge = false,
            oppholdUtenforNorgeLand = "",
            oppholdUtenforNorgeFom = "",
            oppholdUtenforNorgeTom = "",
            oppholdUtenforNorgeAntallPerioder = 0,
            oppholdUtenforNorgeGrunn = ""
        )

        assertEquals(fra, forventet)
    }
}