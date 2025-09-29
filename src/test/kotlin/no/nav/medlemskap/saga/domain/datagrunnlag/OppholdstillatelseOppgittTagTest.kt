package no.nav.medlemskap.saga.domain.datagrunnlag

import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgitt
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgittTag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OppholdstillatelseOppgittTagTest {

    @Test
    fun `skal ha tomme felter når det ikke finnes oppholdstillatelse`() {
        val defaultOppholdstillatelse = OppholdstillatelseOppgitt()
        val tag = OppholdstillatelseOppgittTag(defaultOppholdstillatelse)

        assertFalse(tag.oppholdstillatelseOppgitt)
        assertEquals("", tag.oppholdstillatelseOppgittFom)
        assertEquals("", tag.oppholdstillatelseOppgittTom)
        assertEquals(0, tag.oppholdstillatelseOppgittAntallPerioder)
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

        assertTrue(tag.oppholdstillatelseOppgitt)
        assertEquals("2024-01-01", tag.oppholdstillatelseOppgittFom)
        assertEquals("2024-12-31", tag.oppholdstillatelseOppgittTom)
        assertEquals(2, tag.oppholdstillatelseOppgittAntallPerioder)
    }
}