package no.nav.medlemskap.saga.generer_uttrekk

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PeriodeForUttrekkTest {

    @Test
    fun `skal gi riktig første og siste dag for september 2025`() {

        val (førsteDag, sisteDag) = PeriodeForUttrekk.finnPeriode("202509")

        assertEquals(LocalDate.of(2025, 9, 1), førsteDag)
        assertEquals(LocalDate.of(2025, 9, 30), sisteDag)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar skuddår`() {
        val (førsteDag, sisteDag) = PeriodeForUttrekk.finnPeriode("202402")

        assertEquals(LocalDate.of(2024, 2, 1), førsteDag)
        assertEquals(LocalDate.of(2024, 2, 29), sisteDag)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar ikke-skuddår`() {
        val (førsteDag, sisteDag) = PeriodeForUttrekk.finnPeriode("202502")

        assertEquals(LocalDate.of(2025, 2, 1), førsteDag)
        assertEquals(LocalDate.of(2025, 2, 28), sisteDag)
    }

}