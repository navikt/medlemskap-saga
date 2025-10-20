import no.nav.medlemskap.saga.generer_uttrekk.UttrekkForPeriode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class UttrekkForPeriodeTest {

    @Test
    fun `skal gi riktig første og siste dag for september 2025`() {
        val resultat = UttrekkForPeriode("202509")

        assertEquals(LocalDate.of(2025, 9, 1), resultat.førsteDag)
        assertEquals(LocalDate.of(2025, 9, 30), resultat.sisteDag)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar skuddår`() {
        val resultat =UttrekkForPeriode("202402")

        assertEquals(LocalDate.of(2024, 2, 1), resultat.førsteDag)
        assertEquals(LocalDate.of(2024, 2, 29), resultat.sisteDag)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar ikke-skuddår`() {
        val resultat = UttrekkForPeriode("202502")

        assertEquals(LocalDate.of(2025, 2, 1), resultat.førsteDag)
        assertEquals(LocalDate.of(2025, 2, 28), resultat.sisteDag)
    }
}