package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

import no.nav.medlemskap.saga.domain.datagrunnlag.Periode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OppholdstillatelseOppgittTagTest {

    @Test
    fun `skal ha tomme felter når det ikke finnes oppholdstillatelse`() {
        val defaultOppholdstillatelse = OppholdstillatelseOppgitt()
        val tag = OppholdstillatelseOppgittTag(defaultOppholdstillatelse)

        Assertions.assertFalse(tag.oppholdstillatelseOppgitt)
        Assertions.assertEquals("", tag.oppholdstillatelseOppgittFom)
        Assertions.assertEquals("", tag.oppholdstillatelseOppgittTom)
        Assertions.assertEquals(0, tag.oppholdstillatelseOppgittAntallPerioder)
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
        val tag = OppholdstillatelseOppgittTag(oppholdstillatelse)

        Assertions.assertTrue(tag.oppholdstillatelseOppgitt)
        Assertions.assertEquals("2024-01-01", tag.oppholdstillatelseOppgittFom)
        Assertions.assertEquals("2024-12-31", tag.oppholdstillatelseOppgittTom)
        Assertions.assertEquals(2, tag.oppholdstillatelseOppgittAntallPerioder)
    }
}