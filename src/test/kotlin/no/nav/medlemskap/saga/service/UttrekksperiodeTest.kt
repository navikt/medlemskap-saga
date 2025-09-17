package no.nav.medlemskap.saga.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate


class UttrekksperiodeTest {

    @Test
    fun `skal gi riktig første og siste dag for september 2025`() {
        val periode = Uttrekksperiode("2025-09")

        assertEquals(LocalDate.of(2025, 9, 1), periode.førsteDag)
        assertEquals(LocalDate.of(2025, 9, 30), periode.sisteDag)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar skuddår`() {
        val periode = Uttrekksperiode("2024-02")

        assertEquals(LocalDate.of(2024, 2, 1), periode.førsteDag)
        assertEquals(LocalDate.of(2024, 2, 29), periode.sisteDag)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar ikke-skuddår`() {
        val periode = Uttrekksperiode("2025-02")

        assertEquals(LocalDate.of(2025, 2, 1), periode.førsteDag)
        assertEquals(LocalDate.of(2025, 2, 28), periode.sisteDag)
    }

}