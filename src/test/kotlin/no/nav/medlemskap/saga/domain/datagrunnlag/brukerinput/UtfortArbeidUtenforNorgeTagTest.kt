package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtfortArbeidUtenforNorgeTagTest {

    @Test
    fun `skal ikke utlede felter når utført utenfor norge ikke finnes`() {

        val fra = UtfortArbeidUtenforNorgeTag.fra(null)

        assertEquals(null, fra)
    }

    @Test
    fun `skal utlede felter fra UtfortArbeidUtenforNorge når svar er ja`() {
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

        val fra = UtfortArbeidUtenforNorgeTag.fra(utførtArbeidUtenforNorge)

        val forventet = UtfortArbeidUtenforNorgeTag(
            utførtArbeidUtenforNorge = true,
            utførtArbeidUtenforNorgeLand = "Et land",
            utførtArbeidUtenforNorgeFom = "2024-05-01",
            utførtArbeidUtenforNorgeTom = "2024-05-10",
            utførtArbeidUtenforNorgeAntallPerioder = 2
        )

        assertEquals(fra, forventet)

    }


    @Test
    fun `skal ha tomme felter fra UtfortArbeidUtenforNorge når svar er nei`() {

        val utførtArbeidUtenforNorge = UtfortArbeidUtenforNorge(
            svar = false,
            arbeidUtenforNorge = emptyList()
        )

        val fra = UtfortArbeidUtenforNorgeTag.fra(utførtArbeidUtenforNorge)

        val forventet = UtfortArbeidUtenforNorgeTag(
            utførtArbeidUtenforNorge = false,
            utførtArbeidUtenforNorgeLand = "",
            utførtArbeidUtenforNorgeFom = "",
            utførtArbeidUtenforNorgeTom = "",
            utførtArbeidUtenforNorgeAntallPerioder = 0
        )

        assertEquals(fra, forventet)

    }
}