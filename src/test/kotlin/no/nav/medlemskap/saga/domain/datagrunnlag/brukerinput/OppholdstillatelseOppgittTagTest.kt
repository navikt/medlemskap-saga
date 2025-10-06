package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OppholdstillatelseOppgittTagTest {

    @Test
    fun `skal ikke utlede felter når det ikke finnes oppholdstillatelse`() {
        val fra = OppholdstillatelseOppgittTag.fra(null)

        assertEquals(fra, null)
    }

    @Test
    fun `skal utlede verdier fra Oppholdstillatelse`() {
        val oppholdstillatelse = OppholdstillatelseOppgitt(
            svar = true,
            perioder = listOf(
                Periode(fom = "2024-01-01", tom = "2024-12-31"),
                Periode(fom = "2025-01-01", tom = "2025-12-31")
            )
        )
        val fra = OppholdstillatelseOppgittTag.fra(oppholdstillatelse)
        val forventet = OppholdstillatelseOppgittTag(
            oppholdstillatelseOppgitt = true,
            oppholdstillatelseOppgittFom = "2024-01-01",
            oppholdstillatelseOppgittTom = "2024-12-31",
            oppholdstillatelseOppgittAntallPerioder = 2
        )

        assertEquals(fra, forventet)
    }
}