package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.domain.datagrunnlag.InputPeriode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class InputPeriodeTest {

    @Test
    fun `fom og tom samme dag gir 1 dag`() {
        val dato = LocalDate.of(2024, 6, 10)
        val periode = InputPeriode(fom = dato, tom = dato)
        assertEquals(1, periode.antallDager())
    }

    @Test
    fun `fom og tom med to dagers mellomrom gir 3 dager`() {
        val fom = LocalDate.of(2024, 6, 10)
        val tom = LocalDate.of(2024, 6, 12)
        val periode = InputPeriode(fom = fom, tom = tom)
        assertEquals(3, periode.antallDager())
    }

    @Test
    fun `fom og tom med 14 dagers mellomrom gir 15 dager`() {
        val fom = LocalDate.of(2024, 6, 1)
        val tom = LocalDate.of(2024, 6, 15)
        val periode = InputPeriode(fom = fom, tom = tom)
        assertEquals(15, periode.antallDager())
    }

}